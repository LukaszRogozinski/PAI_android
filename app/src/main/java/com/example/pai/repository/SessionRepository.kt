package com.example.pai.repository

import com.example.pai.domain.User
import com.example.pai.network.*
import kotlinx.coroutines.runBlocking
import retrofit2.Response

class SessionRepository {

    private var _user: User? = null
    val user: User?
    get() = _user

    private var _token: String? = null
    val token: String?
    get() = _token

    fun saveToken(token: String) {
        _token = token
    }

    fun saveUser(user: User) {
        _user = user
    }

    fun isAdmin(): Boolean = _user!!.userRoles!!.any { it.name == "ADMIN" }

    suspend fun getLoggedUserNetwork(token: String, username: String) : Response<UserDto> {
        return PaiApi.retrofitService.getLoggedUser(createAuthorizationHeader(token),username)
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
                _user = null
                _token = null
            } else{
                print("eee")
            }
        }
    }

}