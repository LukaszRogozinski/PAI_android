package com.example.pai.repository

import com.example.pai.domain.User
import com.example.pai.network.*
import retrofit2.Response
import java.util.*

class NetworkRepository {

//    suspend fun getProductsFromDatabase(): List<Product> {
//        return database.productDao.getDatabaseProducts().asDomainModel(database)
//    }
//
//    suspend fun getWarehousesFromDatabase(): List<Department> {
//        return database.warehouseDao.getDatabaseWarehouses().asWarehouseDomainModel()
//    }

    suspend fun loadProducts() : Response<List<ProductDto>> {
        return PaiApi.retrofitService.getProducts()
    }

    suspend fun deleteProduct(id: UUID) : Response<Unit> {
        return PaiApi.retrofitService.deleteProductAsync(id)
    }

    suspend fun loadUsers() : Response<List<UserDto>> {
        return PaiApi.retrofitService.getUsers()
    }

    suspend fun deleteUser(username: String) : Response<Unit> {
        return PaiApi.retrofitService.deleteUserAsync(username)
    }
    suspend fun getDepartmentsFromNetwork() : Response<List<DepartmentDto>> {
        return PaiApi.retrofitService.getDepartments()
    }

    suspend fun getProductTypesFromNetwork() : Response<List<ProductTypeDto>> {
        return PaiApi.retrofitService.getProductTypes()
    }

    suspend fun createNewUserByAdminNetwork(newUserDto: NewUserDto) : Response<Unit> {
        return PaiApi.retrofitService.createNewUserByAdmin(newUserDto)
    }

    suspend fun updateUserByAdminNetwork(updateUserDto: UpdateUserDto) : Response<Unit> {
        return PaiApi.retrofitService.updateUserByAdmin(updateUserDto)
    }

    suspend fun updatePasswordByAdminNetwork(updatePasswordDto: UpdatePasswordDto) : Response<Unit> {
        return PaiApi.retrofitService.updatePasswordByAdmin(updatePasswordDto)
    }

    suspend fun logInSuspend(username: String) : Response<UserDto> {
        return PaiApi.retrofitService.getLoggedUser(username)
    }

    fun logIn(username: String, password: String) : Boolean {
        return username=="admin" && password=="admin"
    }

//    suspend fun refreshProductDatabase() {
//        withContext(Dispatchers.IO) {
//            val products = PaiApi.retrofitService.getProducts()
//            val productsType: MutableList<ProductTypeDto> = mutableListOf()
//            val warehouses: MutableList<DepartmentDto> = mutableListOf()
//            products.forEach {
//                productsType.add(it.productType)
//                warehouses.add(it.department)
//            }
//            database.warehouseDao.insertAll(warehouses.asWarehouseDatabaseModel())
//            database.productTypeDao.insertAll(productsType.asProductTypeDatabaseModel())
//            database.productDao.insertAll(products.asProductDatabaseModel())
//        }
//    }

//    suspend fun addProduct(productDomainToDto: ProductDomainToDto) : Response<Unit> {
//       return PaiApi.retrofitService.addProduct(productDomainToDto)
//    }
//
//    suspend fun updateProduct(productDomainToDto: ProductDomainToDto) : Response<Unit> {
//        return PaiApi.retrofitService.updateProduct(productDomainToDto)
//    }



//    suspend fun deleteProductFromDatabase(id: Int) {
//        database.productDao.delete(id)
//    }
}