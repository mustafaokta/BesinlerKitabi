package com.mustafaokta.besinlerkitabi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mustafaokta.besinlerkitabi.R
import com.mustafaokta.besinlerkitabi.adaptor.BesinRecyclerAdaptor
import com.mustafaokta.besinlerkitabi.util.benimEklentim
import com.mustafaokta.besinlerkitabi.viewmodel.BesinListesiViewModel
import kotlinx.android.synthetic.main.fragment_besin_listesi.*


class BesinListesiFragment : Fragment() {

    private lateinit var viewModel : BesinListesiViewModel
    private val recyclerBesinAdapter= BesinRecyclerAdaptor(arrayListOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_besin_listesi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel=ViewModelProviders.of(this).get(BesinListesiViewModel::class.java)
        viewModel.refreshData()

        var benimString ="Mustafa"
        benimString.benimEklentim("Mustafa OKTA")
        /*besin_listesi_button.setOnClickListener(View.OnClickListener {
            val action= BesinListesiFragmentDirections.actionBesinListesiFragmentToBesinDetayiFragment(3)
            Navigation.findNavController(it).navigate(action)
        })*/
    besinListRecycler.layoutManager=LinearLayoutManager(context)
        besinListRecycler.adapter=recyclerBesinAdapter
        swipeRefreshLayout.setOnRefreshListener{

            besinYukleniyor.visibility=View.VISIBLE
            besinHataMesaji.visibility=View.GONE
            besinListRecycler.visibility=View.GONE


            viewModel.refreshfromInternet()
            swipeRefreshLayout.isRefreshing=false  // swiperefreshin kendi refresh görüntüsü kafa karıştırıyor kapatalım
        }
        observeLiveData()
       /*   */





    }
    fun observeLiveData(){
        viewModel.besinler.observe(viewLifecycleOwner, Observer { besinler ->
            besinler?.let{
                besinListRecycler.visibility=View.VISIBLE
                recyclerBesinAdapter.besinListesiniGuncelle(besinler)
            }
        })

viewModel.besinHataMesaji.observe(viewLifecycleOwner, Observer { hata->
    hata?.let {
        if(it){
        besinHataMesaji.visibility=View.VISIBLE
        }else{
            besinHataMesaji.visibility=View.GONE
        }
    }
})

viewModel.besinYukleniyor.observe(viewLifecycleOwner, Observer { yukleniyor->
    yukleniyor?.let {
        if(it){
        besinListRecycler.visibility=View.GONE
            besinHataMesaji.visibility=View.GONE
            besinYukleniyor.visibility=View.VISIBLE
        }else{
            besinYukleniyor.visibility=View.GONE
        }
    }
})

    }



}