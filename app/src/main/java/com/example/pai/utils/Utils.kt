package com.example.pai.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.pai.MainNavigationDirections
import com.example.pai.R
import com.example.pai.repository.SessionRepository
import com.shreyaspatil.MaterialDialog.MaterialDialog
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.util.*

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

        fun onBackPressed(
            activity: FragmentActivity,
            sessionRepository: SessionRepository,
            fragment: Fragment
        ): OnBackPressedCallback {
            return object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    logOutDialog(activity, sessionRepository, fragment)
                }

            }
        }

        @SuppressLint("RestrictedApi")
        fun logOutDialog(activity: FragmentActivity, sessionRepository: SessionRepository, fragment: Fragment) {
            val mDialog: MaterialDialog = MaterialDialog.Builder(activity)
                .setTitle("Logout")
                .setMessage("Are you sure want to logout")
                .setCancelable(false)
                .setPositiveButton(
                    "Logout", R.drawable.ic_exit_to_app_24px
                ) { dialogInterface, _ ->
                    sessionRepository.logout()
                    dialogInterface?.dismiss()
                    val action = MainNavigationDirections.actionGlobalLoginFragment()
                    NavHostFragment.findNavController(fragment).navigate(action)
//                    activity.finish()
                }
                .setNegativeButton(
                    "Cancel", R.drawable.ic_close_24px
                ) { dialogInterface, _ -> dialogInterface?.dismiss() }
                .build()

            mDialog.show()
        }
    }
}

class UuidAdapter {

    @ToJson
    fun toJson(uuid: UUID) : String {
        return uuid.toString()
    }

    @FromJson
    fun fromJson(json: String) : UUID {
        return UUID.fromString(json)
    }
}
