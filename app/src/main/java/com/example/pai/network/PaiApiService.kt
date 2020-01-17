package com.example.pai.network

import com.example.pai.utils.UuidAdapter
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import java.util.*

private const val BASE_URL = "http://10.9.59.41:8081"
private const val CONTENT_TYPE = "application/json"
private const val BEARER = "Bearer"
const val LOGIN_TOKEN = "Basic c3ByaW5nLXNlY3VyaXR5LW9hdXRoMi1yZWFkLXdyaXRlLWNsaWVudDpzcHJpbmctc2VjdXJpdHktb2F1dGgyLXJlYWQtd3JpdGUtY2xpZW50LXBhc3N3b3JkMTIzNA=="
private const val LOGIN_CONTENT_TYPE = "application/x-www-form-urlencoded; charset=utf-8"
private const val LOGIN_GRANT_TYPE = "password"
private const val LOGIN_CLIENT_ID = "spring-security-oauth2-read-write-client"

fun createAuthorizationHeader(token: String): String = "$BEARER $token"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .add(UuidAdapter())
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()

interface PaiApiService {

    //Products------------------------
    @GET("secured/products")
    @Headers("Content-type: $CONTENT_TYPE")
    suspend fun getProducts(@Header("authorization") authorization: String): Response<List<ProductDto>>

    @DELETE("secured/products/{id}")
    @Headers("Content-type: $CONTENT_TYPE")
    suspend fun deleteProductAsync(@Header("authorization") authorization: String, @Path("id") id: UUID): Response<Unit>

    @GET("secured/products/type")
    @Headers("Content-type: $CONTENT_TYPE")
    suspend fun getProductTypes(@Header("authorization") authorization: String): Response<List<ProductTypeDto>>

    //Users--------------------------
    @GET("secured/users")
    @Headers("Content-type: $CONTENT_TYPE")
    suspend fun getUsers(@Header("authorization") authorization: String): Response<List<UserDto>>

    @GET("/secured/users/{username}")
    @Headers("Content-type: $CONTENT_TYPE")
    suspend fun getUserByUsername(@Header("authorization") authorization: String, @Path("username") username: String) : Response<UserDto>

    @DELETE("secured/users/{username}")
    @Headers("Content-type: $CONTENT_TYPE")
    suspend fun deleteUserAsync(@Header("authorization") authorization: String, @Path("username") username: String) : Response<Unit>

    @POST("secured/account/admin/create")
    @Headers("Content-type: $CONTENT_TYPE")
    suspend fun createNewUserByAdmin(@Header("authorization") authorization: String, @Body newUserDto: NewUserDto) : Response<Unit>

    @PUT("secured/account/admin/edit")
    @Headers("Content-type: $CONTENT_TYPE")
    suspend fun updateUserByAdmin(@Header("authorization") authorization: String, @Body updateUserDto: UpdateUserDto) : Response<Unit>

    @PUT("secured/account/self/edit")
    @Headers("Content-type: $CONTENT_TYPE")
    suspend fun updateUser(@Header("authorization") authorization: String, @Body updateUserDto: UpdateUserDto) : Response<Unit>

    @PUT("secured/users/password/admin")
    @Headers("Content-type: $CONTENT_TYPE")
    suspend fun updatePasswordByAdmin(@Header("authorization") authorization: String, @Body updatePasswordDto: UpdatePasswordDto) : Response<Unit>

    @PUT("secured/users/password")
    @Headers("Content-type: $CONTENT_TYPE")
    suspend fun updateOwnPassword(@Header("authorization") authorization: String, @Body updatePasswordDto: UpdatePasswordDto) : Response<Unit>

    @FormUrlEncoded
    @POST("/oauth/token")
    @Headers("Content-type: $LOGIN_CONTENT_TYPE")
    suspend fun getToken(@Header("authorization") authorizationToken: String,
                         @Field(value = "username", encoded = true) username: String,
                         @Field(value = "password", encoded = true) password: String,
                         @Field(value = "grant_type", encoded = true) grant_type: String = LOGIN_GRANT_TYPE,
                         @Field(value = "client_id", encoded = true) client_id: String = LOGIN_CLIENT_ID) : Response<TokenDto>

    @GET("/secured/revoke/token")
    @Headers("Content-type: $CONTENT_TYPE")
    suspend fun revokeToken(@Header("authorization") authorizationToken: String) : Response<String>
}

object PaiApi {
    val retrofitService: PaiApiService by lazy {
        retrofit.create(PaiApiService::class.java)
    }
}