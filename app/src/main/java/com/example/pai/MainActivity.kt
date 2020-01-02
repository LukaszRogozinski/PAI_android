package com.example.pai

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.core.view.get
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.pai.repository.SessionRepository
import com.example.pai.utils.asVisibility
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavController()
    }

    private fun setupNavController() {
        val sessionRepository: SessionRepository = get()
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
                if (destination.id == R.id.productsFragment) {
                    bottomNavigation.menu.findItem(R.id.usersFragment).isVisible = sessionRepository.isAdmin()
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
