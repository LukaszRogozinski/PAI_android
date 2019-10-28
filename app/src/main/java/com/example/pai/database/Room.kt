package com.example.pai.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductDao {
    @Query("SELECT * FROM databaseproduct")
    fun getDatabaseProducts(): LiveData<List<DatabaseProduct>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(products: List<DatabaseProduct>)
}

@Dao
interface ProductTypeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(productType: DatabaseProductType)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(productTypes: List<DatabaseProductType>)
}

@Dao
interface WarehouseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(warehouse: DatabaseWarehouse)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(warehouses: List<DatabaseWarehouse>)
}

@Database(entities = [DatabaseProduct::class, DatabaseProductType::class, DatabaseWarehouse::class], version = 1)
abstract class ProductDatabase : RoomDatabase() {
    abstract val productDao: ProductDao
    abstract val productTypeDao: ProductTypeDao
    abstract val warehouseDao: WarehouseDao
}

private lateinit var INSTANCE: ProductDatabase

fun getDatabase(context: Context): ProductDatabase {
    synchronized(ProductDatabase::class.java) {
        if(!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                ProductDatabase::class.java,
                "products").build()
        }
    }
    return INSTANCE
}