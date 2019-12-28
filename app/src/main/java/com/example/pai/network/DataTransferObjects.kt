package com.example.pai.network

import com.example.pai.domain.*
import java.util.*

data class ProductDto(
    val id: UUID,
    val version: Int,
    val serialNumber: String,
    val status: String,
    val productType: ProductTypeDto,
    val lastUpdate: String,
    val createDate: String,
    val deleted: Boolean,
    val department: DepartmentDto
)

data class ProductTypeDto(
    val id: UUID,
    val version: Int,
    val name: String,
    val manufacture: String,
    val cost: Int,
    val deleted: Boolean
)

data class DepartmentDto(
    val id: UUID,
    val version: Int,
    val name: String,
    val deleted: Boolean
)

data class UserDto(
    val id: UUID,
    val version: Int,
    val username: String,
    val password: String,
    val accountExpired: Boolean,
    val accountLocked: Boolean,
    val credentialsExpired: Boolean,
    val enabled: Boolean,
    val userdata: UserdataDto,
    val authorities: List<AuthorityDto>,
    val credentialsNonExpired: Boolean,
    val accountNonLocked: Boolean,
    val accountNonExpired: Boolean
)

data class UserdataDto(
    val id: UUID,
    val version: Int,
    val name: String,
    val surname: String,
    val email: String,
    val position: String?,
    val workplace: String?,
    val dateOfJoin: String,
    val address: AddressDto
)

data class AddressDto(
    val id: UUID,
    val version: Int,
    val city: String,
    val street: String,
    val buildingNumber: String,
    val flatNumber: String?,
    val deleted: Boolean

)

data class AuthorityDto(
    val id: UUID,
    val name: String,
    val active: Boolean,
    val authority: String
)

data class NewUserDto(
    val city: String,
    val email: String,
    val flatNumber: String? = null,
    val houseNumber: String,
    val name: String,
    val password: String,
    val position: String? = null,
    val roles: List<String>,
    val street: String,
    val surname: String,
    val username: String,
    val workplace: String? = null,
    val versionUser: Int? = null,
    val versionUserData: Int? = null
)

data class UpdateUserDto(
    val city: String,
    val email: String,
    val flatNumber: String,
    val houseNumber: String,
    val id: UUID,
    val name: String,
    val position: String? = null,
    val roles: List<String>,
    val street: String,
    val surname: String,
    val username: String,
    val workplace: String? = null
)

data class UpdatePasswordDto(
    val newPassword: String,
    val oldPassword: String,
    val username: String
)

fun NewPassword.asUpdatePasswordDto() : UpdatePasswordDto {
    return UpdatePasswordDto(
        newPassword = this.newPassword!!,
        oldPassword = this.oldPassword!!,
        username = this.username!!
    )
}

fun User.asUpdateUserDto(authorities: List<String>): UpdateUserDto {
    return UpdateUserDto(
    city = this.userdata.address.city!!,
        email = this.userdata.email!!,
        flatNumber = this.userdata.address.flatNumber!!,
        houseNumber = this.userdata.address.buildingNumber!!,
        id = this.id!!,
        name = this.userdata.name!!,
        position = this.userdata.position,
        roles = authorities,//TODO
    street = this.userdata.address.street!!,
        surname = this.userdata.surname!!,
        username = this.username!!,
        workplace = this.userdata.workplace
    )
}

fun User.asNewUserDto(authorities: List<String>): NewUserDto {
    return NewUserDto(
        city = this.userdata.address.city!!,
        email = this.userdata.email!!,
        flatNumber = this.userdata.address.flatNumber,
        houseNumber = this.userdata.address.buildingNumber!!,
        name = this.userdata.name!!,
        password = this.password!!,
        position = this.userdata.position,
        roles = authorities,//TODO
        street = this.userdata.address.street!!,
        surname = this.userdata.surname!!,
        username = this.username!!,
        workplace = this.userdata.workplace,
        versionUser = this.version,
        versionUserData = this.userdata.version
    )
}


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
            department = it.department.asDomainModel()
        )
    }
}

fun List<UserDto>.asUserDomainModel(): List<User> {
    return map {
        User(
            id = it.id,
            username = it.username,
            version = it.version,
            password = it.password,
            accountExpired = it.accountExpired,
            accountLocked = it.accountLocked,
            credentialsExpired = it.credentialsExpired,
            enabled = it.enabled,
            userdata = it.userdata.asDomainModel(),
            authorities = it.authorities.asAuthorityDomainModel(),
            credentialsNonExpired = it.credentialsNonExpired,
            accountNonExpired = it.accountNonExpired,
            accountNonLocked = it.accountNonLocked
        )
    }
}

fun List<AuthorityDto>.asAuthorityDomainModel(): List<Authority> {
    return map {
        Authority(
            id = it.id,
            name = it.name,
            active = it.active,
            authority = it.authority
        )
    }
}

fun UserdataDto.asDomainModel(): Userdata {
    return Userdata(
        id = this.id,
        version = this.version,
        surname = this.surname,
        name = this.name,
        email = this.email,
        position = this.position,
        workplace = this.workplace,
        dateOfJoin = this.dateOfJoin,
        address = this.address.asDomainModel()
    )
}

fun AddressDto.asDomainModel(): Address {
    return Address(
        id = this.id,
        version = this.version,
        city = this.city,
        buildingNumber = this.buildingNumber,
        street = this.street,
        flatNumber = this.flatNumber,
        deleted = this.deleted
    )
}

//fun List<ProductDto>.asProductDatabaseModel(): List<DatabaseProduct> {
//    return map {
//        DatabaseProduct(
//            id = it.id,
//            version = it.version,
//            serialNumber = it.serialNumber,
//            status = it.status,
//            databaseProductTypeId = it.productType.id,
//            lastUpdate = it.lastUpdate,
//            createDate = it.createDate,
//            deleted = it.deleted,
//            databaseWarehouseId = it.department.id
//        )
//    }
//}

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

//fun List<ProductTypeDto>.asProductTypeDatabaseModel(): List<DatabaseProductType> {
//    return map {
//        DatabaseProductType(
//            id = it.id,
//            version = it.version,
//            name = it.name,
//            manufacture = it.manufacture,
//            cost = it.cost,
//            deleted = it.deleted
//        )
//    }
//}

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

//fun ProductTypeDto.asDatabaseModel(): DatabaseProductType {
//    return DatabaseProductType(
//        id = this.id,
//        version = this.version,
//        name = this.name,
//        manufacture = this.manufacture,
//        cost = this.cost,
//        deleted = this.deleted
//    )
//}

//fun DepartmentDto.asDatabaseModel(): DatabaseWarehouse {
//    return DatabaseWarehouse(
//        id = this.id,
//        version = this.version,
//        name = this.name,
//        deleted = this.deleted
//    )
//}

fun DepartmentDto.asDomainModel(): Department {
    return Department(
        id = this.id,
        version = this.version,
        name = this.name,
        deleted = this.deleted
    )
}

fun List<DepartmentDto>.asDomainModel(): List<Department> {
    return map {
        Department(
            id = it.id,
            version = it.version,
            name = it.name,
            deleted = it.deleted
        )
    }
}

//fun List<DepartmentDto>.asWarehouseDatabaseModel(): List<DatabaseWarehouse> {
//    return map {
//        DatabaseWarehouse(
//            id = it.id,
//            version = it.version,
//            name = it.name,
//            deleted = it.deleted
//        )
//    }
//}