package com.example.pai.utils

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.pai.domain.User

@BindingAdapter("intToString")
fun bindInt(txtView: TextView, integer: Int?) {
    if(integer != null) {
        txtView.text = integer.toString()
    }
}

@BindingAdapter("priceToDisplay")
fun bindPrice(txtView: TextView, price: Int) {
    txtView.text = price.toString()
}

@BindingAdapter("booleanToString")
fun bindBoolean(txtView: TextView, boolean: Boolean) {
    txtView.text = boolean.toString()
}

@BindingAdapter("flatNumber")
fun bindFlatNumber(txtView: TextView, text: String?) {
    if(text != null) {
        txtView.text = "/ $text"
    }
}

@BindingAdapter("isNetworkError", "itemlist")
fun hideIfNetworkError(view: View, isNetWorkError: Boolean, itemlist: Any?) {
    view.visibility = if (itemlist != null) View.GONE else View.VISIBLE

    if(isNetWorkError) {
        view.visibility = View.GONE
    }
}

@BindingAdapter("setTextIfNotNull")
fun checkUser(txtView: TextView, value: String?) {
    if(value != null){
        txtView.text = value
    }
}