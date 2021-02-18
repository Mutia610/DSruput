package com.mutia.dsruput.config

import com.mutia.dsruput.model.action.ResponseAction
import com.mutia.dsruput.model.getData.ResponseGetData
import com.mutia.dsruput.model.getMenu.ResponseGetMenu
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