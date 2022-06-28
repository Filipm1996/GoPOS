package com.example.gopos.data.Retrofit.api

import com.example.gopos.common.Resource
import com.example.gopos.data.Retrofit.entities.TokenResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RefreshTokenApi {

    @GET("oauth/token?grant_type=refresh_token")
    suspend fun refreshToken(@Query("refresh_token") refreshToken: String, @Query("client_secret") clientSecret : String,
                             @Query("client_id") clientId : String ) : Resource<TokenResponse>
}