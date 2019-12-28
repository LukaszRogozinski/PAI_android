package com.example.pai

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
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
                if (destination.id == R.id.loginFragment ||
                    destination.id == R.id.productDetailFragment ||
                    destination.id == R.id.userDetailFragment ||
                    destination.id == R.id.userEditFragment ||
                            destination.id == R.id.changeUserPasswordFragment
                ) {
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
