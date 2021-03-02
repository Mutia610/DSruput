package com.mutia.dsruput.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mutia.dsruput.R
import com.mutia.dsruput.model.getData.DataItem
import com.mutia.dsruput.model.getMenu.DataMenu
import com.mutia.dsruput.preferences.PrefManager
import com.mutia.dsruput.view.MenuActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_menu.view.*
import kotlinx.android.synthetic.main.item_menu.view.*
import kotlinx.android.synthetic.main.item_menu.view.txtRasa
import kotlinx.android.synthetic.main.item_menu.view.txtStatus1
import kotlinx.android.synthetic.main.item_menu.view.txtStatus2
import kotlinx.android.synthetic.main.item_menu.view.txtVarian
import java.util.*
import kotlin.coroutines.coroutineContext

class MenuAdapter(val menu: List<DataMenu?>?, val click : OnClickListener) : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    lateinit var prefManager: PrefManager

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val varian = view.txtVarian
        val rasa = view.txtRasa
        val harga = view.txtHarga
        val status1 = view.txtStatus1
        val status2 = view.txtStatus2
        val imageMenu = view.imgMenu
        val btnTambah = view.btnTambah
        val context = view.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false)
        return MenuAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuAdapter.ViewHolder, position: Int) {
        val item = menu?.get(position)

        holder.rasa.text = item?.rasa
        holder.harga.text = item?.harga
        holder.varian.text = item?.varian

        Picasso.get().load("http://192.168.43.84/dsruput/img/menu/" + item?.gambar)
            .into(holder.imageMenu)

        if (item?.status.toString() == "Tersedia") {
            holder.status1.visibility = View.VISIBLE
            holder.status2.visibility = View.GONE
        } else if (item?.status.toString() == "Tidak Tersedia") {
            holder.status2.visibility = View.VISIBLE
            holder.status1.visibility = View.GONE
        }

        holder.view.setOnClickListener{
            click.detailMenu(item)
        }

        if (item?.status.toString() == "Tersedia"){
            holder.btnTambah.setOnClickListener {
                click.btnTambah()
            }
        }

//        var namaOutlet = prefManager.getValueString("NAMA_OUTLET").toString()
//        holder.outlet.text = namaOutlet
    }

    override fun getItemCount(): Int = menu?.size ?: 0

    interface OnClickListener{
        fun detailMenu(data: DataMenu?)
        fun btnTambah()
    }
}