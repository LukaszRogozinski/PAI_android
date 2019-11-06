package com.example.pai.features.products.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.pai.database.getDatabase
import com.example.pai.domain.Product
import com.example.pai.repository.ProductsRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class ProductDetailViewModel(app: Application, val product: Product?) : ViewModel() {

    private val productsRepository = ProductsRepository(getDatabase(app))

    fun deleteProduct() {
        viewModelScope.launch {
            try {
                productsRepository.deleteProduct(product!!.id)
            } catch (e: Exception) {
                Log.e("TAG", e.message)
            }
        }
    }

    class Factory(val app: Application, val product: Product?) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)) {
                return ProductDetailViewModel(app, product) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}