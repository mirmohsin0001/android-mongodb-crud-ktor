package com.sparrow.mongodb_interaction

// Product.kt
import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse(
    val success: Boolean,
    val data: List<Product>
)

@Serializable
data class Product(
    val _id: String,
    val category: String,
    val name: String,
    val price: Int,
    val image: String,
    val __v: Int
)