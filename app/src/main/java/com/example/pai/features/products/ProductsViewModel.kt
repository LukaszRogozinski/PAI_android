package com.example.pai.features.products

import android.app.Application
import androidx.lifecycle.*
import androidx.recyclerview.widget.DiffUtil
import com.example.pai.BR
import com.example.pai.R
import com.example.pai.database.getDatabase
import com.example.pai.domain.Product
import com.example.pai.network.*
import com.example.pai.repository.NetworkRepository
import kotlinx.coroutines.*
import me.tatarka.bindingcollectionadapter2.OnItemBind
import timber.log.Timber
import java.lang.Exception

class ProductsViewModel() : ViewModel() {

    private val productsRepository = NetworkRepository()


    private val _eventNetworkError = MutableLiveData(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private val _isNetworkErrorShown = MutableLiveData(false)
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>>
        get() = _products

    private val _navigateToSelectedProduct = MutableLiveData<Product>()
    val navigateToSelectedProduct: LiveData<Product>
        get() = _navigateToSelectedProduct

//    private val _navigateToEditProduct = MutableLiveData<Boolean>()
//    val navigateToEditProduct: LiveData<Boolean>
//        get() = _navigateToEditProduct

    fun onSelectedProduct(item: Product) {
        _navigateToSelectedProduct.value = item
    }

    fun navigateToSelectedProductDone() {
        _navigateToSelectedProduct.value = null
    }

    val itemBinding: OnItemBind<Product> = OnItemBind { itemBinding, _, item ->
        itemBinding.set(BR.item, R.layout.product_item)
        itemBinding.bindExtra(BR.vm, this)
    }

    val diff = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    init {
        loadProductsFromNetwork()
    }

    private fun loadProductsFromNetwork() {
        viewModelScope.launch {
            try {
                val response = productsRepository.loadProducts()
                if (response.isSuccessful) {
                    _products.value = response.body()!!.asProductDomainModel()
                    _eventNetworkError.value = false
                    _isNetworkErrorShown.value = false
                } else {
                    Timber.e(response.errorBody().toString())
                    _eventNetworkError.value = true
                }
            } catch (e: Exception) {
                Timber.e(e)
                _eventNetworkError.value = true
            }
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

//    fun navigateToEditProduct() {
//        _navigateToEditProduct.value = true
//    }
//
//    fun navigateToEditProductFinish() {
//        _navigateToEditProduct.value = false
//    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    class Factory() : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProductsViewModel::class.java)) {
                return ProductsViewModel() as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}