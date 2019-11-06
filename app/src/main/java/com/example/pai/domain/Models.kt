package com.example.pai.domain

import android.os.Parcelable
import com.example.pai.network.ProductDomainToDto
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime

@Parcelize
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
) : Parcelable

@Parcelize
data class ProductType(
    val id: Int,
    val version: Int,
    val name: String,
    val manufacture: String,
    val cost: Int,
    val deleted: Boolean
) : Parcelable

@Parcelize
data class Warehouse(
    val id: Int,
    val version: Int,
    val name: String,
    val deleted: Boolean
) : Parcelable

fun Product.asProductDtoToSave() : ProductDomainToDto {
    return ProductDomainToDto(
        id = this.id,
        createDate = LocalDateTime.now().toString(),
        lastUpdate = LocalDateTime.now().toString(),
        productTypeId = this.productType.id,
        serialNumber = this.serialNumber,
        status = this.status,
        warehouseId = this.warehouse.id)
}