package com.example.pai

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
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
                if (destination.id == R.id.loginFragment) {
                    updateNavigationUiVisibility(false)
                } else {
                    updateNavigationUiVisibility(true)
                }
            }
            bottomNavigation.setupWithNavController(this)
        }
    }

    private fun updateNavigationUiVisibility(visible: Boolean) {
        window.decorView.post {
            bottomNavigation.visibility = visible.asVisibility
        }
    }
}
