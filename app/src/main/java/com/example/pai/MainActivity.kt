package com.example.pai

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.pai.features.products.ProductsFragment
import com.example.pai.features.users.UsersFragment
import com.example.pai.features.warehouses.WarehousesFragment
import com.example.pai.utils.asVisibility
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavController()
    }

    private fun setupNavController() {
        findNavController(R.id.nav_host_fragment).apply {
            addOnDestinationChangedListener { _, destination, _ ->
                if (destination.id == R.id.loginFragment || destination.id == R.id.productDetailFragment) {
                    updateNavigationUiVisibility(false)
                } else {
                    updateNavigationUiVisibility(true)
                }
            }
            bottomNavigation.setupWithNavController(this)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val navController = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val currentFragment = navController?.childFragmentManager!!.fragments[0]
        if (currentFragment is UsersFragment ||
            currentFragment is WarehousesFragment ||
            currentFragment is ProductsFragment
        ) {
            finish()
        }
    }

    private fun updateNavigationUiVisibility(visible: Boolean) {
        window.decorView.post {
            bottomNavigation.visibility = visible.asVisibility
        }
    }
}
