package com.shivamkumarjha.baserecyclerview.baserecyclerview

import android.annotation.SuppressLint
import android.util.SparseArray
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shivamkumarjha.baserecyclerview.baserecyclerview.util.isValidIndex

abstract class BaseRecyclerAdapter<T : BaseViewType> :
    RecyclerView.Adapter<DataBoundViewHolder<*>>() {

    private var dataList: ArrayList<T> = arrayListOf()
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
        viewDataBinder.bindData(holder.binding, dataList[position], position, itemCount)
        holder.binding.executePendingBindings()
    }

    override fun getItemViewType(position: Int): Int {
        if (!position.isValidIndex(dataList)) {
            throw IndexOutOfBoundsException("Invalid View type for position $position")
        }
        return dataList[position].baseViewType
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun addViewBinder(viewDataBinder: ViewDataBinder<*, *>) {
        supportedViewBindersMap.put(viewDataBinder.viewType, viewDataBinder)
    }

    fun addViewBinders(viewDataBinders: List<ViewDataBinder<*, *>>) {
        viewDataBinders.forEach { viewDataBinder ->
            addViewBinder(viewDataBinder)
        }
    }

    fun items(): List<T> {
        return dataList
    }

    fun setItems(items: ArrayList<T>) {
        dataList = items
    }

    @SuppressLint("NotifyDataSetChanged")
    fun notifyAllItems() {
        notifyDataSetChanged()
    }

    fun add(position: Int, baseViewType: T) {
        if (!position.isValidIndex(dataList)) return
        dataList.add(position, baseViewType)
        notifyItemInserted(position)
    }

    fun add(baseViewType: T) {
        dataList.add(baseViewType)
        notifyItemChanged(dataList.lastIndex)
    }

    fun addAll(baseViewTypes: List<T>) {
        dataList.addAll(baseViewTypes)
        notifyItemRangeInserted(dataList.size, baseViewTypes.size)
    }

    fun update(position: Int, baseViewType: T) {
        if (!position.isValidIndex(dataList)) return
        dataList[position] = baseViewType
        notifyItemChanged(position)
    }

    fun replace(items: List<T>) {
        val diffUtilCallback = DiffUtilCallback(dataList, items)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
        clear()
        addAllWithoutNotify(items)
        diffResult.dispatchUpdatesTo(this)
    }

    fun removeIndex(index: Int) {
        dataList.removeAt(index)
        notifyItemRemoved(index)
    }

    fun removeRange(startIndex: Int) {
        val size = dataList.size
        dataList.subList(startIndex, size).clear()
        notifyItemRangeRemoved(startIndex, size - startIndex)
    }

    fun removeRange(startIndex: Int, endIndex: Int) {
        val count: Int = if (endIndex >= dataList.size) {
            dataList.subList(startIndex, dataList.size).clear()
            dataList.size - startIndex
        } else {
            dataList.subList(startIndex, endIndex + 1).clear()
            endIndex - startIndex + 1
        }
        notifyItemRangeRemoved(startIndex, count)
    }

    fun removeLast() {
        val lastIndex = dataList.size - 1
        removeIndex(lastIndex)
    }

    fun removeView(@ViewType viewType: Int) {
        replace(dataList.filter { it.baseViewType != viewType })
    }

    fun clear() {
        dataList.clear()
    }

    fun clearAndNotify() {
        dataList.clear()
        notifyAllItems()
    }

    fun addAll(position: Int, baseViewTypes: List<T>) {
        if (!position.isValidIndex(dataList)) return
        dataList.addAll(position, baseViewTypes)
        notifyItemRangeInserted(position, baseViewTypes.size)
    }

    fun addWithNotifyItemInserted(baseViewType: T) {
        dataList.add(baseViewType)
        notifyItemInserted(dataList.size - 1)
    }

    fun addAllWithoutNotify(baseViewTypes: List<T>) {
        dataList.addAll(baseViewTypes)
    }

    fun addWithNotifyItemRangeChanged(position: Int, baseViewType: T) {
        if (!position.isValidIndex(dataList)) return
        dataList.add(position, baseViewType)
        notifyItemRangeChanged(position, dataList.size)
    }
}
