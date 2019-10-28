package com.example.pai.features.products

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.recyclerview.widget.DiffUtil
import com.example.pai.BR
import com.example.pai.R
import com.example.pai.database.getDatabase
import com.example.pai.network.PaiApi
import com.example.pai.network.ProductDto
import com.example.pai.network.ProductTypeDto
import com.example.pai.network.WarehouseDto
import com.example.pai.repository.ProductsRepository
import kotlinx.coroutines.launch
import me.tatarka.bindingcollectionadapter2.OnItemBind
import java.lang.Exception

class ProductsViewModel(application: Application) : ViewModel() {
    private val _products = MutableLiveData<List<ProductDto>>()
    val products: LiveData<List<ProductDto>>
        get() = _products

    private val productsRepository = ProductsRepository(getDatabase(application))

    val itemBinding: OnItemBind<ProductDto> = OnItemBind { itemBinding, _, item ->
        itemBinding.set(BR.item, R.layout.product_item)
    }

    val diff = object : DiffUtil.ItemCallback<ProductDto>() {
        override fun areItemsTheSame(oldItem: ProductDto, newItem: ProductDto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductDto, newItem: ProductDto): Boolean {
            return oldItem == newItem
        }

    }

    init {
        //     loadAllProducts()
    }

    fun loadAllProducts() {
        viewModelScope.launch {
            try {
                productsRepository.refreshProductDatabase()
                _products.value = PaiApi.retrofitService.getProducts()
                Log.i("TAG", _products.toString())
            } catch (e: Exception) {
                Log.e("TAG", e.message)
            }
        }
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(ProductsViewModel::class.java)) {
                return ProductsViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }

    }
}