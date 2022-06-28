package com.example.gopos.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.gopos.R

class SharedPreferencesManager(context: Context) : SharedPreferencesManagerDefault {
    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    override fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString("USER_TOKEN", token)
        editor.apply()
    }
    override fun saveRefreshToken(refreshToken : String){
        val editor = prefs.edit()
        editor.putString("REFRESH_TOKEN", refreshToken)
        editor.apply()
    }
    override fun getAuthToken(): String? {
        return prefs.getString("USER_TOKEN", null)
    }

    override fun getRefreshToken() : String? {
        return prefs.getString("REFRESH_TOKEN", null)
    }

    override fun removeAll() {
        prefs.edit().clear().commit()
    }
}