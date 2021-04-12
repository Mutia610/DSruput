package com.mutia.dsruput.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mutia.dsruput.R
import com.mutia.dsruput.config.Url
import com.mutia.dsruput.model.getDataKeranjang.DataItemKeranjang
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_checkout.view.*
import kotlinx.android.synthetic.main.item_keranjang.view.*
import kotlinx.android.synthetic.main.item_keranjang.view.imgMenuKeranjang

class CheckoutAdapter(val data: List<DataItemKeranjang?>?) : RecyclerView.Adapter<CheckoutAdapter.ViewCheckoutHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewCheckoutHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_checkout, parent, false)
        return ViewCheckoutHolder(view)
    }

    override fun onBindViewHolder(holder: ViewCheckoutHolder, position: Int) {
        val item = data?.get(position)

        holder.rasa.text = item?.rasa
        holder.jumlah.text = item?.jumlah + "X"
        holder.harga.text = item?.total_harga
        holder.tambahan.text = item?.tambahan
        Picasso.get().load(Url.urlImageMenu + item?.gambar)
            .into(holder.imgMenu)
    }

    override fun getItemCount(): Int  = data?.size ?: 0

    class ViewCheckoutHolder(val view: View): RecyclerView.ViewHolder(view) {
        val rasa = view.txtRasaCheck
        val harga = view.txtHargaCheck
        val jumlah = view.txtJmlCheck
        val imgMenu = view.imgMenuCheck
        val tambahan = view.txtTambahanCheck
        val context = view.context
    }
}