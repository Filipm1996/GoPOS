package com.example.gopos.data.db

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Item (

    @Id
    var id : Long = 0,
    var name : String? = null,
    var price : String? = null,
    var vat : String? = null,
    var category : String? = null,
    var imageLink : String? = null
    )