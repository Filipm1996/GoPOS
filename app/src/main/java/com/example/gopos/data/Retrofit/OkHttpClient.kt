package com.example.gopos.data.Retrofit

import okhttp3.OkHttpClient
import javax.inject.Inject

class OkHttpClientClass @Inject constructor(
    private val tokenAuthenticator: TokenAuthenticator,
    private val serviceInterceptor: ServiceInterceptor
) : OkHttpClient() {

   fun getClient() : OkHttpClient{
      return Builder()
           .authenticator(tokenAuthenticator)
           .addInterceptor(serviceInterceptor).build()
   }

}