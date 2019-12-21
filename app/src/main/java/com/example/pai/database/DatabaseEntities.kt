package com.example.pai.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class DatabaseProduct constructor(
    @PrimaryKey
    val id: Int,
    val version: Int,
    val serialNumber: String,
    val status: String,
    @ForeignKey(
        entity = DatabaseProductType::class,
        parentColumns = ["id"],
        childColumns = ["databaseProductTypeId"]
    )
    val databaseProductTypeId: Int,
    val lastUpdate: String,
    val createDate: String,
    val deleted: Boolean,
    @ForeignKey(
        entity = DatabaseWarehouse::class,
        parentColumns = ["id"],
        childColumns = ["databaseWarehouseId"]
    )
    val databaseWarehouseId: Int
)

@Entity
data class DatabaseProductType constructor(
    @PrimaryKey
    val id: Int,
    val version: Int,
    val name: String,
    val manufacture: String,
    val cost: Int,
    val deleted: Boolean
)

@Entity
data class DatabaseWarehouse constructor(
    @PrimaryKey
    val id: Int,
    val version: Int,
    val name: String,
    val deleted: Boolean
)

//fun List<DatabaseProduct>.asDomainModel(database: PaiDatabase): List<Product> {
//    return map {
//        Product(
//            id = it.id,
//            version = it.version,
//            serialNumber = it.serialNumber,
//            status = it.status,
//            productType = database.productTypeDao.get(it.databaseProductTypeId).asDomainModel(),
//            lastUpdate = it.lastUpdate,
//            createDate = it.createDate,
//            deleted = it.deleted,
//            department = database.warehouseDao.get(it.databaseWarehouseId).asDomainModel()
//        )
//    }
//}
//
//fun List<DatabaseWarehouse>.asWarehouseDomainModel(): List<Department> {
//    return map {
//        Department(
//            id = it.id,
//            version = it.version,
//            name = it.name,
//            deleted = it.deleted
//        )
//    }
//}
//
//fun DatabaseProduct.asDomainModel(database: PaiDatabase): Product {
//    return Product(
//        id = this.id,
//        version = this.version,
//        serialNumber = this.serialNumber,
//        status = this.status,
//        productType = database.productTypeDao.get(this.databaseProductTypeId).asDomainModel(),
//        lastUpdate = this.lastUpdate,
//        createDate = this.createDate,
//        deleted = this.deleted,
//        department = database.warehouseDao.get(this.databaseWarehouseId).asDomainModel()
//    )
//}
//
//fun DatabaseProductType.asDomainModel(): ProductType {
//    return ProductType(
//        id = this.id,
//        version = this.version,
//        name = this.name,
//        manufacture = this.manufacture,
//        cost = this.cost,
//        deleted = this.deleted
//    )
//}
//
//fun DatabaseWarehouse.asDomainModel(): Department {
//    return Department(
//        id = this.id,
//        version = this.version,
//        name = this.name,
//        deleted = this.deleted
//    )
//}