package com.example.gopos.data.Retrofit


import com.example.gopos.utils.SharedPreferencesManagerDefault
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


class ServiceInterceptor @Inject constructor(private val sharedPreferencesManager: SharedPreferencesManagerDefault) : Interceptor {


    var token : String = ""

    fun getToken() {
       this.token = sharedPreferencesManager.getAuthToken() ?: ""
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        getToken()
        var request = chain.request()
            if (!token.isEmpty()) {
                val finalToken = "Bearer "+token
                request = request.newBuilder()
                    .addHeader("Authorization",finalToken)
                    .build()
            }
        return chain.proceed(request)
    }

}