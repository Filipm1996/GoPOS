package com.example.gopos

import android.app.Application
import com.example.gopos.data.db.ObjectBox
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        ObjectBox.init(this)
    }
}
