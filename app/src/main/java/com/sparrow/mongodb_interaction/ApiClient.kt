package com.sparrow.mongodb_interaction

import android.util.Log
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object ApiClient {
    private const val BASE_URL = "https://next-js-e-com-swart.vercel.app/api/" // Your actual URL here

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
                encodeDefaults = true
                coerceInputValues = true
            })
        }
        engine {
            requestTimeout = 30_000
        }
    }

    suspend fun getProducts(): List<Product> {
        try {
            val response: HttpResponse = client.get("${BASE_URL}products")
            val body: String = response.bodyAsText()
            Log.d("ApiClient", "Status: ${response.status}")
            Log.d("ApiClient", "Raw response: $body")
            val productResponse: ProductResponse = response.body() // Deserialize into ProductResponse
            return productResponse.data // Return the list of products
        } catch (e: Exception) {
            Log.e("ApiClient", "Request failed for ${BASE_URL}products", e)
            throw e
        }
    }
}