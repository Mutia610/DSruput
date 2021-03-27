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
import kotlinx.android.synthetic.main.activity_detail_menu.*
import kotlinx.android.synthetic.main.activity_menu.view.*
import kotlinx.android.synthetic.main.item_menu.view.*
import kotlinx.android.synthetic.main.item_menu.view.txtRasa
import kotlinx.android.synthetic.main.item_menu.view.txtStatus1
import kotlinx.android.synthetic.main.item_menu.view.txtStatus2
import kotlinx.android.synthetic.main.item_menu.view.txtVarian
import java.util.*
import kotlin.coroutines.coroutineContext

class MenuAdapter(val menu: List<DataMenu?>?, val click: OnClickListener) :
    RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val varian = view.txtVarian
        val rasa = view.txtRasa
        val harga = view.txtHarga
        val status1 = view.txtStatus1
        val status2 = view.txtStatus2
        val imageMenu = view.imgMenu
        val btnTambah = view.btnTambah
        val frameFav = view.frameFav
        var frameButton = view.frameButton
        val favorite = view.favorite
        val favOutline = view.favOutline
        val btnAdd = view.btnAdd
        val btnMin = view.btnMin
        val txtJml = view.txtJml
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

            holder.btnTambah.setOnClickListener {
                holder.btnTambah.visibility = View.GONE
                holder.frameButton.visibility = View.VISIBLE
                click.btnTambah()

                holder.btnAdd.setOnClickListener {
                    var jmlPesanan = holder.txtJml.text.toString().toInt()
                    var jmlAdd = jmlPesanan + 1
                    holder.txtJml.text = jmlAdd.toString()
                    click.addJml()
                }

                holder.btnMin.setOnClickListener {
                    var jmlPesanan = holder.txtJml.text.toString().toInt()
                    var jmlMin = jmlPesanan-1

                    if (jmlMin < 1){
                        holder.btnTambah.visibility = View.VISIBLE
                        holder.frameButton.visibility = View.GONE
                    }else
                    {
                        holder.txtJml.text = jmlMin.toString()
                    }
                    click.minJml()
                }
            }
        } else if (item?.status.toString() == "Tidak Tersedia") {
            holder.status2.visibility = View.VISIBLE
            holder.status1.visibility = View.GONE

            holder.btnTambah.setOnClickListener {

            }
        }

        holder.view.setOnClickListener {
            click.detailMenu(item)
        }

        holder.frameFav.setOnClickListener {
            holder.favorite.visibility = View.VISIBLE
            holder.favOutline.visibility = View.GONE
        }

        holder.favorite.setOnClickListener {
            holder.favorite.visibility = View.GONE
            holder.favOutline.visibility = View.VISIBLE
        }

//        var namaOutlet = prefManager.getValueString("NAMA_OUTLET").toString()
//        holder.outlet.text = namaOutlet
    }

    override fun getItemCount(): Int = menu?.size ?: 0

    interface OnClickListener {
        fun detailMenu(data: DataMenu?)
        fun btnTambah()
        fun addJml()
        fun minJml()
    }
}