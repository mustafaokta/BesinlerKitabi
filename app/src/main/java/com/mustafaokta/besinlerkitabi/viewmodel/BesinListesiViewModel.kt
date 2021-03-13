package com.mustafaokta.besinlerkitabi.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData

import com.mustafaokta.besinlerkitabi.model.Besin
import com.mustafaokta.besinlerkitabi.service.BesinAPIServis
import com.mustafaokta.besinlerkitabi.service.BesinDatabase
import com.mustafaokta.besinlerkitabi.util.OzelSharedPreferences

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class BesinListesiViewModel(application: Application) : BaseViewModel(application) {
    val besinler= MutableLiveData<List<Besin>>()
    val besinHataMesaji=MutableLiveData<Boolean>()
    val besinYukleniyor=MutableLiveData<Boolean>()
    private var guncellemeZamani=0.2 * 60 * 1000 * 1000 * 1000L // 10  dakikayı saniyeye cevırır sonra nanotime a
    private val besinApiServis= BesinAPIServis()
    private val disposable= CompositeDisposable()
    private val ozelSharedPreferences=OzelSharedPreferences(getApplication())
    fun refreshData(){
      /*  val muz = Besin("Muz","100")
        val cilek = Besin("Çilek","200")
        val elma = Besin("Elma","300")

        val besinListesi= arrayListOf<Besin>(muz,cilek,elma)
        besinler.value=besinListesi
        besinHataMesaji.value=false
        besinYukleniyor.value=false
        */


        val kaydedilmeZamani= ozelSharedPreferences.zamaniAl()
        if(kaydedilmeZamani!=null && kaydedilmeZamani!=0L && System.nanoTime()-kaydedilmeZamani<guncellemeZamani){
            //sqlite dan çek
            verileriSqlitetanAl()
        }else{
            verileriInternettenAl()
        }
    }
    fun refreshfromInternet(){
        verileriInternettenAl()
    }

            private fun verileriSqlitetanAl(){
            launch {
                val besinListesi= BesinDatabase(getApplication()).besinDao().getAllBesin()
                besinleriGoster(besinListesi)
                Toast.makeText(getApplication(),"Besinleri roomdan aldık", Toast.LENGTH_LONG).show()
            }
            }
            private fun verileriInternettenAl(){
                besinYukleniyor.value=true

                besinYukleniyor.value=true
                disposable.add(
                        besinApiServis
                                .getData()
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeWith(object : DisposableSingleObserver<List<Besin>>(){
                                    override fun onSuccess(t: List<Besin>) {
                                        //başarılı olursa
                                    sqliteSakla(t)
                                        Toast.makeText(getApplication(),"Besinleri ınterneten aldık", Toast.LENGTH_LONG).show()
                                    }

                                    override fun onError(e: Throwable) {
                                        //Hata alırsak
                                        besinHataMesaji.value=true
                                        besinYukleniyor.value=false
                                        e.printStackTrace()
                                    }


                                })

                ) 
    }

    private  fun besinleriGoster(besinlerListesi : List<Besin>){
        besinler.value=besinlerListesi
        besinHataMesaji.value=false
        besinYukleniyor.value=false
    }

    private fun sqliteSakla(besinListesi: List<Besin>){
    launch {
        val dao = BesinDatabase(getApplication()).besinDao()
        dao.deleteAllBesin()
      val uuidListesi= dao.insertAll(*besinListesi.toTypedArray()) //burada veritabanına ekliyor ve eklenenlerin
        // idlistesini geri dönüyor
        var i=0
        while (i< besinListesi.size){
        besinListesi[i].uuid=uuidListesi[i].toInt()
            i=i+1
        }
        besinleriGoster(besinListesi)
    }
        ozelSharedPreferences.zamaniKaydet(System.nanoTime())
    }
}