package com.example.pai.features.users.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pai.domain.User
import com.example.pai.network.asDomainModel
import com.example.pai.network.asNewUserDto
import com.example.pai.network.asUpdateUserDto
import com.example.pai.repository.SessionRepository
import com.example.pai.repository.UserRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Response
import timber.log.Timber
import java.lang.Exception

class UserEditViewModel(
    private val userRepository: UserRepository,
    val sessionRepository: SessionRepository
) : ViewModel() {

    private var actualUsername: String? = null

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

    fun getLoggedUser(): User {
        var loggedUser: User? = null
        runBlocking {
            try {
                val response = sessionRepository.getLoggedUserNetwork(sessionRepository.token!!, sessionRepository.username!!)
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
        set(value) {
            if (value != null) {
                _isNewUser.value = false
                value.userRoles!!.forEach { rolesArray.add(it.name) }
                field = value
            } else {
                _isNewUser.value = true
            }
        }

    var isMyAccount: Boolean? = null

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
                response = if (_isNewUser.value!!) {
                    val newUser = user!!.asNewUserDto(rolesArray.toList())
                    userRepository.createNewUserByAdminNetwork(
                        sessionRepository.token!!,
                        newUser
                    )
                } else {
                    val updateUser = user!!.asUpdateUserDto(rolesArray.toList())
                    if (isMyAccount!!) {
                        actualUsername = updateUser.username
                        userRepository.updateUser(
                            sessionRepository.token!!,
                            updateUser
                        )
                    } else {
                        userRepository.updateUserByAdminNetwork(
                            sessionRepository.token!!,
                            updateUser
                        )
                    }
                }
                if (response.isSuccessful) {
                    if (_isNewUser.value!!) {
                        _navBackToListOfUsers.postValue(true)
                    } else {
                        if(actualUsername != null) {
                            sessionRepository.saveUsername(actualUsername!!)
                        }
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