package com.shivamkumarjha.baserecyclerview.baserecyclerview.databinder

import com.shivamkumarjha.baserecyclerview.baserecyclerview.BaseViewType
import com.shivamkumarjha.baserecyclerview.baserecyclerview.ITEM_IMAGES

data class MainImages(
    val images: ArrayList<MainImage>
) : BaseViewType {

    override val baseViewType: Int
        get() = ITEM_IMAGES
}