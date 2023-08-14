package com.shivamkumarjha.baserecyclerview.baserecyclerview.databinder

import com.shivamkumarjha.baserecyclerview.baserecyclerview.BaseRecyclerAsyncAdapter
import com.shivamkumarjha.baserecyclerview.baserecyclerview.BaseViewType

class MainImageAdapter : BaseRecyclerAsyncAdapter<BaseViewType>() {

    init {
        addViewBinder(MainImageDataBinder())
    }
}