package com.shivamkumarjha.baserecyclerview.baserecyclerview

import android.util.SparseArray
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter

abstract class BasePagingAdapter<T : BaseViewType> :
    PagingDataAdapter<T, DataBoundViewHolder<*>>(DiffUtilItemCallback()) {

    private val supportedViewBindersMap: SparseArray<ViewDataBinder<*, *>> = SparseArray(1)

    init {
        stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBoundViewHolder<*> {
        val viewDataBinder = supportedViewBindersMap[viewType]
            ?: throw IllegalArgumentException("No view binder found for viewType: $viewType")
        return DataBoundViewHolder(viewDataBinder.createBinder(parent))
    }

    override fun onBindViewHolder(holder: DataBoundViewHolder<*>, position: Int) {
        val viewDataBinder = supportedViewBindersMap[getItemViewType(position)]
            ?: throw IllegalArgumentException("Please add the supported view binder to view binders list in adapter")
        viewDataBinder.bindData(holder.binding, getItem(position)!!, position, itemCount)
        holder.binding.executePendingBindings()
    }

    override fun getItemViewType(position: Int): Int {
        if (position >= 0 && position < snapshot().size) {
            val item: T = getItem(position)!!
            return item.baseViewType
        }
        return 0
    }

    fun addViewBinder(viewDataBinder: ViewDataBinder<*, *>) {
        supportedViewBindersMap.put(viewDataBinder.viewType, viewDataBinder)
    }

    fun addViewBinders(viewDataBinders: List<ViewDataBinder<*, *>>) {
        viewDataBinders.forEach { viewDataBinder ->
            addViewBinder(viewDataBinder)
        }
    }
}