package com.mustafaokta.besinlerkitabi.viewmodel

import android.app.Application
import android.os.ParcelUuid
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mustafaokta.besinlerkitabi.model.Besin
import com.mustafaokta.besinlerkitabi.service.BesinDatabase
import kotlinx.coroutines.launch

class BesinDetayiViewModel(application: Application) : BaseViewModel(application) {
val besinLiveData=MutableLiveData<Besin>()
    fun roomVerisiniAl(uuid: Int){
       // val muz = Besin("Muz","100", "4", "3","4","xxx" )
        //besinLiveData.value=muz
       launch {
           val dao = BesinDatabase(getApplication()).besinDao()
           val besin=dao.getBesin(uuid)
           besinLiveData.value=besin

       }



    }

}