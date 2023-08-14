package com.shivamkumarjha.baserecyclerview.baserecyclerview

import android.annotation.SuppressLint
import androidx.annotation.IntDef

const val ITEM_TODO = 0

@SuppressLint("UniqueConstants")
@IntDef(
    ITEM_TODO,
)
@Retention(AnnotationRetention.SOURCE)
annotation class ViewType