package com.mutia.dsruput.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mutia.dsruput.R
import com.mutia.dsruput.model.getMenu.DataMenu
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_menu.view.*

class MenuFavoriteAdapter(val data:List<DataMenu?>?, val click: OnClickListener): RecyclerView.Adapter<MenuFavoriteAdapter.FavoriteHolder>() {


    class FavoriteHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val varian = view.txtVarian
        val rasa = view.txtRasa
        val imageMenu = view.imgMenu
        val context = view.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_menu_favorite, parent,false)
        return FavoriteHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        val item = data?.get(position)

        holder.rasa.text = item?.rasa
        holder.varian.text = item?.varian

        Picasso.get().load("http://192.168.43.84/dsruput/img/menu/" + item?.gambar)
            .into(holder.imageMenu)

        holder.view.setOnClickListener{
            click.detailMenuFav(item)
        }

    }

    override fun getItemCount(): Int = data?.size ?: 0

    interface OnClickListener {
        fun detailMenuFav(data: DataMenu?)
    }
}