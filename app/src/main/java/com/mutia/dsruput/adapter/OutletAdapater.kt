package com.mutia.dsruput.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import com.mutia.dsruput.R
import com.mutia.dsruput.config.Url
import com.mutia.dsruput.model.getData.DataItem
import com.mutia.dsruput.preferences.PrefManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_outlet.view.*

class OutletAdapater(
    val data: List<DataItem?>?,
    val itemClick: OnClickListener,
) : RecyclerView.Adapter<OutletAdapater.ViewHolder>() {

    lateinit var prefManager: PrefManager

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val nama_outlet = view.namaOutlet
        val alamat = view.alamatOutlet
        val gambar_outlet = view.imgOutlet
        val jarak = view.jarakOutlet
        val context= view.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OutletAdapater.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_outlet, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: OutletAdapater.ViewHolder, position: Int) {
        val item = data?.get(position)


        holder.nama_outlet.text = item?.namaOutlet
        holder.alamat.text = item?.alamat

        Picasso.get().load(Url.urlImageOutlet + item?.gambarOutlet).into(holder.gambar_outlet)

        holder.view.setOnClickListener{
            itemClick.menu(item?.kodeOutlet, item?.namaOutlet)
        }

        holder.jarak.text = item?.jarak.toString()

    }

    override fun getItemCount(): Int = data?.size ?:0

    interface OnClickListener{
        fun menu(kodeOutlet: String?, namaOutlet: String?)
    }
}

