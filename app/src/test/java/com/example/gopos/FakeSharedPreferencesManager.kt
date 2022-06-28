package com.example.gopos

import com.example.gopos.utils.SharedPreferencesManagerDefault

class FakeSharedPreferencesManager  : SharedPreferencesManagerDefault {
    private var authToken : String? = ""
    private var refreshToken : String? = ""
    override fun saveAuthToken(token: String) {
        this.authToken = token
    }

    override fun saveRefreshToken(refreshToken: String) {
        this.refreshToken = refreshToken
    }

    override fun getAuthToken(): String? {
        return authToken
    }

    override fun getRefreshToken(): String? {
        return refreshToken
    }

    override fun removeAll() {
        authToken = ""
        refreshToken = ""
    }
}