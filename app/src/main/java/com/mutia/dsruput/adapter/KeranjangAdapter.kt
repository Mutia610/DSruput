package com.mutia.dsruput.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mutia.dsruput.R
import com.mutia.dsruput.config.Network
import com.mutia.dsruput.config.Url
import com.mutia.dsruput.model.getDataKeranjang.DataItemKeranjang
import com.mutia.dsruput.model.getDataKeranjang.ResponseGetDataKeranjang
import com.mutia.dsruput.model.getMenu.DataMenu
import com.mutia.dsruput.preferences.PrefManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_menu.*
import kotlinx.android.synthetic.main.activity_keranjang.view.*
import kotlinx.android.synthetic.main.item_keranjang.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KeranjangAdapter(val data: List<DataItemKeranjang?>?, val itemClick: OnClickListener) : RecyclerView.Adapter<KeranjangAdapter.ViewKeranjangHolder>() {

    private lateinit var prefManager: PrefManager

    class ViewKeranjangHolder(val view: View): RecyclerView.ViewHolder(view) {
        val rasa = view.txtRasaKrj
        val varian = view.txtVarianKrj
        val harga = view.txtHargaKrj
        val hargaSatuan = view.hargaSatuan
        val jumlah = view.txtJml
        val imgMenu = view.imgMenuKeranjang
        var btnAdd = view.btnAdd
        var btnMin = view.btnMin
        val btnDelete = view.btnDelete
        val tambahan = view.txtTambahanKrj
        val context = view.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewKeranjangHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_keranjang, parent, false)
        return KeranjangAdapter.ViewKeranjangHolder(view)
    }

    override fun onBindViewHolder(holder: ViewKeranjangHolder, position: Int) {
        val item = data?.get(position)

        var jml = item?.jumlah.toString().toInt()

        holder.rasa.text = item?.rasa
        holder.varian.text = item?.varian
        holder.jumlah.text = item?.jumlah
        holder.hargaSatuan.text = item?.harga
        holder.harga.text = item?.total_harga
        holder.tambahan.text = item?.tambahan

        Picasso.get().load(Url.urlImageMenu + item?.gambar)
            .into(holder.imgMenu)

        if (jml > 1){
            holder.hargaSatuan.visibility = View.VISIBLE
        }else{
            holder.hargaSatuan.visibility = View.GONE
        }

        holder.btnDelete.setOnClickListener {
            itemClick.hapus(item)
        }

        holder.view.setOnClickListener {
            itemClick.detail(item)
        }

        holder.btnAdd.setOnClickListener {

            var jmlAdd = holder.jumlah.text.toString().toInt() + 1
            var hargaSatuan = holder.hargaSatuan.text.toString().toInt()
            var harga = holder.harga.text.toString().toInt()

            var totHarga = (hargaSatuan + harga).toString()

            holder.jumlah.text = jmlAdd.toString()
            holder.harga.text = totHarga

            item?.jumlah = jmlAdd.toString()
            item?.total_harga = totHarga

            if (jmlAdd > 1){
                holder.hargaSatuan.visibility = View.VISIBLE
            }else{
                holder.hargaSatuan.visibility = View.GONE
            }

            updateKeranjang(item?.idKeranjang.toString(), item?.tambahan.toString(), holder.jumlah.text.toString(), holder.harga.text.toString())
            itemClick.updateHarga()
        }

        holder.btnMin.setOnClickListener {
            var jmlMin = holder.jumlah.text.toString().toInt()
            if (jmlMin > 1){
                var jmlM = holder.jumlah.text.toString().toInt() - 1
                var hargaSatuan = holder.hargaSatuan.text.toString().toInt()
                var harga = holder.harga.text.toString().toInt()

                var totHarga = (harga - hargaSatuan).toString()

                if (jmlM >= 1){
                    holder.jumlah.text = jmlM.toString()
                    holder.harga.text = totHarga

                    item?.jumlah = jmlM.toString()
                    item?.total_harga = totHarga

                    updateKeranjang(item?.idKeranjang.toString(), item?.tambahan.toString(), holder.jumlah.text.toString(), holder.harga.text.toString())

                    if (jmlM == 1){
                        holder.hargaSatuan.visibility = View.GONE
                    }

                    itemClick.updateHarga()
                }
            }else{

            }
        }
    }

    override fun getItemCount(): Int = data?.size ?: 0

    private fun updateKeranjang(id_keranjang: String?, tambahan: String?, jumlah: String?, total_harga: String?){

        val update = Network.service().updateKeranjang(id_keranjang ?: "", tambahan ?: "", jumlah ?: "", total_harga ?: "" )
        update.enqueue(object : Callback<ResponseGetDataKeranjang> {
            override fun onResponse(
                call: Call<ResponseGetDataKeranjang>,
                response: Response<ResponseGetDataKeranjang>
            ) {

            }

            override fun onFailure(call: Call<ResponseGetDataKeranjang>, t: Throwable) {
            }
        })
    }

    interface OnClickListener {
        fun hapus(item: DataItemKeranjang?)
        fun detail(item: DataItemKeranjang?)
        fun updateHarga()
    }
}
