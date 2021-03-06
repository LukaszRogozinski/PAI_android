package com.example.pai.network

import com.example.pai.database.DatabaseProduct
import com.example.pai.database.DatabaseProductType
import com.example.pai.database.DatabaseWarehouse
import com.example.pai.domain.Product
import com.example.pai.domain.ProductType
import com.example.pai.domain.Warehouse

data class ProductDto(
    val id: Int,
    val version: Int,
    val serialNumber: String,
    val status: String,
    val productType: ProductTypeDto,
    val lastUpdate: String,
    val createDate: String,
    val deleted: Boolean,
    val warehouse: WarehouseDto
)

data class ProductTypeDto(
    val id: Int,
    val version: Int,
    val name: String,
    val manufacture: String,
    val cost: Int,
    val deleted: Boolean
)

data class WarehouseDto(
    val id: Int,
    val version: Int,
    val name: String,
    val deleted: Boolean
)

data class ProductDomainToDto(
    var id: Int? = null,
    val createDate: String,
    val lastUpdate: String,
    var productTypeId: Int? = null,
    var serialNumber: String? = null,
    var status: String? = null,
    var warehouseId: Int? = null
)

fun List<ProductDto>.asProductDomainModel(): List<Product> {
    return map {
        Product(
            id = it.id,
            version = it.version,
            serialNumber = it.serialNumber,
            status = it.status,
            productType = it.productType.asDomainModel(),
            lastUpdate = it.lastUpdate,
            createDate = it.createDate,
            deleted = it.deleted,
            warehouse = it.warehouse.asDomainModel()
        )
    }
}

fun List<ProductDto>.asProductDatabaseModel(): List<DatabaseProduct> {
    return map {
        DatabaseProduct(
            id = it.id,
            version = it.version,
            serialNumber = it.serialNumber,
            status = it.status,
            databaseProductTypeId = it.productType.id,
            lastUpdate = it.lastUpdate,
            createDate = it.createDate,
            deleted = it.deleted,
            databaseWarehouseId = it.warehouse.id
        )
    }
}

fun List<ProductTypeDto>.asProductTypeDomainModel(): List<ProductType> {
    return map {
        ProductType(
            id = it.id,
            version = it.version,
            name = it.name,
            manufacture = it.manufacture,
            cost = it.cost,
            deleted = it.deleted
        )
    }
}

fun List<ProductTypeDto>.asProductTypeDatabaseModel(): List<DatabaseProductType> {
    return map {
        DatabaseProductType(
            id = it.id,
            version = it.version,
            name = it.name,
            manufacture = it.manufacture,
            cost = it.cost,
            deleted = it.deleted
        )
    }
}

fun ProductTypeDto.asDomainModel() : ProductType {
    return ProductType(
        id = this.id,
        version = this.version,
        name = this.name,
        manufacture = this.manufacture,
        cost = this.cost,
        deleted = this.deleted
    )
}

fun ProductTypeDto.asDatabaseModel(): DatabaseProductType {
    return DatabaseProductType(
        id = this.id,
        version = this.version,
        name = this.name,
        manufacture = this.manufacture,
        cost = this.cost,
        deleted = this.deleted
    )
}

fun WarehouseDto.asDatabaseModel(): DatabaseWarehouse {
    return DatabaseWarehouse(
        id = this.id,
        version = this.version,
        name = this.name,
        deleted = this.deleted
    )
}

fun WarehouseDto.asDomainModel(): Warehouse {
    return Warehouse(
        id = this.id,
        version = this.version,
        name = this.name,
        deleted = this.deleted
    )
}

fun List<WarehouseDto>.asDomainModel(): List<Warehouse> {
    return map {
        Warehouse(
            id = it.id,
            version = it.version,
            name = it.name,
            deleted = it.deleted
        )
    }
}

fun List<WarehouseDto>.asWarehouseDatabaseModel(): List<DatabaseWarehouse> {
    return map {
        DatabaseWarehouse(
            id = it.id,
            version = it.version,
            name = it.name,
            deleted = it.deleted
        )
    }
}