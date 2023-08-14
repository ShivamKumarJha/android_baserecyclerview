package com.shivamkumarjha.baserecyclerview.baserecyclerview.util

import android.content.res.Resources

fun Int.isValidIndex(items: List<*>? = null): Boolean {
    return if (items != null) {
        this > -1 && this < items.size
    } else {
        this > -1
    }
}

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()