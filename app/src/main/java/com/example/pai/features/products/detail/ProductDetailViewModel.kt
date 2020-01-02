package com.example.pai.features.products.detail

import androidx.lifecycle.*
import com.example.pai.domain.Product
import com.example.pai.domain.User
import com.example.pai.repository.ProductRepository
import com.example.pai.repository.SessionRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import timber.log.Timber

class ProductDetailViewModel(private val productRepository: ProductRepository, val sessionRepository: SessionRepository) : ViewModel() {

     private lateinit var product: Product

    fun setProduct(product: Product) {
        this.product = product
    }

    fun getProduct(): Product {
        return product
    }

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

    fun showDeleteDialogCanceled() {
        _showDeleteDialog.value = false
    }

    fun deleteButtonClicked() {
        _showDeleteDialog.value = true
    }

    fun getLoggedUser() : User {
        return sessionRepository.user!!
    }

    fun isLoggedUserAdmin() : Boolean = sessionRepository.isAdmin()

    fun deleteProduct() {
        viewModelScope.launch {
            try {
                val response = productRepository.deleteProduct(sessionRepository.token!!, product.id)
                if (response.isSuccessful) {
                    _deleteResponse.postValue(true)
                    _showDeleteDialog.value = false
                    Timber.i("deleteResponse value= ${_deleteResponse.value}")
                }
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
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

//    class Factory(val product: Product?) : ViewModelProvider.Factory {
//        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//            if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)) {
//                return ProductDetailViewModel(product) as T
//            }
//            throw IllegalArgumentException("Unable to construct viewmodel")
//        }
//    }
}