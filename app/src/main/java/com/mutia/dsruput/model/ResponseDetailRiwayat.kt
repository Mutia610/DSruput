package com.mutia.dsruput.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseDetailRiwayat(

	@field:SerializedName("data")
	val data: List<DataItemDetailRiwayat?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("isSuccess")
	val isSuccess: Boolean? = null
) : Parcelable

@Parcelize
data class DataItemDetailRiwayat(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("id_menu")
	val idMenu: String? = null,

	@field:SerializedName("jumlah")
	val jumlah: String? = null,

	@field:SerializedName("harga")
	val harga: String? = null,

	@field:SerializedName("rasa")
	val rasa: String? = null,

	@field:SerializedName("nama_outlet")
	val namaOutlet: String? = null,

	@field:SerializedName("gambar")
	val gambar: String? = null,

	@field:SerializedName("topping_tambahan")
	val toppingTambahan: String? = null
) : Parcelable
