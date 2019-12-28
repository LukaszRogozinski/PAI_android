package com.example.pai.features.users.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pai.domain.LoggedUser
import com.example.pai.domain.User
import com.example.pai.network.asNewUserDto
import com.example.pai.network.asUpdateUserDto
import com.example.pai.repository.NetworkRepository
import com.example.pai.repository.SessionRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import timber.log.Timber
import java.lang.Exception

class UserEditViewModel(private val networkRepository: NetworkRepository, val sessionRepository: SessionRepository) : ViewModel() {

    var rolesArray = mutableListOf<String>()

    private val _selectRolesClicked = MutableLiveData<Boolean>()
    val selectRolesClicked: LiveData<Boolean>
        get() = _selectRolesClicked

    fun selectRolesClickedDone() {
        _selectRolesClicked.value = false
    }

    fun selectRolesClickedBegin() {
        _selectRolesClicked.value = true
    }

    fun getLoggedUser() : LoggedUser {
        return sessionRepository.currentUser!!
    }

    private val _navBackToListOfUsers = MutableLiveData<Boolean>()
    val navBackToListOfUsers: LiveData<Boolean>
        get() = _navBackToListOfUsers

    private val _navBackToDetailUser = MutableLiveData<Boolean>()
    val navBackToDetailUser: LiveData<Boolean>
        get() = _navBackToDetailUser

    private val _isNewUser = MutableLiveData<Boolean>()
    val isNewUser: LiveData<Boolean>
    get() = _isNewUser

    var user: User? = User()
        set(value) =
            if (value != null) {
                _isNewUser.value = false
                value.authorities!!.forEach { rolesArray.add(it.authority) }
                field = value
            } else {
                _isNewUser.value = true
            }

    fun navBackToListOfUsersDone() {
        _navBackToListOfUsers.value = false
    }

    fun navBackToDetailUserDone() {
        _navBackToDetailUser.value = false
    }

    fun saveUser() {
        viewModelScope.launch {
            try {
                lateinit var response: Response<Unit>
                response = if(_isNewUser.value!!){
                    val newUser = user!!.asNewUserDto(rolesArray.toList())
                    networkRepository.createNewUserByAdminNetwork(newUser)
                } else{
                    val updateUser = user!!.asUpdateUserDto(rolesArray.toList())
                    networkRepository.updateUserByAdminNetwork(updateUser)
                }
                if (response.isSuccessful) {
                    if(_isNewUser.value!!){
                        _navBackToListOfUsers.postValue(true)
                    } else{
                        _navBackToDetailUser.postValue(true)
                    }
                } else {
                    println(response.errorBody())
                }
            } catch (e: Exception) {
                Timber.e(e)
            }
        }

    }

}