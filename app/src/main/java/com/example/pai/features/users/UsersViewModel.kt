package com.example.pai.features.users

import androidx.lifecycle.*
import androidx.recyclerview.widget.DiffUtil
import com.example.pai.BR
import com.example.pai.R
import com.example.pai.domain.User
import com.example.pai.network.asDomainModel
import com.example.pai.network.asUserDomainModel
import com.example.pai.repository.SessionRepository
import com.example.pai.repository.UserRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import me.tatarka.bindingcollectionadapter2.OnItemBind
import timber.log.Timber
import java.lang.Exception

class UsersViewModel(
    private val userRepository: UserRepository,
    val sessionRepository: SessionRepository
) : ViewModel() {

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
//        loadUsersFromNetwork()
    }

    fun getLoggedUser(): User {
        var loggedUser: User? = null
        runBlocking {
            try {
                val response = sessionRepository.getLoggedUserNetwork(sessionRepository.token!!, sessionRepository.user!!.username!!)
                if(response.isSuccessful) {
                    loggedUser =  response.body()!!.asDomainModel()
                } else{
                    println("nie moge pobrac zalogowanego uzytkownika")
                }
            } catch (e: Exception) {
                println("getLoggedUser nie pyklo")
            }
        }
        return loggedUser!!
    }



    fun loadUsersFromNetwork() {
        viewModelScope.launch {
            try {
                val response = userRepository.loadUsers(sessionRepository.token!!)
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
}