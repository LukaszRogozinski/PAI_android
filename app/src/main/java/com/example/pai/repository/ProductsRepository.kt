package com.example.pai.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.pai.database.*
import com.example.pai.domain.Product
import com.example.pai.domain.Warehouse
import com.example.pai.network.*
import kotlinx.coroutines.*

class ProductsRepository(private val database: PaiDatabase) {

    suspend fun getProductsFromDatabase(): List<Product> {
        return database.productDao.getDatabaseProducts().asDomainModel(database)
    }

    suspend fun getWarehousesFromDatabase(): List<Warehouse> {
        return database.warehouseDao.getDatabaseWarehouses().asWarehouseDomainModel()
    }

    suspend fun refreshProductDatabase() {
        withContext(Dispatchers.IO) {
            val products = PaiApi.retrofitService.getProducts()
            val productsType: MutableList<ProductTypeDto> = mutableListOf()
            val warehouses: MutableList<WarehouseDto> = mutableListOf()
            products.forEach {
                productsType.add(it.productType)
                warehouses.add(it.warehouse)
            }
            database.warehouseDao.insertAll(warehouses.asWarehouseDatabaseModel())
            database.productTypeDao.insertAll(productsType.asProductTypeDatabaseModel())
            database.productDao.insertAll(products.asProductDatabaseModel())
            Log.i("", "")
        }
    }

    suspend fun addProduct(productDomainToDto: ProductDomainToDto) {
        PaiApi.retrofitService.addProduct(productDomainToDto)
    }

    suspend fun updateProduct(productDomainToDto: ProductDomainToDto) {
        PaiApi.retrofitService.updateProduct(productDomainToDto)
    }

    suspend fun deleteProduct(id: Int) {
        PaiApi.retrofitService.deleteProduct(id)
    }
}