package com.mutia.dsruput.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import com.mutia.dsruput.R
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

//        Picasso.get().load("http://192.168.43.84/dsruput/img/" + item?.gambarOutlet).resize(
//            90,
//            90
//        ).centerCrop()
//            .into(holder.gambar_outlet)

        Picasso.get().load("http://192.168.43.84/dsruput/img/" + item?.gambarOutlet).into(holder.gambar_outlet)

        holder.view.setOnClickListener{
            itemClick.menu(item?.kodeOutlet, item?.namaOutlet)
        }

        holder.jarak.text = item?.jarak.toString()

//        prefManager = PrefManager(holder.context)
//        val ltdOutlet = item?.latitude
//        val ntdOutlet = item?.longtitude
//
//        var ltdUser = prefManager?.getValueFloat("latitude")
//        var ntdUser = prefManager?.getValueFloat("longtitude")
//
//        var latLngOutlet : LatLng = LatLng(ltdOutlet.toString().toDouble(), ntdOutlet.toString().toDouble())
//        var latLngUser: LatLng = LatLng(ltdUser.toString().toDouble(), ntdUser.toString().toDouble())
//        var distance: Double? = null
//
//        distance = SphericalUtil.computeDistanceBetween(latLngOutlet, latLngUser)
//
//        if (distance >= 1000){
//            var distanceKm = String.format("%.2f", distance!! / 1000) + " Km"
//            holder.jarak.text = distanceKm
//        }else{
//            var distanceM = String.format("%.2f", distance!!) + " M"
//            holder.jarak.text = distanceM
//        }


//        var jarak = (distance!! / 1000).toString().toFloat()
//        Log.d("jarak adr"," : "+jarak)
//
//        var jml = (data?.size)?.minus(1)
//
//        var arrayJarak : Array<String>
//        for (i in 0..jml!! ){
//            arrayJarak = arrayOf(arrayOf(jarak).toString())
//
//        }

       // prefManager.save("jarakOutlet", jarak)

    }

    override fun getItemCount(): Int = data?.size ?:0

    interface OnClickListener{
        fun menu(kodeOutlet: String?, namaOutlet: String?)
    }
}

