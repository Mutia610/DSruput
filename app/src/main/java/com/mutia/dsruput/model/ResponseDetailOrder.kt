package com.mutia.dsruput.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseDetailOrder(
//	val data: List<DataItemDetailOrder?>? = null,
	val message: String? = null,
	val isSuccess: Boolean? = null,
	val username: String? = null,
	val alamat: String? = null,
	val status: String? = null,
	val total_harga: String? = null,
	val ongkir: String? = null,
	val total_bayar: String? = null
) : Parcelable

//@Parcelize
//data class DataItemDetailOrder(
//	val alamatCustomer: String?,
//	val metodePembayaran: String?,
//	val idOrder: String?,
//	val ongkosKirim: String?,
//	val idCostumer: String?,
//	val jam: String?,
//	val totalHarga: String?,
//	val tanggal: String?,
//	val totalBayar: String?,
//	val namaCostumer: String?,
//	val status: String?
//) : Parcelable
