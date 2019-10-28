package com.example.pai.repository

import com.example.pai.database.ProductDatabase
import com.example.pai.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductsRepository(private val database: ProductDatabase) {

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
        }
    }

}