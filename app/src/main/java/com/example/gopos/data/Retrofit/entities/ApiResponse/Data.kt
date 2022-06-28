package com.example.gopos.data.Retrofit.entities.ApiResponse

data class Data(
    val barcodes: List<String>,
    val category: Category,
    val category_id: Int,
    val color: String,
    val custom_fields: List<CustomField>,
    val description: String,
    val id: Int,
    val image_link: ImageLinkX,
    val item_group_id: Int,
    val joint_id: String,
    val label: String,
    val name: String,
    val price: Price,
    val reference_id: String,
    val sku: String,
    val status: String,
    val tax: Tax,
    val tax_id: Int,
    val translations: List<TranslationX>,
    val updated_at: String
)