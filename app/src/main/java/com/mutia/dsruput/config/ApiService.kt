package com.mutia.dsruput.config

import com.mutia.dsruput.model.action.ResponseAction
import com.mutia.dsruput.model.getData.ResponseGetData
import com.mutia.dsruput.model.getDataKeranjang.ResponseGetDataKeranjang
import com.mutia.dsruput.model.getMenu.ResponseGetMenu
import com.mutia.dsruput.model.login.ResponseLogin
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

    //Insert Jumlah Item Keranjang
    @FormUrlEncoded
    @POST("insertBagShop.php")
    fun insertBagShop(@Field("id_costumer")id_costumer:String,
                      @Field("jumlah")jumlah:String,): Call<ResponseAction>

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











/*
    //insert
    @FormUrlEncoded
    @POST("insertOutlet.php")
    fun  insertOutlet(@Field("nama_outlet") nama_outlet : String,
                      @Field("alamat") alamat : String,
                      @Field("gambar_outlet") gambar_outlet : String): Flowable<ResponseAction>

    //update
    @FormUrlEncoded
    @POST("updateOutlet.php")
    fun updateOutlet(@Field("kode_outlet") kode_outlet : String,
                    @Field("nama_outlet") nama_outlet: String,
                    @Field("alamat") alamat: String,
                    @Field("gambar_outlet") gambar_outlet: String): Flowable<ResponseAction>

    //delete
    @FormUrlEncoded
    @POST("deleteOutlet.php")
    fun deleteOutlet(@Field("kode_outlet") kode_outlet: String) : Flowable<ResponseAction>
*/
}