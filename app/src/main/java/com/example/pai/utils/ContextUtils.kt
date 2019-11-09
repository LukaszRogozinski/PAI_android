package com.example.pai.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog

fun Context.isConnectedToNetwork(): Boolean {
    val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
    return activeNetwork?.isConnectedOrConnecting == true
}

fun Context.buildAlertDialog() {
    val builder = AlertDialog.Builder(this)
    builder.setTitle("No internet connection")
    builder.setMessage("You need to have access to internet to perform this action")
    builder.setPositiveButton("OK"){dialog, which ->
    }
    val dialog: AlertDialog = builder.create()
    dialog.show()
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}