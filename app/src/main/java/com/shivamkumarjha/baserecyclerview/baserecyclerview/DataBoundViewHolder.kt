package com.shivamkumarjha.baserecyclerview.baserecyclerview

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * A generic ViewHolder that works with a [ViewDataBinding].
 *
 * @param <V> The type of the ViewDataBinding.
</T> */
class DataBoundViewHolder<V : ViewDataBinding>(val binding: V) :
    RecyclerView.ViewHolder(binding.root)