package com.example.pai.network

//import com.example.pai.database.DatabaseProduct
//import com.example.pai.database.DatabaseProductType
//import com.example.pai.database.DatabaseWarehouse

data class Product(
    val id: Int,
    val version: Int,
    val serialNumber: String,
    val status: String,
    val productType: ProductType,
    val lastUpdate: String,
    val createDate: String,
    val deleted: Boolean,
    val warehouse: Warehouse
)

data class ProductType(
    val id: Int,
    val version: Int,
    val name: String,
    val manufacture: String,
    val cost: Int,
    val deleted: Boolean
)

data class Warehouse(
    val id: Int,
    val version: Int,
    val name: String,
    val deleted: Boolean
)
//
//fun List<Product>.asDatabaseModel(): List<DatabaseProduct> {
//    return map {
//        DatabaseProduct(
//            id = it.id,
//            version = it.version,
//            serialNumber = it.serialNumber,
//            status = it.status,
//            productType = it.productType.asDatabaseModel(),
//            lastUpdate = it.lastUpdate,
//            createDate = it.createDate,
//            deleted = it.deleted,
//            warehouse = it.warehouse.asDatabaseModel()
//        )
//    }
//}
//
//fun ProductType.asDatabaseModel(): DatabaseProductType {
//    return DatabaseProductType(
//        id = this.id,
//        version = this.version,
//        name = this.name,
//        manufacture = this.manufacture,
//        cost = this.cost,
//        deleted = this.deleted
//    )
//}
//
//fun Warehouse.asDatabaseModel(): DatabaseWarehouse {
//    return DatabaseWarehouse(
//        id = this.id,
//        version = this.version,
//        name = this.name,
//        deleted = this.deleted
//    )
//}