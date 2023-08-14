package com.shivamkumarjha.baserecyclerview.baserecyclerview.util

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.core.content.ContextCompat

fun Context.getColorById(id: Int) = ContextCompat.getColor(this, id)

fun Context.getDrawableById(id: Int) = ContextCompat.getDrawable(this, id)

fun Context.toast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Context?.isValidContext(): Boolean {
    return when (this) {
        null -> false
        else -> if (this is Activity) {
            !(this.isDestroyed || this.isFinishing)
        } else {
            true
        }
    }
}