package com.shivamkumarjha.baserecyclerview.baserecyclerview.util

import android.graphics.BlurMaskFilter
import android.graphics.Paint
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.text.HtmlCompat

fun TextView.blurText() {
    val radius = textSize / 3
    val filter = BlurMaskFilter(radius, BlurMaskFilter.Blur.NORMAL)
    setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    paint.maskFilter = filter
}

fun TextView.clearBlur() {
    paint.maskFilter = null
}

fun TextView.setHtmlText(string: String) {
    text = HtmlCompat.fromHtml(string, HtmlCompat.FROM_HTML_MODE_COMPACT)
}

fun TextView.underline() {
    this.paintFlags = Paint.UNDERLINE_TEXT_FLAG
}

fun TextView.setDrawableWithStartPadding(@DrawableRes drawableResId: Int, paddingDp: Int) {
    val drawable = context.getDrawableById(drawableResId) ?: return
    val drawableWithPadding = drawable.mutate()
    val padding = paddingDp.dp
    drawableWithPadding.setBounds(
        padding,
        0,
        drawableWithPadding.intrinsicWidth + padding,
        drawableWithPadding.intrinsicHeight
    )
    setCompoundDrawablesWithIntrinsicBounds(drawableWithPadding, null, null, null)
    compoundDrawablePadding = padding / 3
}

fun TextView.setDrawableWithEndPadding(@DrawableRes drawableResId: Int, paddingDp: Int) {
    val drawable = context.getDrawableById(drawableResId) ?: return
    val drawableWithPadding = drawable.mutate()
    val padding = paddingDp.dp
    drawableWithPadding.setBounds(
        padding,
        0,
        drawableWithPadding.intrinsicWidth + padding,
        drawableWithPadding.intrinsicHeight
    )
    setCompoundDrawablesWithIntrinsicBounds(null, null, drawableWithPadding, null)
    compoundDrawablePadding = padding / 3
}

fun TextView.removeDrawables() {
    setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
}

fun TextView.setMargins(left: Int = 0, top: Int = 0, right: Int = 0, bottom: Int = 0) {
    val layoutParams = layoutParams as ViewGroup.MarginLayoutParams
    layoutParams.setMargins(left, top, right, bottom)
    requestLayout()
}