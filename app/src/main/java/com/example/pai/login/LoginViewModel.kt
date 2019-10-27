package com.example.pai.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {


     val username = MutableLiveData<String>()
//    val username : LiveData<String>
//        get() = _username

    val password = MutableLiveData<String>()
//    val password : LiveData<String>
//        get() = _password

}