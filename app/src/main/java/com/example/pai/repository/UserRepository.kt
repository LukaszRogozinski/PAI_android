package com.example.pai.repository

import com.example.pai.network.*
import retrofit2.Response

class UserRepository {

    suspend fun loadUsers(token: String) : Response<List<UserDto>> {
        return PaiApi.retrofitService.getUsers(createAuthorizationHeader(token))
    }

    suspend fun deleteUser(token: String, username: String) : Response<Unit> {
        return PaiApi.retrofitService.deleteUserAsync(createAuthorizationHeader(token), username)
    }

    suspend fun createNewUserByAdminNetwork(token: String, newUserDto: NewUserDto) : Response<Unit> {
        return PaiApi.retrofitService.createNewUserByAdmin(createAuthorizationHeader(token), newUserDto)
    }

    suspend fun updateUserByAdminNetwork(token: String, updateUserDto: UpdateUserDto) : Response<Unit> {
        return PaiApi.retrofitService.updateUserByAdmin(createAuthorizationHeader(token), updateUserDto)
    }

    suspend fun updateUser(token: String, updateUserDto: UpdateUserDto) : Response<Unit> {
        return PaiApi.retrofitService.updateUser(createAuthorizationHeader(token), updateUserDto)
    }

    suspend fun updatePasswordByAdminNetwork(token: String, updatePasswordDto: UpdatePasswordDto) : Response<Unit> {
        return PaiApi.retrofitService.updatePasswordByAdmin(createAuthorizationHeader(token), updatePasswordDto)
    }

    suspend fun updatePassword(token: String, updatePasswordDto: UpdatePasswordDto) : Response<Unit> {
        return PaiApi.retrofitService.updateOwnPassword(createAuthorizationHeader(token), updatePasswordDto)
    }
}