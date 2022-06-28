package com.example.gopos.data.Retrofit


import com.example.gopos.common.Constants
import com.example.gopos.common.Resource
import com.example.gopos.data.Retrofit.api.RefreshTokenApi
import com.example.gopos.data.Retrofit.entities.TokenResponse
import com.example.gopos.utils.SharedPreferencesManagerDefault
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import java.lang.Exception
import javax.inject.Inject


class TokenAuthenticator @Inject constructor (private val refreshTokenApi: RefreshTokenApi, private val sharedPreferencesManager: SharedPreferencesManagerDefault) : Authenticator {



    override fun authenticate(route: Route?, response: Response): Request? {

                return runBlocking {
                    when (val tokenResponse = getUpdatedToken()) {
                        is Resource.Success -> {
                            tokenResponse.data?.let {
                                sharedPreferencesManager.saveAuthToken(
                                    it.access_token!!
                                )
                            }
                            tokenResponse.data?.let {
                                sharedPreferencesManager.saveRefreshToken(
                                    it.refresh_token!!
                                )
                            }
                            response.request().newBuilder()
                                .header("Authorization", "Bearer ${tokenResponse.data?.access_token}")
                                .build()
                        }
                        else -> null
                    }
                }
    }


    private suspend fun getUpdatedToken(): Resource<TokenResponse>{
        val refreshToken = sharedPreferencesManager.getRefreshToken() ?: ""
        return try {
            val response = refreshTokenApi.refreshToken(refreshToken,Constants.clientSecret,Constants.clientId)
            response
        } catch (e: Exception){
            Resource.Error(e.message.toString())
        }
    }


}