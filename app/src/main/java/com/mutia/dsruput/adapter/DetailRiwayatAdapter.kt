package com.mutia.dsruput.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mutia.dsruput.R
import com.mutia.dsruput.config.Url
import com.mutia.dsruput.model.DataItemDetailRiwayat
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_checkout.view.*
import kotlinx.android.synthetic.main.item_riwayat.view.*

class DetailRiwayatAdapter(val data: List<DataItemDetailRiwayat?>?): RecyclerView.Adapter<DetailRiwayatAdapter.DetailRiwayatViewHolder>() {

    class DetailRiwayatViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val rasa = view.txtRasaCheck
        val harga = view.txtHargaCheck
        val jumlah = view.txtJmlCheck
        val imgMenu = view.imgMenuCheck
        val tambahan = view.txtTambahanCheck
        val context = view.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailRiwayatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_checkout, parent, false)
        return DetailRiwayatAdapter.DetailRiwayatViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailRiwayatViewHolder, position: Int) {
        val item = data?.get(position)

        holder.rasa.text = item?.rasa
        holder.jumlah.text = item?.jumlah + "X"
        holder.harga.text = item?.harga
        holder.tambahan.text = item?.toppingTambahan
        Picasso.get().load(Url.urlImageMenu + item?.gambar)
            .into(holder.imgMenu)
    }

    override fun getItemCount(): Int = data?.size ?: 0

}