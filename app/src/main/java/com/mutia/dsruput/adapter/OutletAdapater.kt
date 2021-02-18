package com.mutia.dsruput.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mutia.dsruput.R
import com.mutia.dsruput.model.getData.DataItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_outlet.view.*
import java.lang.System.load

class OutletAdapater(
    val data: List<DataItem?>?,
    val itemClick: OnClickListener
) : RecyclerView.Adapter<OutletAdapater.ViewHolder>(){

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val nama_outlet = view.namaOutlet
        val alamat = view.alamatOutlet
        val gambar_outlet = view.imgOutlet
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OutletAdapater.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_outlet, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: OutletAdapater.ViewHolder, position: Int) {
        val item = data?.get(position)

        holder.nama_outlet.text = item?.namaOutlet
        holder.alamat.text = item?.alamat

        Picasso.get().load("http://192.168.100.143/dsruput/img/"+item?.gambarOutlet).resize(172,172).centerCrop()
            .into(holder.gambar_outlet)

        holder.view.setOnClickListener{
            itemClick.menu(item?.kodeOutlet)
        }
    }

    override fun getItemCount(): Int = data?.size ?:0

    interface OnClickListener{
        fun menu(kodeOutlet: String?)
    }
}

