//package com.example.pai.database
//
//import android.content.Context
//import androidx.lifecycle.LiveData
//import androidx.room.*
//
//@Dao
//interface ProductDao {
//    @Query("SELECT * FROM databaseproduct")
//    fun getDatabaseProducts(): LiveData<List<DatabaseProduct>>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertAll(products: List<DatabaseProduct>)
//}
//
//@Database(entities = [DatabaseProduct::class, DatabaseProductType::class, DatabaseWarehouse::class], version = 1)
//abstract class ProductDatabase : RoomDatabase() {
//    abstract val productDao: ProductDao
//}
//
//private lateinit var INSTANCE: ProductDatabase
//
//fun getDatabase(context: Context): ProductDatabase {
//    synchronized(ProductDatabase::class.java) {
//        if(!::INSTANCE.isInitialized) {
//            INSTANCE = Room.databaseBuilder(context.applicationContext,
//                ProductDatabase::class.java,
//                "products").build()
//        }
//    }
//    return INSTANCE
//}