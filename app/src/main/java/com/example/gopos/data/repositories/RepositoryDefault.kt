package com.example.gopos.data.repositories

import com.example.gopos.common.Resource
import com.example.gopos.data.Retrofit.entities.ApiResponse.ApiResponse
import com.example.gopos.data.Retrofit.entities.TokenResponse
import kotlinx.coroutines.flow.Flow


interface RepositoryDefault {

    suspend fun getTokenFromServer(login : String, password : String) : Resource<TokenResponse>

    suspend fun getItemsFromAPI(token:String) : Resource<ApiResponse>
}