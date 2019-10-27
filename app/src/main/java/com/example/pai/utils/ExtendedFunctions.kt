package com.example.pai.utils

import android.view.View

val Boolean.asVisibility get() = if(this) View.VISIBLE else View.GONE