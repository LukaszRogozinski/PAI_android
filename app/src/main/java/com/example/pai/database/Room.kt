package com.example.pai.database

import android.content.Context
import androidx.room.*

@Dao
interface ProductDao {
    @Query("SELECT * FROM DatabaseProduct")
    suspend fun getDatabaseProducts(): List<DatabaseProduct>

    @Query("SELECT * FROM DatabaseProduct WHERE id = :id")
    fun get(id: Int): DatabaseProduct

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(products: List<DatabaseProduct>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: DatabaseProduct)

    @Query("DELETE FROM DatabaseProduct WHERE id = :id")
    suspend fun delete(id: Int)
}

@Dao
interface ProductTypeDao {
    @Query("SELECT * FROM DatabaseProductType")
    fun getDatabaseProductTypes(): List<DatabaseProductType>

    @Query("SELECT * FROM DatabaseProductType WHERE id = :id")
    fun get(id: Int) : DatabaseProductType

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(productType: DatabaseProductType)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(productTypes: List<DatabaseProductType>)
}

@Dao
interface WarehouseDao {
    @Query("SELECT * FROM DatabaseWarehouse")
    suspend fun getDatabaseWarehouses(): List<DatabaseWarehouse>

    @Query("SELECT * FROM DatabaseWarehouse WHERE id = :id")
    fun get(id: Int) : DatabaseWarehouse

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(warehouse: DatabaseWarehouse)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(warehouses: List<DatabaseWarehouse>)
}

@Database(entities = [DatabaseProduct::class, DatabaseProductType::class, DatabaseWarehouse::class], version = 1)
abstract class PaiDatabase : RoomDatabase() {
    abstract val productDao: ProductDao
    abstract val productTypeDao: ProductTypeDao
    abstract val warehouseDao: WarehouseDao
}

private lateinit var INSTANCE: PaiDatabase

fun getDatabase(context: Context): PaiDatabase {
    synchronized(PaiDatabase::class.java) {
        if(!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                PaiDatabase::class.java,
                "products").build()
        }
    }
    return INSTANCE
}