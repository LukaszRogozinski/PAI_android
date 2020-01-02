package com.example.pai.features.users.detail

import androidx.lifecycle.*
import com.example.pai.domain.User
import com.example.pai.repository.SessionRepository
import com.example.pai.repository.UserRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import timber.log.Timber

class UserDetailViewModel(private  val userRepository: UserRepository, val sessionRepository: SessionRepository) : ViewModel() {

    private lateinit var user: User

    fun setUser(user: User) {
        this.user = user
    }

    fun getUser(): User {
        return this.user
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
                val response = userRepository.deleteUser(sessionRepository.token!!, user.username!!)
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

//    fun getLoggedUser() : LoggedUser {
//        return sessionRepository.currentUser!!
//    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

//    class Factory(val user: User) : ViewModelProvider.Factory {
//        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//            if (modelClass.isAssignableFrom(UserDetailViewModel::class.java)) {
//                return UserDetailViewModel(user) as T
//            }
//            throw IllegalArgumentException("Unable to construct viewmodel")
//        }
//    }
}