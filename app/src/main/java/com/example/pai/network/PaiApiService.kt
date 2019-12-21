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

private const val BASE_URL = "http://10.9.59.41:8080"

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
    @GET("api/products")
    suspend fun getProducts(): Response<List<ProductDto>>

//    @POST("api/products")
//    suspend fun addProduct(@Body productDomainToDto: ProductDomainToDto): Response<Unit>
//
//    @PUT("api/products")
//    suspend fun updateProduct(@Body productDomainToDto: ProductDomainToDto): Response<Unit>

    @DELETE("/api/products/{id}")
    suspend fun deleteProductAsync(@Path("id") id: UUID): Response<Unit>

    @GET("api/warehouses")
    suspend fun getDepartments(): Response<List<DepartmentDto>>

    @GET("api/products/type")
    suspend fun getProductTypes(): Response<List<ProductTypeDto>>

    @GET("api/users")
    suspend fun getUsers(): Response<List<UserDto>>
}

interface UserService {
    @GET("api/users")
    suspend fun getUsers(): Response<List<UserDto>>
}

object PaiApi {
    val retrofitService: PaiApiService by lazy {
        retrofit.create(PaiApiService::class.java)
    }
}

object UserApi {
    val userService: UserService by lazy {
        retrofit.create(UserService::class.java)
    }
}