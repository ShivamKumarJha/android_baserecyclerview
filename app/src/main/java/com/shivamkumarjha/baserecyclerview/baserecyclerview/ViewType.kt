package com.shivamkumarjha.baserecyclerview.baserecyclerview

import android.annotation.SuppressLint
import androidx.annotation.IntDef

const val ITEM_TEXT = 0
const val ITEM_IMAGES = 1
const val ITEM_IMAGE = 2

@SuppressLint("UniqueConstants")
@IntDef(
    ITEM_TEXT,
    ITEM_IMAGES,
    ITEM_IMAGE,
)
@Retention(AnnotationRetention.SOURCE)
annotation class ViewType