package com.example.pai

import android.app.Application
import com.example.pai.features.products.ProductsViewModel
import com.example.pai.features.login.LoginViewModel
import com.example.pai.features.products.detail.ProductDetailViewModel
import com.example.pai.features.products.types.ProductTypesViewModel
import com.example.pai.features.users.UsersViewModel
import com.example.pai.features.users.changepassword.ChangeUserPasswordViewModel
import com.example.pai.features.users.detail.UserDetailViewModel
import com.example.pai.features.users.edit.UserEditViewModel
import com.example.pai.repository.ProductRepository
import com.example.pai.repository.SessionRepository
import com.example.pai.repository.UserRepository
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        Timber.plant(Timber.DebugTree())
        startKoin {
            androidContext(this@App)
            modules(appModule)
        }
    }
}

val appModule = module {

    single { ProductRepository() }
    single { UserRepository() }
    single { SessionRepository() }

    viewModel { LoginViewModel(get()) }
    viewModel { ProductsViewModel(get(), get()) }
    viewModel { ProductDetailViewModel(get(), get()) }
    viewModel { UsersViewModel(get(), get()) }
    viewModel { ProductTypesViewModel(get(), get()) }
    viewModel { UserEditViewModel(get(), get()) }
    viewModel { ChangeUserPasswordViewModel(get(), get()) }
    viewModel { UserDetailViewModel(get(), get()) }
}