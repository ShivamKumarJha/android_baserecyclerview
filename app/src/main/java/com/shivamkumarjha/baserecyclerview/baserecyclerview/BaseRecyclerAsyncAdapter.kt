package com.shivamkumarjha.baserecyclerview.baserecyclerview

import android.util.SparseArray
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAsyncAdapter<T : BaseViewType> :
    RecyclerView.Adapter<DataBoundViewHolder<*>>() {

    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return ViewComparator.areItemsTheSame(oldItem, newItem)
        }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return ViewComparator.areContentsTheSame(oldItem, newItem)
        }
    })
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
        viewDataBinder.bindData(holder.binding, differ.currentList[position], position, itemCount)
        holder.binding.executePendingBindings()
    }

    override fun getItemViewType(position: Int): Int {
        if (0 > position || position >= differ.currentList.size) {
            throw IndexOutOfBoundsException("Invalid View type for position $position")
        }
        return differ.currentList[position].baseViewType
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun addViewBinder(viewDataBinder: ViewDataBinder<*, *>) {
        supportedViewBindersMap.put(viewDataBinder.viewType, viewDataBinder)
    }

    fun addViewBinders(viewDataBinders: List<ViewDataBinder<*, *>>) {
        viewDataBinders.forEach { viewDataBinder ->
            addViewBinder(viewDataBinder)
        }
    }

    fun items(): MutableList<T> = differ.currentList

    fun submitList(items: List<T>) {
        differ.submitList(items)
    }
}