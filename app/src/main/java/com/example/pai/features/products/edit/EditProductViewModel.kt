package com.example.pai.features.products.edit

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.pai.database.getDatabase
import com.example.pai.domain.Product
import com.example.pai.domain.Warehouse
import com.example.pai.domain.asProductDtoToSave
import com.example.pai.network.ProductDomainToDto
import com.example.pai.repository.ProductsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception
import java.time.LocalDateTime

class EditProductViewModel(app: Application, val product: Product?, val isNewProduct: Boolean) :
    AndroidViewModel(app) {

    private val productsRepository = ProductsRepository(getDatabase(app))

    private val viewModelJob = Job()

    private val viewModelIOScope = CoroutineScope(viewModelJob + Dispatchers.IO)

    var productDtoToSave: ProductDomainToDto? = product?.asProductDtoToSave() ?: ProductDomainToDto(
        createDate = LocalDateTime.now().toString(),
        lastUpdate = LocalDateTime.now().toString()
    )

    var warehouses: List<Int> =
        arrayListOf(1, 2, 3)// = productsRepository.warehouses.map { it.map { it.id } }.value
    val status: List<String> = arrayListOf("available", "unavailable")
    var productTypes: List<Int> = arrayListOf(1, 2, 3, 4)


    init {
        //showSpinners()
        loadWarehouses()
        Log.e("TA0", "")
    }

    fun showSpinners() {
        viewModelScope.launch {

            val a = viewModelIOScope.launch {
                try {
                    warehouses = productsRepository.getWarehousesFromDatabase().map { it.id }
                    Log.e("TAG", "item loaded")
                } catch (e: Exception) {
                    Log.e("TAG", e.message)
                }
            }
            a.join()
        }


    }

    fun loadWarehouses() {
        viewModelIOScope.launch {
            try {
                warehouses = productsRepository.getWarehousesFromDatabase()
                    .map { it.id }// { it.id } }.value!!
                Log.e("TAG", "item loaded")
            } catch (e: Exception) {
                Log.e("TAG", e.message)
            }
        }
    }

    fun saveProduct() {
        viewModelScope.launch {
            try {
                if (isNewProduct) {
                    productsRepository.addProduct(productDtoToSave!!)
                } else {
                    productsRepository.updateProduct(productDtoToSave!!)
                }
            } catch (e: Exception) {
                Log.e("TAG", e.message)
            }
        }
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