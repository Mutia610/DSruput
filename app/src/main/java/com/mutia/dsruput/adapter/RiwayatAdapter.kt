package com.mutia.dsruput.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mutia.dsruput.R
import com.mutia.dsruput.config.Url
import com.mutia.dsruput.model.riwayat.DataItemRiwayat
import com.mutia.dsruput.view.dashboard.DetailRiwayatActivity
import com.mutia.dsruput.view.dashboard.KonfirmasiActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_riwayat.view.*

class RiwayatAdapter(val data: List<DataItemRiwayat?>?): RecyclerView.Adapter<RiwayatAdapter.RiwayatViewHolder>(){

    class RiwayatViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val status = view.statusRiwayatr
        val rasa = view.txtRasaRiwayat
        val topping = view.toppingTambahRwt
        val jumlah = view.jumlahRwt
        val totalBayar = view.totBayarRiwayat
        val jmlRiwayat2 = view.jmlRiwayat2
        val totalHarga = view.hargaTotalRwt
        val gambar = view.imgRiwayat
        val context = view.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RiwayatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_riwayat, parent, false)
        return RiwayatAdapter.RiwayatViewHolder(view)
    }

    override fun onBindViewHolder(holder: RiwayatViewHolder, position: Int) {
        val item = data?.get(position)

        holder.status.text = item?.status
        holder.rasa.text = item?.rasa
        holder.topping.text = item?.toppingTambahan
        holder.jumlah.text = item?.jumlah
        holder.totalBayar.text = item?.totalBayar
        holder.totalHarga.text = item?.totalHarga
        holder.jmlRiwayat2.text = item?.banyakItem
        Picasso.get().load(Url.urlImageMenu + item?.gambar).into(holder.gambar)

        holder.view.setOnClickListener(View.OnClickListener { v ->
            val intent = Intent(v.context, DetailRiwayatActivity::class.java)
            intent.putExtra("id_order", item?.idOrder)
            intent.putExtra("total_barang", item?.jumlah)
            v.context.startActivity(intent)
//            if (item?.status.equals("Menunggu Konfirmasi")){
//                val intent = Intent(v.context, KonfirmasiActivity::class.java)
//                intent.putExtra("Metode", 1)
//                v.context.startActivity(intent)
//            }else {
//                val intent = Intent(v.context, DetailRiwayatActivity::class.java)
//                intent.putExtra("id_order", item?.idOrder)
//                intent.putExtra("total_barang", item?.jumlah)
//                v.context.startActivity(intent)
//            }
        })
    }

    override fun getItemCount(): Int = data?.size ?: 0
}

