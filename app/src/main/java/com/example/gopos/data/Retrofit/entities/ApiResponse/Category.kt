package com.example.gopos.data.Retrofit.entities.ApiResponse

data class Category(
    val color: String,
    val description: String,
    val id: Int,
    val image_link: ImageLink,
    val label: String,
    val name: String,
    val status: String,
    val translations: List<Translation>,
    val updated_at: String
)