package com.shivamkumarjha.baserecyclerview.baserecyclerview.databinder

import com.shivamkumarjha.baserecyclerview.baserecyclerview.BaseViewType
import com.shivamkumarjha.baserecyclerview.baserecyclerview.ITEM_IMAGE

data class MainImage(
    val imageUrl: String
) : BaseViewType {

    override val baseViewType: Int
        get() = ITEM_IMAGE
}