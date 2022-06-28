package com.example.gopos.data.db

import android.content.Context
import io.objectbox.Box
import io.objectbox.BoxStore

object ObjectBox {
    lateinit var store: BoxStore
        private set

    fun init(context: Context) {
        store = MyObjectBox.builder()
            .androidContext(context.applicationContext)
            .build()
    }

    fun createBoxForItem() : Box<Item> {
        return store.boxFor(Item::class.java)
    }
}