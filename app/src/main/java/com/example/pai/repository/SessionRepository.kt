package com.example.pai.repository

import android.content.SharedPreferences
import com.example.pai.domain.LoggedUser

class SessionRepository(
    private val sharedPreferences: SharedPreferences,
    private val networkRepository: NetworkRepository
) {
    companion object {
        const val USER_TOKEN_KEY = "user_token"
    }

    private var loggedUser: LoggedUser? = null

    val currentUser: LoggedUser?
    get() = loggedUser

    fun logout() {
        loggedUser = null
        sharedPreferences.edit().remove(USER_TOKEN_KEY).apply()
    }

    fun saveCurrentUserToken(loggedUser: LoggedUser) {
        this.loggedUser = loggedUser
        sharedPreferences.edit().putString(USER_TOKEN_KEY, loggedUser.token).apply()
    }

}