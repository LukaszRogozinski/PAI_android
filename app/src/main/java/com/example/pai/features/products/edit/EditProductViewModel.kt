package com.example.pai.features.products.edit

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.pai.database.getDatabase
import com.example.pai.domain.Product
import com.example.pai.domain.Warehouse
import com.example.pai.domain.asProductDtoToSave
import com.example.pai.network.ProductDomainToDto
import com.example.pai.network.asDomainModel
import com.example.pai.network.asProductTypeDomainModel
import com.example.pai.repository.ProductsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception
import java.time.LocalDateTime

class EditProductViewModel(app: Application, val product: Product?, val isNewProduct: Boolean) :
    AndroidViewModel(app) {

    private val productsRepository = ProductsRepository(getDatabase(app))

    private val _warehousesObservable = MutableLiveData<List<Int>>()
    val warehousesObservable: LiveData<List<Int>>
        get() = _warehousesObservable

    private val _productTypesObservable = MutableLiveData<List<Int>>()
    val productTypesObservable: LiveData<List<Int>>
        get() = _productTypesObservable

    private val _saveProductResponse = MutableLiveData<Boolean>()
    val saveProductResponse: LiveData<Boolean>
        get() = _saveProductResponse

    private val _updateProductResponse = MutableLiveData<Boolean>()
    val updateProductResponse: LiveData<Boolean>
        get() = _updateProductResponse


    var productDtoToSave: ProductDomainToDto? = product?.asProductDtoToSave() ?: ProductDomainToDto(
        createDate = LocalDateTime.now().toString(),
        lastUpdate = LocalDateTime.now().toString()
    )

    val status: List<String> = arrayListOf("available", "unavailable")

    init {
        loadWarehousesFromNetwork()
        loadProductTypesFromNetwork()
    }

    fun loadWarehousesFromNetwork() {
        viewModelScope.launch {
            val response = productsRepository.getWarehousesFromNetwork()
            if (response.isSuccessful) {
                _warehousesObservable.value =
                    response.body()!!.asDomainModel().map { it.id }.reversed()
            } else {
                Timber.e("could not load warehouses from server")
            }
        }
    }

    fun loadProductTypesFromNetwork() {
        viewModelScope.launch {
            val response = productsRepository.getProductTypesFromNetwork()
            if (response.isSuccessful) {
                _productTypesObservable.value =
                    response.body()!!.asProductTypeDomainModel().map { it.id }
            } else {
                Timber.e("Failed to load product types from server")
            }
        }
    }

    fun setCurrentProductType(): Int {
        return _productTypesObservable.value!!.indexOf(product?.productType?.id ?: 0)
    }

    fun setCurrentWarehouseType(): Int {
        return _warehousesObservable.value!!.indexOf(product?.warehouse?.id ?: 0)
    }

    fun setCurrentStatus(): Int {
        return status.indexOf(product?.status ?: "available")
    }

    fun saveProduct() {
        viewModelScope.launch {
            try {
                if (isNewProduct) {
                    val response = productsRepository.addProduct(productDtoToSave!!)
                    if (response.isSuccessful) {
                        _saveProductResponse.value = true
                    } else {
                        Timber.e(response.errorBody().toString())
                    }
                } else {
                    val response = productsRepository.updateProduct(productDtoToSave!!)
                    if (response.isSuccessful) {
                        _updateProductResponse.value = true
                    } else {
                        Timber.e(response.errorBody().toString())
                    }
                }
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    fun saveProductResponseFinish() {
        _saveProductResponse.value = false
        Timber.i("saveProductResponse value= ${_saveProductResponse.value}")
    }

    fun updateProductResponseFinish() {
        _updateProductResponse.value = false
        Timber.i("updateProductResponse value= ${_updateProductResponse.value}")
    }

    class Factory(val app: Application, val product: Product?, val isNewProduct: Boolean) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(EditProductViewModel::class.java)) {
                return EditProductViewModel(app, product, isNewProduct) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}