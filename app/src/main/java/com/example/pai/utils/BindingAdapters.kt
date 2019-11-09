package com.example.pai.utils

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("priceToDisplay")
fun bindPrice(txtView: TextView, price: Int) {
    txtView.text = price.toString()
}

@BindingAdapter("booleanToString")
fun bindBoolean(txtView: TextView, boolean: Boolean) {
    txtView.text = boolean.toString()
}

@BindingAdapter("isNetworkError", "playlist")
fun hideIfNetworkError(view: View, isNetWorkError: Boolean, playlist: Any?) {
    view.visibility = if (playlist != null) View.GONE else View.VISIBLE

    if(isNetWorkError) {
        view.visibility = View.GONE
    }
}