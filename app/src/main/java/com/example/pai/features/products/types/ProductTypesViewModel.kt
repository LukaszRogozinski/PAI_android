package com.example.pai.features.products.types

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import com.example.pai.BR
import com.example.pai.R
import com.example.pai.domain.LoggedUser
import com.example.pai.domain.ProductType
import com.example.pai.network.asProductTypeDomainModel
import com.example.pai.repository.NetworkRepository
import com.example.pai.repository.SessionRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import me.tatarka.bindingcollectionadapter2.OnItemBind
import timber.log.Timber
import java.lang.Exception

class ProductTypesViewModel(private val networkRepository: NetworkRepository, val sessionRepository: SessionRepository) : ViewModel() {

    private val _eventNetworkError = MutableLiveData(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private val _isNetworkErrorShown = MutableLiveData(false)
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    private val _productTypes = MutableLiveData<List<ProductType>>()
    val productTypes: LiveData<List<ProductType>>
        get() = _productTypes

    init {
        loadProductTypesFromNetwork()
    }

    fun getLoggedUser() : LoggedUser {
        return sessionRepository.currentUser!!
    }

    val itemBinding: OnItemBind<ProductType> = OnItemBind { itemBinding, _, _ ->
        itemBinding.set(BR.item, R.layout.product_type_item)
//        itemBinding.bindExtra(BR.vm, this)
    }

    val diff = object : DiffUtil.ItemCallback<ProductType>() {
        override fun areItemsTheSame(oldItem: ProductType, newItem: ProductType): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductType, newItem: ProductType): Boolean {
            return oldItem == newItem
        }
    }

    private fun loadProductTypesFromNetwork() {
        viewModelScope.launch {
            try {
                val response = networkRepository.getProductTypesFromNetwork()
                if(response.isSuccessful){
                    _productTypes.value = response.body()!!.asProductTypeDomainModel()
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