package com.example.gopos.data.Retrofit

import com.example.gopos.common.Constants
import com.example.gopos.data.Retrofit.api.RefreshTokenApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RefreshTokenInstance {

    fun getApi() :  RefreshTokenApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RefreshTokenApi::class.java)
    }
}