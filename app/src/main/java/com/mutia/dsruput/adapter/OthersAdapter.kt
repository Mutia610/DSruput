package com.mutia.dsruput.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mutia.dsruput.R
import com.mutia.dsruput.model.others.DataOthers
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_menu.view.*
import kotlinx.android.synthetic.main.item_others.view.*
import java.lang.System.load

class OthersAdapter(val data:List<DataOthers?>?, var itemClick: OnClickListener): RecyclerView.Adapter<OthersAdapter.OthersHolder>(){

    class OthersHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val icon = view.icOthers
        val keterangan = view.textOthers
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OthersHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_others, parent, false)
        return OthersAdapter.OthersHolder(view)
    }

    override fun onBindViewHolder(holder: OthersHolder, position: Int) {
        val item = data?.get(position)

        holder.keterangan.text = item?.keterangan
        item?.icon?.let { holder.icon.setImageResource(it) }

        holder.view.setOnClickListener {
            itemClick.detail(item?.keterangan)
        }
    }

    override fun getItemCount(): Int = data?.size ?: 0

    interface OnClickListener{
        fun detail(keterangan: String?)
    }
}