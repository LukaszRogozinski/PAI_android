package com.example.pai.network

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