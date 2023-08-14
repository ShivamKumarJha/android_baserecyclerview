package com.shivamkumarjha.baserecyclerview.baserecyclerview.util

fun Int.isValidIndex(items: List<*>? = null): Boolean {
    return if (items != null) {
        this > -1 && this < items.size
    } else {
        this > -1
    }
}