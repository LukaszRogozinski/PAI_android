package com.example.pai

import android.app.Application
import com.example.pai.features.products.ProductsViewModel
import com.example.pai.homePage.HomePageViewModel
import com.example.pai.login.LoginViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(appModule)
        }
    }
}

val appModule = module {

    viewModel { LoginViewModel() }
    viewModel { HomePageViewModel() }
    viewModel { ProductsViewModel() }
}