package com.example.pai.features.users.detail

import androidx.lifecycle.*
import com.example.pai.domain.User
import com.example.pai.repository.NetworkRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class UserDetailViewModel(val user: User) : ViewModel() {

    private val usersRepository = NetworkRepository()

    private val _deleteResponse = MutableLiveData<Boolean>()
    val deleteResponse: LiveData<Boolean>
        get() = _deleteResponse

    private val _showDeleteDialog = MutableLiveData(false)
    val showDeleteDialog: LiveData<Boolean>
        get() = _showDeleteDialog

    fun onDeleteResponseFinish() {
        _deleteResponse.value = false
        Timber.i("deleteResponse value= ${_deleteResponse.value}")
    }

    fun deleteButtonClicked() {
        _showDeleteDialog.value = true
    }

    fun deleteUser() {
        viewModelScope.launch {
            try {
                val response = usersRepository.deleteUser(user.username)
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

    class Factory(val user: User) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserDetailViewModel::class.java)) {
                return UserDetailViewModel(user) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}