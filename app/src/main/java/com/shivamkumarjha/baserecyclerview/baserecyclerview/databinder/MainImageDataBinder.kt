package com.shivamkumarjha.baserecyclerview.baserecyclerview.databinder

import android.view.LayoutInflater
import android.view.ViewGroup
import com.shivamkumarjha.baserecyclerview.baserecyclerview.ITEM_IMAGE
import com.shivamkumarjha.baserecyclerview.baserecyclerview.ViewDataBinder
import com.shivamkumarjha.baserecyclerview.baserecyclerview.util.dp
import com.shivamkumarjha.baserecyclerview.baserecyclerview.util.loadImage
import com.shivamkumarjha.baserecyclerview.databinding.ItemImageBinding

class MainImageDataBinder : ViewDataBinder<ItemImageBinding, MainImage>() {

    override val viewType: Int
        get() = ITEM_IMAGE

    override fun createBinder(parent: ViewGroup): ItemImageBinding {
        return ItemImageBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    }

    override fun bindData(
        binding: ItemImageBinding,
        data: MainImage,
        position: Int,
        itemCount: Int
    ) {
        binding.ivImage.loadImage(data.imageUrl, cornerRadius = 8.dp)
    }
}