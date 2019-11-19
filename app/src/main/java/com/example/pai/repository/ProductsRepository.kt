package com.example.pai.repository

import android.util.Log
import com.example.pai.database.*
import com.example.pai.domain.Product
import com.example.pai.domain.Warehouse
import com.example.pai.network.*
import kotlinx.coroutines.*
import retrofit2.Response

class ProductsRepository(private val database: PaiDatabase) {

    suspend fun getProductsFromDatabase(): List<Product> {
        return database.productDao.getDatabaseProducts().asDomainModel(database)
    }

    suspend fun getWarehousesFromDatabase(): List<Warehouse> {
        return database.warehouseDao.getDatabaseWarehouses().asWarehouseDomainModel()
    }

    suspend fun loadProducts() : Response<List<ProductDto>> {
        return PaiApi.retrofitService.getProducts()
    }

    suspend fun getWarehousesFromNetwork() : Response<List<WarehouseDto>> {
        return PaiApi.retrofitService.getWarehouses()
    }

    suspend fun getProductTypesFromNetwork() : Response<List<ProductTypeDto>> {
        return PaiApi.retrofitService.getProductTypes()
    }

//    suspend fun refreshProductDatabase() {
//        withContext(Dispatchers.IO) {
//            val products = PaiApi.retrofitService.getProducts()
//            val productsType: MutableList<ProductTypeDto> = mutableListOf()
//            val warehouses: MutableList<WarehouseDto> = mutableListOf()
//            products.forEach {
//                productsType.add(it.productType)
//                warehouses.add(it.warehouse)
//            }
//            database.warehouseDao.insertAll(warehouses.asWarehouseDatabaseModel())
//            database.productTypeDao.insertAll(productsType.asProductTypeDatabaseModel())
//            database.productDao.insertAll(products.asProductDatabaseModel())
//        }
//    }

    suspend fun addProduct(productDomainToDto: ProductDomainToDto) : Response<Unit> {
       return PaiApi.retrofitService.addProduct(productDomainToDto)
    }

    suspend fun updateProduct(productDomainToDto: ProductDomainToDto) : Response<Unit> {
        return PaiApi.retrofitService.updateProduct(productDomainToDto)
    }

    suspend fun deleteProduct(id: Int) : Response<Unit> {
        return PaiApi.retrofitService.deleteProductAsync(id)
    }

    suspend fun deleteProductFromDatabase(id: Int) {
        database.productDao.delete(id)
    }
}