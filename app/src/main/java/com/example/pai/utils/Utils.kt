package com.example.pai.utils

import android.content.Context
import android.os.Handler
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentActivity

val Boolean.asVisibility get() = if(this) View.VISIBLE else View.GONE

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

class Utils {
    companion object {
        fun onBackPressedCallback(
            activity: FragmentActivity,
            context: Context
        ): OnBackPressedCallback {
            return object :
                OnBackPressedCallback(true) {

                private var doubleBackToExitPressedOnce = false
                override fun handleOnBackPressed() {
                    if (doubleBackToExitPressedOnce) {
                        activity.finish()
                        return
                    }
                    doubleBackToExitPressedOnce = true
                    Toast.makeText(
                        context,
                        "Please click BACK again to exit",
                        Toast.LENGTH_SHORT
                    ).show()
                    Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
                }
            }
        }
    }
}
