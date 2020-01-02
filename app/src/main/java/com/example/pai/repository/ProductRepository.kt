package com.example.pai.repository

import com.example.pai.network.PaiApi
import com.example.pai.network.ProductDto
import com.example.pai.network.ProductTypeDto
import com.example.pai.network.createAuthorizationHeader
import retrofit2.Response
import java.util.*

class ProductRepository {

    suspend fun loadProducts(token: String) : Response<List<ProductDto>> {
        return PaiApi.retrofitService.getProducts(createAuthorizationHeader(token))
    }

    suspend fun deleteProduct(token: String, id: UUID) : Response<Unit> {
        return PaiApi.retrofitService.deleteProductAsync(createAuthorizationHeader(token), id)
    }

    suspend fun getProductTypesFromNetwork(token: String) : Response<List<ProductTypeDto>> {
        return PaiApi.retrofitService.getProductTypes(createAuthorizationHeader(token))
    }
}