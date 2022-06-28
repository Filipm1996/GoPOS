package com.example.gopos.di

import android.content.Context
import com.example.gopos.data.Retrofit.*
import com.example.gopos.data.Retrofit.api.ApiService
import com.example.gopos.data.Retrofit.api.RefreshTokenApi
import com.example.gopos.data.db.Item
import com.example.gopos.data.db.ObjectBox
import com.example.gopos.data.repositories.Repository
import com.example.gopos.data.repositories.RepositoryDefault
import com.example.gopos.utils.SharedPreferencesManager
import com.example.gopos.utils.SharedPreferencesManagerDefault
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.objectbox.Box
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Singleton
    @Provides
    fun provideOkHttpClient(
        tokenAuthenticator: TokenAuthenticator,
        serviceInterceptor: ServiceInterceptor
    ) : OkHttpClientClass = OkHttpClientClass(tokenAuthenticator,serviceInterceptor)

    @Singleton
    @Provides
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClientClass
    ) : RetrofitInstance = RetrofitInstance(okHttpClient)

    @Singleton
    @Provides
    fun provideApi (
        retrofitInstance: RetrofitInstance
    ) : ApiService = retrofitInstance.getApi()

    @Singleton
    @Provides
    fun provideSharedPreferencesManager(
        @ApplicationContext context : Context
    ) : SharedPreferencesManagerDefault = SharedPreferencesManager(context)

    @Singleton
    @Provides
    fun provideRefreshTokenApi() : RefreshTokenApi = RefreshTokenInstance().getApi()

    @Singleton
    @Provides
    fun provideRepository(
        apiService: ApiService,
    ) = Repository(apiService) as RepositoryDefault


    @Singleton
    @Provides
    fun provideTokenAuthenticator (
        refreshTokenApi: RefreshTokenApi,
        sharedPreferencesManager: SharedPreferencesManagerDefault
    ) = TokenAuthenticator(refreshTokenApi,sharedPreferencesManager)


    @Singleton
    @Provides
    fun provideBoxForItem() : Box<Item> = ObjectBox.createBoxForItem()

    @Singleton
    @Provides
    fun provideServiceInterceptor(
        sharedPreferencesManager: SharedPreferencesManagerDefault
    ) = ServiceInterceptor(sharedPreferencesManager)
}