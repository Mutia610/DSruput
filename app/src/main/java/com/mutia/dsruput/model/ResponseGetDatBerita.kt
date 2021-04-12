package com.mutia.dsruput.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseGetDatBerita(

	@field:SerializedName("data")
	val data: List<DataItemBerita?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("isSuccess")
	val isSuccess: Boolean? = null
) : Parcelable

@Parcelize
data class DataItemBerita(

	@field:SerializedName("keterangan")
	val keterangan: String? = null,

	@field:SerializedName("jam")
	val jam: String? = null,

	@field:SerializedName("img_berita")
	val imgBerita: String? = null,

	@field:SerializedName("tanggal")
	val tanggal: String? = null,

	@field:SerializedName("id_berita")
	val idBerita: String? = null
) : Parcelable
