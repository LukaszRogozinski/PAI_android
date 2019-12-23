package com.example.pai.features.users.edit

import androidx.lifecycle.ViewModel
import com.example.pai.domain.User
import com.example.pai.repository.NetworkRepository

class UserEditViewModel(private val networkRepository: NetworkRepository) : ViewModel() {
    var user: User? = null
    set(value) {
        if(user != null) {
            isNewUser = false
            this.user = user
        } else{
            isNewUser = true
        }
        field = value
    }
        private var isNewUser: Boolean = false

        private val listOfAuthorities = listOf<String>("PASSWORD_READ", "USER_READ", "USER_ROLES_READ", "PASSWORD_UPDATE")
//
//        fun setUser(user: User?){
//            if(user != null) {
//                isNewUser = false
//                this.user = user
//            } else{
//                isNewUser = true
//            }
//        }

//        fun getUser(): User?{
//            return user
//        }

        fun saveUser(){

        }

}