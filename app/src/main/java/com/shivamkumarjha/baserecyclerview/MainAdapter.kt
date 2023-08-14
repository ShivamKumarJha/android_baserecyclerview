package com.shivamkumarjha.baserecyclerview

import com.shivamkumarjha.baserecyclerview.baserecyclerview.BaseRecyclerAsyncAdapter
import com.shivamkumarjha.baserecyclerview.baserecyclerview.BaseViewType
import com.shivamkumarjha.baserecyclerview.baserecyclerview.databinder.TextHeaderDataBinder

class MainAdapter(
    onTextClick: (String) -> Unit
) : BaseRecyclerAsyncAdapter<BaseViewType>() {

    init {
        addViewBinder(TextHeaderDataBinder(onTextClick))
    }
}