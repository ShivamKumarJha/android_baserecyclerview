package com.shivamkumarjha.baserecyclerview.baserecyclerview.util

import android.graphics.Bitmap
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmapOrNull
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.signature.ObjectKey
import jp.wasabeef.glide.transformations.BlurTransformation

/**
 * Use it to load images programmatically
 */
fun ImageView.loadImage(
    file: Any?,
    placeholder: Any? = null,
    isCircle: Boolean = false,
    cornerRadius: Int = 0,
    showGreyScale: Boolean = false,
    isBlur: Boolean = false,
    objectKey: Any? = null,
    isVideo: Boolean = false,
    fitCenter: Boolean = false,
    onResourceReady: () -> Unit = {},
    onBitmapReady: (Bitmap?) -> Unit = {},
) {
    if ((file == null && placeholder == null) ||
        !this.context.isValidContext() ||
        (file != null && file !is String &&
                file !is Uri && file !is Drawable &&
                file !is Int && file !is Bitmap)
    ) {
        return
    }

    var builder = Glide.with(this).load(file)

    //Signature
    if (objectKey != null) {
        builder = builder.signature(ObjectKey(objectKey))
    }

    //Set the placeholder
    if (placeholder != null && placeholder is Int) {
        builder = builder.placeholder(placeholder).error(placeholder)
    } else if (placeholder != null && placeholder is Drawable) {
        builder = builder.placeholder(placeholder).error(placeholder)
    }
    if (isCircle) {
        builder = builder.circleCrop()
    }

    if (cornerRadius > 0 && isBlur) {
        builder = builder.transform(
            CenterCrop(),
            BlurTransformation(),
            RoundedCorners(cornerRadius)
        )
    } else if (cornerRadius > 0) {
        builder = builder.transform(
            CenterCrop(), RoundedCorners(cornerRadius)
        )
    } else if (isBlur) {
        builder = builder.transform(CenterCrop(), BlurTransformation())
    }

    if (isVideo) {
        builder = builder.sizeMultiplier(1f)
    }

    if (fitCenter && !isBlur) {
        builder = builder.fitCenter()
    }

    val listener = object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            onResourceReady()
            onBitmapReady(resource?.toBitmapOrNull())
            return false
        }
    }
    builder.listener(listener).into(this)
    if (showGreyScale) {
        this.createImageFilter()
    } else {
        this.clearColorFilter()
    }
}

fun ImageView.createImageFilter() {
    val colorFilter = ColorMatrixColorFilter(ColorMatrix().apply {
        setSaturation(0f)
    })
    this.colorFilter = colorFilter
}