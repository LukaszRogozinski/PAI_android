package com.example.pai.features.users

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pai.database.getDatabase
import com.example.pai.repository.ProductsRepository

//testowy vievmodel - na razie nie usuwam
class UsersViewModel(app: Application) : ViewModel() {

    private val productsRepository = ProductsRepository(getDatabase(app))

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UsersViewModel::class.java)) {
                return UsersViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}