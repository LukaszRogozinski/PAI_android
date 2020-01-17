package com.example.pai.features.login

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pai.network.asDomainModel
import com.example.pai.repository.SessionRepository
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber
import java.lang.Exception

class LoginViewModel(private val sessionRepository: SessionRepository) : ViewModel() {

    private val _navigateToProducts = MutableLiveData<Boolean>()
    val navigateToProducts: LiveData<Boolean>
        get() = _navigateToProducts

    private val _showErrorMessage = MutableLiveData<String>()
    val showErrorMessage: LiveData<String>
        get() = _showErrorMessage

    fun showErrorMessageDone() {
        _showErrorMessage.value = null
    }

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
        if (password.value.isNullOrBlank()) {
            passwordError.set("Password can't be empty!")
            valid = false
        }
        if (valid) {
            viewModelScope.launch {
                try {
                    val response = sessionRepository.logIn(username.value!!, password.value!!)
                    if (response.isSuccessful) {
                        sessionRepository.saveToken(response.body()!!.access_token)
                        sessionRepository.saveUsername(username.value!!)
                        val getUserResponse = sessionRepository.getLoggedUserNetwork(
                            sessionRepository.token!!,
                            username.value!!
                        )
                        if (getUserResponse.isSuccessful) {
                            sessionRepository.saveUser(getUserResponse.body()!!.asDomainModel())
                            onNavigateToProducts()
                        } else {

                            print("ouchInside")
                        }
                    } else {
                        val jsonObjectError = JSONObject(response.errorBody()!!.string())
                        val errorMessage: String = jsonObjectError.get("error_description") as String
                        _showErrorMessage.postValue(errorMessage)
                        print("ouch")
                    }

                } catch (e: Exception) {
                    Timber.e(e)
                }
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