package com.shivamkumarjha.baserecyclerview.baserecyclerview

object ViewComparator {

    fun areItemsTheSame(oldItem: BaseViewType, newItem: BaseViewType): Boolean {
        return when {
            else -> oldItem.baseViewType == newItem.baseViewType
        }
    }

    fun areContentsTheSame(oldItem: BaseViewType, newItem: BaseViewType): Boolean {
        return when {
            else -> oldItem == newItem
        }
    }
}