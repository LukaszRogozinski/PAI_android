package com.example.pai.features.products

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.recyclerview.widget.DiffUtil
import com.example.pai.BR
import com.example.pai.R
import com.example.pai.database.getDatabase
import com.example.pai.domain.Product
import com.example.pai.network.PaiApi
import com.example.pai.network.ProductDto
import com.example.pai.network.ProductTypeDto
import com.example.pai.network.WarehouseDto
import com.example.pai.repository.ProductsRepository
import kotlinx.coroutines.*
import me.tatarka.bindingcollectionadapter2.OnItemBind
import java.lang.Exception

class ProductsViewModel(application: Application) : AndroidViewModel(application) {

    private val productsRepository = ProductsRepository(getDatabase(application))

    private val viewModelJob = Job()

    private val viewModelScope2 = CoroutineScope(viewModelJob + Dispatchers.IO)

    var view: ProductsView? = null

    val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>>
        get() = _products

    val itemBinding: OnItemBind<Product> = OnItemBind { itemBinding, _, item ->
        itemBinding.set(BR.item, R.layout.product_item)
        itemBinding.bindExtra(BR.listener, object : OnProductClickedListener {
            override fun onProductClicked(product: Product) {
                view?.navigateToProductDetails(product)
            }
        })
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
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            val loadProductsAndPutToDatabase = viewModelScope.launch {
                try {
                    productsRepository.refreshProductDatabase()
                } catch (e: Exception) {
                    Log.e("TAG", e.message)
                }
            }
            loadProductsAndPutToDatabase.join()

            viewModelScope2.launch {
                try {
                    _products.postValue(productsRepository.getProductsFromDatabase())
                } catch (e: Exception) {
                    Log.e("TAG", e.message)
                }
            }
        }
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProductsViewModel::class.java)) {
                return ProductsViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}