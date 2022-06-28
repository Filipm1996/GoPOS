package com.example.gopos.data.Retrofit.api




import com.example.gopos.data.Retrofit.entities.ApiResponse.ApiResponse
import com.example.gopos.data.Retrofit.entities.TokenResponse
import retrofit2.Response
import retrofit2.http.*


interface ApiService {

    @GET("oauth/token?grant_type=password")
    suspend fun getToken(@Query("password" )password : String, @Query("client_secret") client_secret  : String,
                         @Query("client_id") clientId : String, @Query("username") username : String): Response<TokenResponse>

    @Headers(
        "accept: application/json"
    )
    @GET("/api/v3/27/items")
    suspend fun getItems(@Query("include") include : String ) : ApiResponse

}