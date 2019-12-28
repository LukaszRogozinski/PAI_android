package com.example.pai.features.users.changepassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pai.domain.LoggedUser
import com.example.pai.domain.NewPassword
import com.example.pai.network.asUpdatePasswordDto
import com.example.pai.repository.NetworkRepository
import com.example.pai.repository.SessionRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception

class ChangeUserPasswordViewModel(private val networkRepository: NetworkRepository, val sessionRepository: SessionRepository) : ViewModel() {

    private val _navBack = MutableLiveData<Boolean>()
    val navBack: LiveData<Boolean>
        get() = _navBack

    var newPassword = NewPassword()

    fun setUsername(username: String) {
        newPassword.username = username
    }

    fun navBackDone() {
        _navBack.value = false
    }

    fun saveNewPassword() {
        viewModelScope.launch {
            try {
                val updatePasswordDto = newPassword.asUpdatePasswordDto()
                val response = networkRepository.updatePasswordByAdminNetwork(updatePasswordDto)
                if (response.isSuccessful) {
                    print("yea")
                    _navBack.postValue(true)
                } else {
                    print("buu")
                }
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    fun getLoggedUser() : LoggedUser {
        return sessionRepository.currentUser!!
    }

}