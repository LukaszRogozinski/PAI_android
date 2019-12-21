package com.example.pai.features.users.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pai.domain.User

class UserDetailViewModel(val user: User?) : ViewModel() {

    class Factory(val product: User?) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserDetailViewModel::class.java)) {
                return UserDetailViewModel(product) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}