package com.example.pai.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Product(
    val id: UUID,
    val version: Int,
    val serialNumber: String,
    val status: String,
    val productType: ProductType,
    val lastUpdate: String,
    val createDate: String,
    val deleted: Boolean,
    val department: Department
) : Parcelable

@Parcelize
data class ProductType(
    val id: UUID,
    val version: Int,
    val name: String,
    val manufacture: String,
    val cost: Int,
    val deleted: Boolean
) : Parcelable

@Parcelize
data class Department(
    val id: UUID,
    val version: Int,
    val name: String,
    val deleted: Boolean
) : Parcelable

@Parcelize
data class User(
    val id: UUID,
    val version: Int,
    val username: String,
    val password: String,
    val accountExpired: Boolean,
    val accountLocked: Boolean,
    val credentialsExpired: Boolean,
    val enabled: Boolean,
    val userdata: Userdata,
    val authorities: List<Authority>,
    val credentialsNonExpired: Boolean,
    val accountNonLocked: Boolean,
    val accountNonExpired: Boolean
    ) : Parcelable

@Parcelize
data class Userdata(
    val id: UUID,
    val version: Int,
    val name: String,
    val surname: String,
    val email: String,
    val position: String?,
    val workplace: String?,
    val dateOfJoin: String,
    val address: Address
) : Parcelable

@Parcelize
data class Address(
    val id: UUID,
    val version: Int,
    val city: String,
    val street: String,
    val buildingNumber: String,
    val flatNumber: String?,
    val deleted: Boolean
) : Parcelable

@Parcelize
data class Authority(
    val id: UUID,
    val name: String,
    val active: Boolean,
    val authority: String
) : Parcelable


data class LoggedUser(
    val id: UUID,
    val username: String,
    val password: String,
    val token: String
)
//fun Product.asProductDtoToSave() : ProductDomainToDto {
//    return ProductDomainToDto(
//        id = this.id,
//        createDate = LocalDateTime.now().toString(),
//        lastUpdate = LocalDateTime.now().toString(),
//        productTypeId = this.productType.id,
//        serialNumber = this.serialNumber,
//        status = this.status,
//        warehouseId = this.department.id)
//}