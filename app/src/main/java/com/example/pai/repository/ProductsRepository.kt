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

//    private val _products = MutableLiveData<List<Product>>()
//    val products: LiveData<List<Product>>
//        get() {
//           // GlobalScope.launch {
//          //      _products.postValue(database.productDao.getDatabaseProducts().asDomainModel(database))
//          //  }
//            return _products
//        }

    private val _warehouses = MutableLiveData<List<Warehouse>>()
    val warehouses: LiveData<List<Warehouse>>
        get() {
            GlobalScope.launch {
                _warehouses.postValue(database.warehouseDao.getDatabaseWarehouses().asWarehouseDomainModel())
            }
            return _warehouses
        }

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

    suspend fun getProductType() {
        withContext(Dispatchers.IO) {
            val b = database.productTypeDao.get(
                1
            )
            val c = database.productTypeDao.getDatabaseProductTypes()
            val eee = database.productDao.getDatabaseProducts().asDomainModel(database)
            // var ggg = MutableLiveData<List<Warehouse>>()
            //       _products.postValue(database.productDao.getDatabaseProducts().asDomainModel(database))
            Log.i("", "")
        }

    }

}