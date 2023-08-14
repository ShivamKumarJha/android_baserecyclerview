package com.shivamkumarjha.baserecyclerview.baserecyclerview

import androidx.recyclerview.widget.DiffUtil

class DiffUtilItemCallback<T : BaseViewType> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return ViewComparator.areItemsTheSame(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return ViewComparator.areContentsTheSame(oldItem, newItem)
    }
}