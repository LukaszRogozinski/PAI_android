package com.example.pai

import android.app.Application
import android.preference.PreferenceManager
import com.example.pai.features.products.ProductsViewModel
import com.example.pai.features.login.LoginViewModel
import com.example.pai.features.products.detail.ProductDetailFragmentArgs
import com.example.pai.features.products.detail.ProductDetailViewModel
import com.example.pai.features.products.types.ProductTypesViewModel
import com.example.pai.features.users.UsersViewModel
import com.example.pai.features.users.edit.UserEditViewModel
import com.example.pai.repository.NetworkRepository
import com.example.pai.repository.SessionRepository
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

    single { NetworkRepository() }

    viewModel { LoginViewModel(get()) }
    viewModel { ProductsViewModel(get()) }
    viewModel { ProductDetailViewModel(get()) }
    viewModel { UsersViewModel(get()) }
    viewModel { ProductTypesViewModel(get()) }
    viewModel { UserEditViewModel(get()) }
//    single { SessionRepository(PreferenceManager.getDefaultSharedPreferences(androidContext())) }
    //viewModel { ProductDetailViewModel() }
}