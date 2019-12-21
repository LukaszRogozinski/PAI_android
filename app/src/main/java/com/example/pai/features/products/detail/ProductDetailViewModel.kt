package com.example.pai.features.products.detail

import android.app.Application
import androidx.lifecycle.*
import com.example.pai.database.getDatabase
import com.example.pai.domain.Product
import com.example.pai.repository.NetworkRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class ProductDetailViewModel(val product: Product?) : ViewModel() {

    private val productsRepository = NetworkRepository()

//    private val _navigateToEditProduct = MutableLiveData<Boolean>()
//    val navigateToEditProduct: LiveData<Boolean>
//        get() = _navigateToEditProduct

    private val _deleteResponse = MutableLiveData<Boolean>()
    val deleteResponse: LiveData<Boolean>
        get() = _deleteResponse

    private val _showDeleteDialog = MutableLiveData(false)
    val showDeleteDialog: LiveData<Boolean>
        get() = _showDeleteDialog

    fun onDeleteResponseFinish() {
        _deleteResponse.value = false
        Timber.i("deleteResponse value= ${_deleteResponse.value}")
    }

    fun deleteButtonClicked() {
        _showDeleteDialog.value = true
    }

    fun deleteProduct() {
        viewModelScope.launch {
            try {
                val response = productsRepository.deleteProduct(product!!.id)
                if (response.isSuccessful) {
                    _deleteResponse.postValue(true)
                    Timber.i("deleteResponse value= ${_deleteResponse.value}")
                }
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

//    fun navigateToEditProduct() {
//        _navigateToEditProduct.value = true
//        Timber.i("navigateToEditProduct value= ${_navigateToEditProduct.value}")
//
//    }
//
//    fun navigateToEditProductFinish() {
//        _navigateToEditProduct.value = false
//        Timber.i("navigateToEditProduct value= ${_navigateToEditProduct.value}")
//    }

    class Factory(val product: Product?) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)) {
                return ProductDetailViewModel(product) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}