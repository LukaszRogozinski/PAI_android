package com.example.pai.features.products.edit

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.pai.database.getDatabase
import com.example.pai.domain.Warehouse
import com.example.pai.repository.ProductsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class EditProductViewModel(app: Application) : AndroidViewModel(app) {

    private val productsRepository = ProductsRepository(getDatabase(app))

    private val viewModelJob = Job()

    private val viewModelIOScope = CoroutineScope(viewModelJob + Dispatchers.IO)

    private val _isWarehousesLoaded = MutableLiveData<Boolean>()
    val isWarehousesLoaded: LiveData<Boolean>
        get() = _isWarehousesLoaded

     var warehouses: List<Int> = arrayListOf(1,2,3)// = productsRepository.warehouses.map { it.map { it.id } }.value
    val status: List<String> = arrayListOf("available", "unavailable")
    var productTypes: List<Int> = arrayListOf(1,2,3,4)
    var _waregouseee = MutableLiveData<List<Warehouse>>()


    init {
       // loadWarehouses()
        Log.e("TA0","")
    }

     fun loadWarehouses() {
        viewModelIOScope.launch {
            try {
                warehouses = productsRepository.getWarehousesFromDatabase().map { it.id}// { it.id } }.value!!
                Log.e("TAG", "item loaded")
            } catch (e: Exception) {
                Log.e("TAG", e.message)
            }
        }
    }

    fun isWarehousesLoadedFinish() {
        _isWarehousesLoaded.value = null
    }


    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(EditProductViewModel::class.java)) {
                return EditProductViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}