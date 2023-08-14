package com.shivamkumarjha.baserecyclerview.baserecyclerview.util

import android.content.Context
import android.widget.Toast
import androidx.core.content.ContextCompat

fun Context.getColorById(id: Int) = ContextCompat.getColor(this, id)

fun Context.getDrawableById(id: Int) = ContextCompat.getDrawable(this, id)

fun Context.toast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()