package com.example.gopos.utils

interface SharedPreferencesManagerDefault {

    fun saveAuthToken(token : String)

    fun saveRefreshToken(refreshToken : String)

    fun getAuthToken(): String?

    fun getRefreshToken() : String?

    fun removeAll()
}