package com.example.pai.repository

import com.example.pai.domain.User
import com.example.pai.network.*
import kotlinx.coroutines.runBlocking
import retrofit2.Response

class SessionRepository {

//    private var _user: User? = null
//    val user: User?
//    get() = _user

    private var _username: String? = null
    val username: String?
    get() = _username

    private var _isAdmin: Boolean = false
    val isAdmin: Boolean
    get() = _isAdmin

    private var _token: String? = null
    val token: String?
        get() = _token

    fun saveUsername(username: String) {
        _username = username
    }

    fun saveToken(token: String) {
        _token = token
    }

//    fun saveUser(user: User) {
//        _user = user
//    }

    fun checkIfAdmin(user: User) {
        _isAdmin = user.userRoles!!.any{ it.name == "ADMIN" }
    }

//    fun isAdmin(): Boolean = _user!!.userRoles!!.any { it.name == "ADMIN" }

    suspend fun getLoggedUserNetwork(token: String, username: String) : Response<UserDto> {
        return PaiApi.retrofitService.getUserByUsername(createAuthorizationHeader(token),username)
    }

    private suspend fun logoutUser(token: String) : Response<String> {
        return PaiApi.retrofitService.revokeToken(createAuthorizationHeader(token))
    }

    suspend fun logIn(username: String, password: String): Response<TokenDto> {
        return PaiApi.retrofitService.getToken(LOGIN_TOKEN, username = username, password = password)
    }

    fun logout() {
        runBlocking {
            val response = logoutUser(_token!!)
            if(response.isSuccessful) {
                _isAdmin = false
//                _user = null
                _token = null
                _username = null
            } else{
                print("eee")
            }
        }
    }
}