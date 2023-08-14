package com.shivamkumarjha.baserecyclerview.baserecyclerview.databinder

import android.graphics.Typeface
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.shivamkumarjha.baserecyclerview.baserecyclerview.BaseViewType
import com.shivamkumarjha.baserecyclerview.baserecyclerview.ITEM_TEXT

data class TextHeader(
    val text: String,
    val textSize: Float,
    val fontFamily: Typeface? = null,
    val textStyle: Int = Typeface.NORMAL,
    @ColorRes val textColor: Int = android.R.color.black,
    @DrawableRes val drawableRes: Int? = null,
    val maxLines: Int = 1,
    val isUnderline: Boolean = false,
    val leftMargin: Int = 0,
    val topMargin: Int = 0,
    val rightMargin: Int = 0,
    val bottomMargin: Int = 0,
) : BaseViewType {

    override val baseViewType: Int
        get() = ITEM_TEXT
}