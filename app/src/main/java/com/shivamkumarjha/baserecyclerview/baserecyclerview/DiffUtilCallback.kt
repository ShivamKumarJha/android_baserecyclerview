package com.shivamkumarjha.baserecyclerview.baserecyclerview

import androidx.recyclerview.widget.DiffUtil

class DiffUtilCallback(
    private val oldList: List<BaseViewType>,
    private val newList: List<BaseViewType>,
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return ViewComparator.areItemsTheSame(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return ViewComparator.areContentsTheSame(oldItem, newItem)
    }
}