package com.mutia.dsruput.model.getData

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataItem(

	@field:SerializedName("gambar_outlet")
	val gambarOutlet: String? = null,

	@field:SerializedName("kode_outlet")
	val kodeOutlet: String? = null,

	@field:SerializedName("nama_outlet")
	val namaOutlet: String? = null,

	@field:SerializedName("alamat")
	val alamat: String? = null,

	@field:SerializedName("latitude")
	val latitude: String? = null,

	@field:SerializedName("longtitude")
	val longtitude: String? = null,

	var jarak: String? = null

):Parcelable
