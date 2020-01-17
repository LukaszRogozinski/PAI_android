package com.example.pai.features.users.detail

import androidx.lifecycle.*
import com.example.pai.domain.User
import com.example.pai.network.UserDto
import com.example.pai.network.asDomainModel
import com.example.pai.repository.SessionRepository
import com.example.pai.repository.UserRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Response
import timber.log.Timber

class UserDetailViewModel(
    private val userRepository: UserRepository,
    val sessionRepository: SessionRepository
) : ViewModel() {

    private val _mutableUser = MutableLiveData<User>()
    val mutableUser: LiveData<User>
        get() = _mutableUser

    fun setUser(user: User?) {
        runBlocking {
            try {
                val response = if (!isMyAccount!!) {
                    sessionRepository.getLoggedUserNetwork(
                        sessionRepository.token!!,
                        user!!.username!!
                    )
                } else {
                    sessionRepository.getLoggedUserNetwork(
                        sessionRepository.token!!,
                        sessionRepository.username!!
                    )
                }
                if (response.isSuccessful) {
                    _mutableUser.value = response.body()!!.asDomainModel()
                } else {
                    println("unable to load user from network")
                }

            } catch (e: java.lang.Exception) {
                println("exception")
            }
        }
    }

    fun getUser(): User {
        return mutableUser.value!!
    }

    private var isMyAccount: Boolean? = null

    fun setIsMyAccount(value: Boolean) {
        isMyAccount = value
    }

    fun getIsMyAccount(): Boolean {
        return isMyAccount!!
    }

    private val _deleteResponse = MutableLiveData<Boolean>()
    val deleteResponse: LiveData<Boolean>
        get() = _deleteResponse

    private val _showDeleteDialog = MutableLiveData(false)
    val showDeleteDialog: LiveData<Boolean>
        get() = _showDeleteDialog

    private val _navigateToEditUser = MutableLiveData<Boolean>()
    val navigateToEditUser: LiveData<Boolean>
        get() = _navigateToEditUser

    private val _navigateToChangePasswordUser = MutableLiveData<Boolean>()
    val navigateToChangePasswordUser: LiveData<Boolean>
        get() = _navigateToChangePasswordUser

    fun onDeleteResponseFinish() {
        _deleteResponse.value = false
        Timber.i("deleteResponse value= ${_deleteResponse.value}")
    }

    fun deleteButtonClicked() {
        _showDeleteDialog.value = true
    }

    fun showDeleteButtonClickedCanceled() {
        _showDeleteDialog.value = false
    }

    fun editButtonClicked() {
        _navigateToEditUser.value = true
    }

    fun changePasswordButtonClicked() {
        _navigateToChangePasswordUser.value = true
    }

    fun navigateToChangePasswordUserDone() {
        _navigateToChangePasswordUser.value = false
    }

    fun navigateToEditUserDone() {
        _navigateToEditUser.value = false
    }

    fun deleteUser() {
        viewModelScope.launch {
            try {
                val response = userRepository.deleteUser(
                    sessionRepository.token!!,
                    _mutableUser.value!!.username!!
                )
                if (response.isSuccessful) {
                    _deleteResponse.postValue(true)
                    _showDeleteDialog.value = false
                    Timber.i("deleteResponse value= ${_deleteResponse.value}")
                }
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}