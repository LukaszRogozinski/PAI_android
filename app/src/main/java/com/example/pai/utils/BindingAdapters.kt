package com.example.pai.utils

import android.opengl.Visibility
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import androidx.databinding.*
import com.example.pai.domain.User
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlin.reflect.typeOf



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

@BindingAdapter("setVisibility")
fun setVisibility(view: View, show: Boolean) {
    if(!show){
        view.visibility = View.GONE
    } else {
        view.visibility = View.VISIBLE
    }
}

@BindingAdapter("isNetworkError", "itemlist")
fun hideIfNetworkError(view: View, isNetWorkError: Boolean, itemlist: Any?) {
    view.visibility = if (itemlist != null) View.GONE else View.VISIBLE

    if(isNetWorkError) {
        view.visibility = View.GONE
    }
}

@BindingAdapter("realValueAttrChanged")
fun setListener(txtView: TextView, listener: InverseBindingListener?) {
    if (listener != null) {
            txtView.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun afterTextChanged(p0: Editable?) {
                    listener.onChange()
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })
    }
}

@BindingAdapter("realValue")
fun setRealValue(txtView: TextView, value: String?){
    if(value != null){
        txtView.text = value
    }
}

@BindingAdapter("setTextIfNotNull")
fun checkUser(txtView: TextView, value: String?) {


    if(value != null && txtView.text != value){
        txtView.text = value
    }
}

@InverseBindingMethods(InverseBindingMethod(type = TextView::class, attribute = "customtext"))
class BindingAdapters{
    companion object{
        @BindingAdapter("customtext")
       @JvmStatic fun setTime(txtView: TextView, value: String?){
            if(value != null && txtView.text != value){
                txtView.text = value
            }
        }

        @JvmStatic
        @BindingAdapter(value = ["customtextAttrChanged"], requireAll = false)
        fun setListener(txtView: TextView, listener: InverseBindingListener?){
            if (listener != null) {
                txtView.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                    }

                    override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                    }

                    override fun afterTextChanged(editable: Editable) {
                        listener.onChange()
                    }
                })
            }
        }

        @InverseBindingAdapter(attribute = "customtext")
       @JvmStatic fun getTime(txtView: TextView): String? {
            return txtView.text.toString()
        }
    }
}

