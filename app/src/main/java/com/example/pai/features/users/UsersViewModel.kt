package com.example.pai.features.users

import android.app.Application
import androidx.lifecycle.*
import androidx.recyclerview.widget.DiffUtil
import com.example.pai.BR
import com.example.pai.R
import com.example.pai.database.getDatabase
import com.example.pai.domain.User
import com.example.pai.network.asUserDomainModel
import com.example.pai.repository.NetworkRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import me.tatarka.bindingcollectionadapter2.OnItemBind
import timber.log.Timber
import java.lang.Exception

class UsersViewModel(private val networkRepository: NetworkRepository) : ViewModel() {

//    val usersRepository = NetworkRepository()

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>>
        get() = _users

    private val _navigateToSelectedUser = MutableLiveData<User>()
    val navigateToSelectedUser: LiveData<User>
        get() = _navigateToSelectedUser

    private val _navigateToNewUser = MutableLiveData<Boolean>()
    val navigateToNewUser: LiveData<Boolean>
    get() = _navigateToNewUser

    fun onSelectedUser(item: User) {
        _navigateToSelectedUser.value = item
    }

    fun navigateToSelectedUserDone() {
        _navigateToSelectedUser.value = null
    }

    private val _eventNetworkError = MutableLiveData(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private val _isNetworkErrorShown = MutableLiveData(false)
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    val itemBinding: OnItemBind<User> = OnItemBind { itemBinding, _, item ->
        itemBinding.set(BR.item, R.layout.user_item)
        itemBinding.bindExtra(BR.vm, this)
    }

    val diff = object : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }

    init {
        loadUsersFromNetwork()
    }

    private fun loadUsersFromNetwork() {
        viewModelScope.launch {
            try {
                val response = networkRepository.loadUsers()
                if (response.isSuccessful) {
                    _users.value = response.body()!!.asUserDomainModel()
                    _eventNetworkError.value = false
                    _isNetworkErrorShown.value = false
                    Timber.i("success")
                } else {
                    Timber.e(response.errorBody().toString())
                    _eventNetworkError.value = true
                }
            } catch (e: Exception) {
                Timber.e(e)
                _eventNetworkError.value = true
            }
        }
    }

    fun newUserButtonClicked() {
        _navigateToNewUser.value = true
    }

    fun navigateToNewUserDone() {
        _navigateToNewUser.value = false
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

//    class Factory() : ViewModelProvider.Factory {
//        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//            if (modelClass.isAssignableFrom(UsersViewModel::class.java)) {
//                return UsersViewModel() as T
//            }
//            throw IllegalArgumentException("Unable to construct viewmodel")
//        }
//    }
}