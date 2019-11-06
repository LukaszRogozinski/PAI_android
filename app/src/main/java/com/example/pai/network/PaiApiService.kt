package com.example.pai.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL = "http://10.9.59.41:8081"

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()

interface PaiApiService {
    @GET("api/products")
   suspend fun getProducts() : List<ProductDto>

    @POST("api/products")
    suspend fun addProduct(@Body productDomainToDto: ProductDomainToDto) : Response<Void>

    @PUT("api/products")
    suspend fun updateProduct(@Body productDomainToDto: ProductDomainToDto) : Response<Void>

    @DELETE("/api/products/{id}")
    suspend fun deleteProduct(@Path("id") id: Int) : Response<Void>
}

object PaiApi {
    val retrofitService : PaiApiService by lazy {
        retrofit.create(PaiApiService::class.java)
    }
}