package com.shivamkumarjha.baserecyclerview.baserecyclerview

import android.annotation.SuppressLint
import androidx.annotation.IntDef

const val ITEM_TEXT = 0

@SuppressLint("UniqueConstants")
@IntDef(
    ITEM_TEXT,
)
@Retention(AnnotationRetention.SOURCE)
annotation class ViewType