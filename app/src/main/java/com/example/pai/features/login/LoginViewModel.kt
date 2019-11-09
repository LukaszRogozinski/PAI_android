package com.example.pai.features.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class LoginViewModel : ViewModel() {

    private val _navigateToProducts = MutableLiveData<Boolean>()
    val navigateToProducts: LiveData<Boolean>
    get() = _navigateToProducts

    val username = MutableLiveData<String>()
//    val username : LiveData<String>
//        get() = _username

    val password = MutableLiveData<String>()
//    val password : LiveData<String>
//        get() = _password

    fun onNavigateToProducts() {
        _navigateToProducts.value = true
        Timber.i("navigateToProducts value= ${_navigateToProducts.value}")
    }

    fun onNavigateToProductsComplete() {
        _navigateToProducts.value = false
        Timber.i("navigateToProducts value= ${_navigateToProducts.value}")
    }

}