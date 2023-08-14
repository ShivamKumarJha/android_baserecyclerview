package com.shivamkumarjha.baserecyclerview.baserecyclerview.databinder

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shivamkumarjha.baserecyclerview.baserecyclerview.ITEM_TEXT
import com.shivamkumarjha.baserecyclerview.baserecyclerview.ViewDataBinder
import com.shivamkumarjha.baserecyclerview.baserecyclerview.util.dp
import com.shivamkumarjha.baserecyclerview.baserecyclerview.util.getColorById
import com.shivamkumarjha.baserecyclerview.baserecyclerview.util.removeDrawables
import com.shivamkumarjha.baserecyclerview.baserecyclerview.util.setDrawableWithStartPadding
import com.shivamkumarjha.baserecyclerview.baserecyclerview.util.setMargins
import com.shivamkumarjha.baserecyclerview.databinding.ItemTextHeaderBinding

class TextHeaderDataBinder(
    private val onTextClick: (String) -> Unit = {}
) : ViewDataBinder<ItemTextHeaderBinding, TextHeader>() {

    override val viewType: Int
        get() = ITEM_TEXT

    override fun createBinder(parent: ViewGroup): ItemTextHeaderBinding {
        val binding = ItemTextHeaderBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        binding.tvHeaderChats.setOnClickListener {
            val data = binding.data ?: return@setOnClickListener
            onTextClick(data.text)
        }
        return binding
    }

    override fun bindData(
        binding: ItemTextHeaderBinding,
        data: TextHeader,
        position: Int,
        itemCount: Int,
    ) {
        binding.data = data
        binding.itemPosition = position

        binding.tvHeaderChats.text = data.text
        binding.tvHeaderChats.setTextSize(TypedValue.COMPLEX_UNIT_SP, data.textSize)
        binding.tvHeaderChats.setTypeface(null, data.textStyle)
        binding.tvHeaderChats.setTextColor(binding.tvHeaderChats.context.getColorById(data.textColor))
        binding.tvHeaderChats.maxLines = data.maxLines
        data.fontFamily?.let { fontFamily ->
            binding.tvHeaderChats.typeface = fontFamily
        }

        if (data.isUnderline) {
            binding.tvHeaderChats.paintFlags =
                binding.tvHeaderChats.paintFlags or android.graphics.Paint.UNDERLINE_TEXT_FLAG
        } else {
            binding.tvHeaderChats.paintFlags =
                binding.tvHeaderChats.paintFlags and android.graphics.Paint.UNDERLINE_TEXT_FLAG.inv()
        }

        if (data.drawableRes != null) {
            binding.tvHeaderChats.setDrawableWithStartPadding(data.drawableRes, 6.dp)
        } else {
            binding.tvHeaderChats.removeDrawables()
        }

        //Margins
        binding.tvHeaderChats.setMargins(
            data.leftMargin,
            data.topMargin,
            data.rightMargin,
            data.bottomMargin,
        )
    }
}