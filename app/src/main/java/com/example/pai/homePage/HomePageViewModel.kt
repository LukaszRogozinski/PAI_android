package com.example.pai.homePage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomePageViewModel : ViewModel() {
    private val _example = MutableLiveData<String>()
    val example : LiveData<String>
        get() = _example

    init {
        _example.value = "Welcome to HomePageViewModel page!"
    }
}