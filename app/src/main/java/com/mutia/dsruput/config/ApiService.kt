package com.mutia.dsruput.config

import com.mutia.dsruput.model.*
import com.mutia.dsruput.model.action.ResponseAction
import com.mutia.dsruput.model.getData.ResponseGetData
import com.mutia.dsruput.model.getDataKeranjang.ResponseGetDataKeranjang
import com.mutia.dsruput.model.getMenu.ResponseGetMenu
import com.mutia.dsruput.model.login.ResponseLogin
import com.mutia.dsruput.model.riwayat.ResponseDataRiwayat
import io.reactivex.rxjava3.core.Flowable
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    //getDataOutlet
    @GET("getDataOutlet.php")
    fun getData(): Call<ResponseGetData>

    //getDataById
    @GET("getDataOutlet.php")
    fun getDataById(@Query("kode_outlet")kode_outlet:String): Call<ResponseGetData>

    //getDataMenu
    @GET("getDataMenu.php")
    fun getMenu(@Query("kode_outlet")kode_outlet:String): Call<ResponseGetMenu>

    //Insert Data Keranjang
    @FormUrlEncoded
    @POST("insertKeranjang.php")
    fun insertKeranjang(@Field("id_menu")id_menu:String,
                 @Field("id_outlet")id_outlet:String,
                 @Field("id_user")id_user:String,
                 @Field("tambahan")tambahan:String,
                 @Field("jumlah")jumlah:String,
                 @Field("total_harga")total_harga:String,): Call<ResponseAction>

    //getDataKeranjang
    @GET("getDataKeranjang.php")
    fun getDataKeranjang(@Query("id_user")id_user:String): Call<ResponseGetDataKeranjang>

    //delete data keranjang
    @FormUrlEncoded
    @POST("deleteKeranjang.php")
    fun deleteKeranjang(@Field("id_keranjang") id_keranjang : String): Call<ResponseGetDataKeranjang>

    //update data keranjang
    @FormUrlEncoded
    @POST("updateKeranjang.php")
    fun updateKeranjang(@Field("id_keranjang") id_keranjang : String,
                        @Field("tambahan") tambahan: String,
                        @Field("jumlah") jumlah: String,
                        @Field("total_harga") total_harga: String): Call<ResponseGetDataKeranjang>

    //update jumlah data keranjang
    @FormUrlEncoded
    @POST("updateJmlKeranjang.php")
    fun updateJmlKeranjang(@Field("id_menu") id_keranjang : String,
                        @Field("jumlah") jumlah: String,
                        @Field("total_harga") total_harga: String): Call<ResponseGetDataKeranjang>

    //Insert Jumlah Item Keranjang
    @FormUrlEncoded
    @POST("insertBagShop.php")
    fun insertBagShop(@Field("id_costumer")id_costumer:String,
                      @Field("jumlah")jumlah:String): Call<ResponseAction>

    //Get Data Riwayat
    @GET("getOrders.php")
    fun getOrders(@Query("id_costumer")id_costumer:String): Call<ResponseDataRiwayat>

    //Get Data Detail Riwayat
    @GET("getOrderDetail.php")
    fun getOrderDetail(@Query("id_order")id_order:String): Call<ResponseDetailRiwayat>

    //get Data Detail Order
    @GET("getDetailOrder.php")
    fun getDetailOrder(@Query("id_order")id_order:String): Call<ResponseDetailOrder>

    //Insert Orders
    @FormUrlEncoded
    @POST("insertOrders.php")
    fun insertOrders(@Field("id_costumer")id_costumer:String,
                     @Field("nama_costumer")nama_costumer:String,
                      @Field("total")total:String,
                     @Field("alamat_customer")alamat_customer:String,
                     @Field("total_bayar")total_bayar:String,
                     @Field("metode")metode:String): Call<ResponseActionOrders>

    //Insert Konfirmasi Pembayaran
    @FormUrlEncoded
    @POST("konfirmasi.php")
    fun konfirmasi(@Field("id_order")id_order:String,
                   @Field("bukti_bayar")bukti_bayar:String,
                   @Field("total_bayar")total_bayar:String): Call<ResponseActionKonfirmasi>

    //Registrasi
    @FormUrlEncoded
    @POST("register.php")
    fun register(@Field("username")username:String,
                 @Field("email")email:String,
                 @Field("password")password:String): Call<ResponseAction>

    //Login
    @FormUrlEncoded
    @POST("login.php")
    fun login(@Field("email")email:String,
              @Field("password")password:String): Call<ResponseLogin>

    //getDataUser
    @GET("getUsers.php")
    fun getUsers(@Query("id")id:String): Call<ResponseGetUsers>

    //Update Users With Password
    @FormUrlEncoded
    @POST("updateUsers.php")
    fun updateUsersPass(@Field("id") id : String,
                        @Field("username")username:String,
                        @Field("email")email:String,
                        @Field("password")password:String,
                        @Field("no_telp")no_telp:String,
                        @Field("jenis_kelamin")jenis_kelamin:String): Call<ResponseAction>

    //Update Users Without Password
    @FormUrlEncoded
    @POST("updateUsers.php")
    fun updateUsers(@Field("id") id : String,
                    @Field("username")username:String,
                    @Field("email")email:String,
                    @Field("no_telp")no_telp:String,
                    @Field("jenis_kelamin")jenis_kelamin:String): Call<ResponseAction>

    //getBerita
    @GET("getBerita.php")
    fun getDataBerita(): Call<ResponseGetDatBerita>
}