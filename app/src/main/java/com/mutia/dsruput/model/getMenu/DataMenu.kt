package com.mutia.dsruput.model.getMenu

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataMenu(

	@field:SerializedName("id_menu")
	val idMenu: String? = null,

	@field:SerializedName("harga")
	val harga: String? = null,

	@field:SerializedName("kode_outlet")
	val kodeOutlet: String? = null,

	@field:SerializedName("varian")
	val varian: String? = null,

	@field:SerializedName("rasa")
	val rasa: String? = null,

	@field:SerializedName("gambar")
	val gambar: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable