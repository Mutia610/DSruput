package com.mutia.dsruput.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mutia.dsruput.R
import com.mutia.dsruput.model.getData.DataItem
import com.mutia.dsruput.model.getMenu.DataMenu
import com.mutia.dsruput.view.MenuActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_menu.view.*
import java.util.*

class MenuAdapter(val menu: List<DataMenu?>?) : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    class ViewHolder (val view: View) : RecyclerView.ViewHolder(view){
        val rasa = view.txtRasa
        val harga = view.txtHarga
        val varian = view.txtVarian
        val status1 = view.txtStatus1
        val status2 = view.txtStatus2
        val imageMenu = view.imgMenu
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_menu,parent,false)
        return MenuAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuAdapter.ViewHolder, position: Int) {
        val item = menu?.get(position)

        holder.rasa.text = item?.rasa
        holder.harga.text = item?.harga
      //holder.status1.text = item?.status
        holder.varian.text = item?.varian

        Picasso.get().load("http://192.168.100.143/dsruput/img/menu/"+ item?.gambar).resize(172,172)
            .into(holder.imageMenu)

        if (item?.status.toString() == "Tersedia"){
            holder.status1.visibility = View.VISIBLE
            holder.status2.visibility = View.GONE
        }else if(item?.status.toString() == "Tidak Tersedia"){
            holder.status2.visibility = View.VISIBLE
            holder.status1.visibility = View.GONE
        }

    }

    override fun getItemCount(): Int = menu?.size ?:0

}