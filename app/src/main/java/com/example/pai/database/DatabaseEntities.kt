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

//fun List<DatabaseProduct>.asDomainModel(): List<ProductDto> {
//    return map {
//        ProductDto(
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
//fun DatabaseProductType.asDomainModel(): ProductTypeDto {
//    return ProductTypeDto(
//        id = this.id,
//        version = this.version,
//        name = this.name,
//        manufacture = this.manufacture,
//        cost = this.cost,
//        deleted = this.deleted
//    )
//}
//
//fun DatabaseWarehouse.asDomainModel(): WarehouseDto {
//    return WarehouseDto(
//        id = this.id,
//        version = this.version,
//        name = this.name,
//        deleted = this.deleted
//    )
//}