package com.example.pai.features.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import com.example.pai.BR
import com.example.pai.R
import com.example.pai.domain.Product
import com.example.pai.domain.User
import com.example.pai.network.asDomainModel
import com.example.pai.network.asProductDomainModel
import com.example.pai.repository.ProductRepository
import com.example.pai.repository.SessionRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import me.tatarka.bindingcollectionadapter2.OnItemBind
import timber.log.Timber
import kotlin.math.log

class ProductsViewModel(
    private val productRepository: ProductRepository,
    val sessionRepository: SessionRepository
) : ViewModel() {

    private val _eventNetworkError = MutableLiveData(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private val _isNetworkErrorShown = MutableLiveData(false)
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>>
        get() = _products

    private val _navigateToSelectedProduct = MutableLiveData<Product>()
    val navigateToSelectedProduct: LiveData<Product>
        get() = _navigateToSelectedProduct

    val itemBinding: OnItemBind<Product> = OnItemBind { itemBinding, _, _ ->
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

    fun onSelectedProduct(item: Product) {
        _navigateToSelectedProduct.value = item
    }

    fun navigateToSelectedProductDone() {
        _navigateToSelectedProduct.value = null
    }

    fun getLoggedUser(): User {
        var loggedUser: User? = null
        runBlocking {
            try {
                val response = sessionRepository.getLoggedUserNetwork(sessionRepository.token!!, sessionRepository.user!!.username!!)
                if(response.isSuccessful) {
                    loggedUser =  response.body()!!.asDomainModel()
                } else{
                    println("nie moge pobrac zalogowanego uzytkownika")
                }
            } catch (e: Exception) {
                println("getLoggedUser nie pyklo")
            }
        }
        return loggedUser!!
    }

    private fun loadProductsFromNetwork() {
        viewModelScope.launch {
            try {
                val response = productRepository.loadProducts(sessionRepository.token!!)
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

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}