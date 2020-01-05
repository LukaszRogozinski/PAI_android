package com.example.pai.features.users.changepassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pai.domain.NewPassword
import com.example.pai.domain.User
import com.example.pai.network.asUpdatePasswordDto
import com.example.pai.repository.SessionRepository
import com.example.pai.repository.UserRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception

class ChangeUserPasswordViewModel(
    private val userRepository: UserRepository,
    val sessionRepository: SessionRepository
) : ViewModel() {

    private val _navBack = MutableLiveData<Boolean>()
    val navBack: LiveData<Boolean>
        get() = _navBack

    var newPassword = NewPassword()

    var isMyAccount: Boolean? = null

    var user: User? = null
        set(value) {
            field = value
            newPassword.version = value!!.version
            newPassword.username = value.username
        }

    fun navBackDone() {
        _navBack.value = false
    }

    fun getLoggedUser(): User {
        return sessionRepository.user!!
    }

    fun saveNewPassword() {
        viewModelScope.launch {
            try {
                val updatePasswordDto = newPassword.asUpdatePasswordDto()
                val response = if (isMyAccount!!) {
                    userRepository.updatePassword(sessionRepository.token!!, updatePasswordDto)
                } else {
                    userRepository.updatePasswordByAdminNetwork(
                        sessionRepository.token!!,
                        updatePasswordDto
                    )
                }
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
}