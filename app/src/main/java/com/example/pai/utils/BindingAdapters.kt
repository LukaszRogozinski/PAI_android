package com.example.pai.utils

import android.widget.Spinner
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