package com.example.pai.features.products.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pai.domain.Product

class ProductDetailViewModel(val product: Product?) : ViewModel() {



    class Factory(val product: Product?) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)) {
                return ProductDetailViewModel(product) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}