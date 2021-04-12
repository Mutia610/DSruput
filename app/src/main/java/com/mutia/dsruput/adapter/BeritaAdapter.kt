package com.mutia.dsruput.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mutia.dsruput.R
import com.mutia.dsruput.config.Url
import com.mutia.dsruput.model.DataItemBerita
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_berita.view.*
import kotlinx.android.synthetic.main.item_checkout.view.*

class BeritaAdapter(val data: List<DataItemBerita?>?): RecyclerView.Adapter<BeritaAdapter.ViewHolderBerita>() {

    class ViewHolderBerita (val view: View): RecyclerView.ViewHolder(view) {
        val img = view.imgBerita
        val keterangan = view.textBerita
        val tanggal = view.tglBerita
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderBerita {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_berita, parent, false)
        return ViewHolderBerita(view)
    }

    override fun onBindViewHolder(holder: ViewHolderBerita, position: Int) {
        val data = data?.get(position)

        holder.keterangan.text = data?.keterangan
        holder.tanggal.text = data?.tanggal + " - " + data?.jam
        Picasso.get().load(Url.urlSlider + data?.imgBerita)
            .into(holder.img)
    }

    override fun getItemCount(): Int = data?.size ?: 0

}