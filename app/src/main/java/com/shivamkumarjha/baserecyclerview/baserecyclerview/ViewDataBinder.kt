package com.shivamkumarjha.baserecyclerview.baserecyclerview

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding

abstract class ViewDataBinder<out V : ViewDataBinding, out T : BaseViewType> {

    @get:ViewType
    abstract val viewType: Int

    abstract fun createBinder(parent: ViewGroup): V

    abstract fun bindData(
        binding: @UnsafeVariance V,
        data: @UnsafeVariance T,
        position: Int,
        itemCount: Int,
    )
}