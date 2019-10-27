//package com.example.pai.database
//
//import androidx.room.Embedded
//import androidx.room.Entity
//import androidx.room.PrimaryKey
//import com.example.pai.network.Product
//import com.example.pai.network.ProductType
//import com.example.pai.network.Warehouse
//
//@Entity
//data class DatabaseProduct constructor(
//    @PrimaryKey
//    val id: Int,
//    val version: Int,
//    val serialNumber: String,
//    val status: String,
//    @Embedded
//    val productType: DatabaseProductType,
//    val lastUpdate: String,
//    val createDate: String,
//    val deleted: Boolean,
//    @Embedded
//    val warehouse: DatabaseWarehouse
//)
//
//@Entity
//data class DatabaseProductType constructor(
//    @PrimaryKey
//    val id: Int,
//    val version: Int,
//    val name: String,
//    val manufacture: String,
//    val cost: Int,
//    val deleted: Boolean
//)
//
//@Entity
//data class DatabaseWarehouse constructor(
//    @PrimaryKey
//    val id: Int,
//    val version: Int,
//    val name: String,
//    val deleted: Boolean
//)
//
//fun List<DatabaseProduct>.asDomainModel(): List<Product> {
//    return map {
//        Product(
//            id = it.id,
//            version = it.version,
//            serialNumber = it.serialNumber,
//            status = it.status,
//            productType = it.productType.asDomainModel(),
//            lastUpdate = it.lastUpdate,
//            createDate = it.createDate,
//            deleted = it.deleted,
//            warehouse = it.warehouse.asDomainModel()
//        )
//    }
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
//fun DatabaseWarehouse.asDomainModel(): Warehouse {
//    return Warehouse(
//        id = this.id,
//        version = this.version,
//        name = this.name,
//        deleted = this.deleted
//    )
//}