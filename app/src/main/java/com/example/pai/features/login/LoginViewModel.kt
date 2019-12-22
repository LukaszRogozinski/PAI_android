package com.example.pai.features.login

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pai.repository.NetworkRepository
import com.example.pai.repository.SessionRepository
import timber.log.Timber

class LoginViewModel(private val networkRepository: NetworkRepository) : ViewModel() {

//    private val networkRepository = NetworkRepository()
  //  private val sessionRepository = SessionRepository()

    private val _navigateToProducts = MutableLiveData<Boolean>()
    val navigateToProducts: LiveData<Boolean>
        get() = _navigateToProducts

    val username = MutableLiveData<String>()
    val usernameError = ObservableField<String>()
    val password = MutableLiveData<String>()
    val passwordError = ObservableField<String>()


    fun onLoginButtonClicked() {
        var valid = true
        if (username.value.isNullOrBlank()) {
            usernameError.set("Username can't be empty!")
            valid = false
        }
        if(password.value.isNullOrBlank()) {
            passwordError.set("Password can't be empty!")
            valid = false
        }
        if(valid){
            val response = networkRepository.logIn(username.value!!, password.value!!)
            if(response){
                onNavigateToProducts()
            }
        }
    }

    fun onNavigateToProducts() {
        _navigateToProducts.value = true
        Timber.i("navigateToProducts value= ${_navigateToProducts.value}")
    }

    fun onNavigateToProductsComplete() {
        _navigateToProducts.value = false
        Timber.i("navigateToProducts value= ${_navigateToProducts.value}")
    }

}