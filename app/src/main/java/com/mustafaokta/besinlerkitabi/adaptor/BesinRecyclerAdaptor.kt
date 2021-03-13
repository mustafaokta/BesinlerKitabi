package com.mustafaokta.besinlerkitabi.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.mustafaokta.besinlerkitabi.R
import com.mustafaokta.besinlerkitabi.databinding.BesinRecyclerRowBinding
import com.mustafaokta.besinlerkitabi.model.Besin
import com.mustafaokta.besinlerkitabi.util.gorselIndir
import com.mustafaokta.besinlerkitabi.util.placeHolderYap
import com.mustafaokta.besinlerkitabi.view.BesinListesiFragmentDirections
import kotlinx.android.synthetic.main.besin_recycler_row.view.*

class BesinRecyclerAdaptor(val besinListesi: ArrayList<Besin>): RecyclerView.Adapter<BesinRecyclerAdaptor.BesinViewHolder>(), BesinClickListener {
    class  BesinViewHolder(var view: BesinRecyclerRowBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BesinViewHolder {
        val inflator =LayoutInflater.from(parent.context)
       // val view=inflator.inflate(R.layout.besin_recycler_row,parent,false)
        val view= DataBindingUtil.inflate<BesinRecyclerRowBinding>(inflator,R.layout.besin_recycler_row, parent, false)
        return BesinViewHolder(view)
    }

    override fun onBindViewHolder(holder: BesinViewHolder, position: Int) {

        holder.view.besin=besinListesi[position]
        holder.view.listener=this
        /*

        holder.itemView.isimText.text=besinListesi.get(position).besinIsim
        holder.itemView.kaloriText.text=besinListesi.get(position).besinKalori


        holder.itemView.setOnClickListener {
            val action= BesinListesiFragmentDirections.actionBesinListesiFragmentToBesinDetayiFragment(besinListesi.get(position).uuid)
            Navigation.findNavController(it).navigate(action)
        }
    holder.itemView.imageView.gorselIndir(besinListesi.get(position).besinGorsel, placeHolderYap(holder.itemView.context))
         */
    }


    fun besinListesiniGuncelle(yeniBesinListesi: List<Besin>){
        besinListesi.clear()
        besinListesi.addAll(yeniBesinListesi)
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return besinListesi.size
    }

    override fun besinTiklandi(view: View) {
        val uuid=  view.besin_uuid.text.toString().toIntOrNull()
        uuid?.let {
            val action= BesinListesiFragmentDirections.actionBesinListesiFragmentToBesinDetayiFragment(it)
            Navigation.findNavController(view).navigate(action)
        }

    }

}