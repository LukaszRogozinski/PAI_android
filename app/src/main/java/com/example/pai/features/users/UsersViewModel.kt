package com.example.pai.features.users

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.pai.database.getDatabase
import com.example.pai.repository.ProductsRepository
import kotlinx.coroutines.launch
//testowy vievmodel - na razie nie usuwam
class UsersViewModel(app: Application) : ViewModel() {

    private val productsRepository = ProductsRepository(getDatabase(app))


    init {
        viewModelScope.launch {
            val b = productsRepository.getProductType(1)

            Log.i("TAG", "")
        }
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UsersViewModel::class.java)) {
                return UsersViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}