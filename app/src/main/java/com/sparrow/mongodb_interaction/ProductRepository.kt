package com.sparrow.mongodb_interaction

class ProductRepository {
    suspend fun getProducts(): List<Product> {
        return ApiClient.getProducts()
    }
}