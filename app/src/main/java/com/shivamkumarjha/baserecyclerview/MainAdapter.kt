package com.shivamkumarjha.baserecyclerview

import androidx.recyclerview.widget.RecyclerView
import com.shivamkumarjha.baserecyclerview.baserecyclerview.BaseRecyclerAsyncAdapter
import com.shivamkumarjha.baserecyclerview.baserecyclerview.BaseViewType
import com.shivamkumarjha.baserecyclerview.baserecyclerview.databinder.MainImagesDataBinder
import com.shivamkumarjha.baserecyclerview.baserecyclerview.databinder.TextHeader
import com.shivamkumarjha.baserecyclerview.baserecyclerview.databinder.TextHeaderDataBinder

class MainAdapter(
    recycledViewPool: RecyclerView.RecycledViewPool,
    onTextClick: (String) -> Unit
) : BaseRecyclerAsyncAdapter<BaseViewType>() {

    init {
        addViewBinder(TextHeaderDataBinder(onTextClick))
        addViewBinder(MainImagesDataBinder(recycledViewPool))
    }

    fun getHeaderForCurrentPosition(position: Int): String? {
        val data = items()[position]
        return if (data is TextHeader) {
            "$position ${data.text}"
        } else {
            null
        }
    }
}