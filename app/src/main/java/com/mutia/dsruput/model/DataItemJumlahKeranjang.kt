package com.mutia.dsruput.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataItemJumlahKeranjang (
    @field:SerializedName("id_bag_shop")
    val idBagShop: String? = null,

    @field:SerializedName("id_costumer")
    val idCostumer: String? = null,

    @field:SerializedName("jumlah")
    val jumlah: String? = null,
): Parcelable