package com.mutia.dsruput.config

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Network {
    fun getRetrofit() : Retrofit {
        return Retrofit.Builder().baseUrl(Url.baseUrl) //192.168.43.84 //192.168.100.143
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun service() : ApiService = getRetrofit().create(ApiService::class.java)
}