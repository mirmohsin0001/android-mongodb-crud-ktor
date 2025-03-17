package com.sparrow.mongodb_interaction

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _isLoading = MutableStateFlow(true) // Loading state
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            try {
                _isLoading.value = true // Start loading
                val productList = repository.getProducts()
                Log.d("ProductViewModel", "Fetched products: $productList")
                _products.value = productList
                if (productList.isEmpty()) {
                    Log.w("ProductViewModel", "Product list is empty")
                }
            } catch (e: Exception) {
                Log.e("ProductViewModel", "Error fetching products", e)
                _products.value = emptyList()
            } finally {
                _isLoading.value = false // Stop loading
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val repository = ProductRepository()
                ProductViewModel(repository)
            }
        }
    }
}