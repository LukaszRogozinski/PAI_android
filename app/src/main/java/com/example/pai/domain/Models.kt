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
    var id: UUID? = null,
    var version: Int? = null,
    var username: String? = null,
    var password: String? = null,
    var accountExpired: Boolean? = null,
    var accountLocked: Boolean? = null,
    var credentialsExpired: Boolean? = null,
    var enabled: Boolean? = null,
    var userdata: Userdata = Userdata(),
    var authorities: List<Authority>? = null,
    var credentialsNonExpired: Boolean? = null,
    var accountNonLocked: Boolean? = null,
    var accountNonExpired: Boolean? = null
    ) : Parcelable

@Parcelize
data class Userdata(
    var id: UUID? = null,
    var version: Int? = null,
    var name: String? = null,
    var surname: String?= null,
    var email: String?= null,
    var position: String?= null,
    var workplace: String?= null,
    var dateOfJoin: String?= null,
    var address: Address= Address()
) : Parcelable

@Parcelize
data class Address(
    var id: UUID?= null,
    var version: Int?= null,
    var city: String?= null,
    var street: String?= null,
    var buildingNumber: String?= null,
    var flatNumber: String?= null,
    var deleted: Boolean?= null
) : Parcelable

@Parcelize
data class Authority(
    val id: UUID,
    val name: String,
    val active: Boolean,
    val authority: String
) : Parcelable

@Parcelize
data class NewPassword(
    var newPassword: String? = null,
    var oldPassword: String? = null,
    var username: String? = null
) : Parcelable

@Parcelize
data class LoggedUser(
    val user: User,
    val token: String
) : Parcelable

fun User.asLoggedUser() : LoggedUser {
    return LoggedUser(
        this,
        "generated_token" //TODO
    )
}

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