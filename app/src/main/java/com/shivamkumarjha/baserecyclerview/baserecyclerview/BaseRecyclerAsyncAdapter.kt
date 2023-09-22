package com.shivamkumarjha.baserecyclerview.baserecyclerview

import android.util.SparseArray
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shivamkumarjha.baserecyclerview.baserecyclerview.util.isValidIndex

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

    private fun modifyList(position: Int? = null, modify: (ArrayList<T>) -> Unit) {
        val newList = ArrayList(items())
        if (position != null && !position.isValidIndex(newList)) return
        modify(newList)
        submitList(newList)
    }

    // Adds an item to the end of the list.
    fun addItem(item: T) {
        modifyList {
            it.add(item)
        }
    }

    // Inserts an item at a specified position in the list.
    fun addItem(position: Int, baseViewType: T) {
        modifyList(position) {
            it.add(position, baseViewType)
        }
    }

    // Adds a list of items to the end of the list.
    fun addItems(items: List<T>) {
        modifyList {
            it.addAll(items)
        }
    }

    // Inserts a list of items at a specified position in the list.
    fun addItems(position: Int, items: List<T>) {
        modifyList(position) {
            it.addAll(position, items)
        }
    }

    // Clears all items from the list.
    fun clearItems() {
        modifyList {
            it.clear()
        }
    }

    // Removes a specific item from the list.
    fun removeItem(item: T) {
        modifyList {
            it.remove(item)
        }
    }

    // Removes an item at a specified position from the list.
    fun removeIndex(position: Int) {
        modifyList(position) {
            it.removeAt(position)
        }
    }

    // Removes items from the specified start index to the end of the list.
    fun removeRange(startIndex: Int) {
        modifyList(startIndex) {
            val size = it.size
            it.subList(startIndex, size).clear()
        }
    }

    // Removes items within a specified range of indices.
    fun removeRange(startIndex: Int, endIndex: Int) {
        modifyList {
            if (!startIndex.isValidIndex(it)) return@modifyList
            if (!endIndex.isValidIndex(it)) return@modifyList
            if (endIndex >= it.size) {
                it.subList(startIndex, it.size).clear()
                it.size - startIndex
            } else {
                it.subList(startIndex, endIndex + 1).clear()
                endIndex - startIndex + 1
            }
        }
    }

    // Removes the last item from the list.
    fun removeLast() {
        modifyList {
            removeIndex(it.lastIndex)
        }
    }

    // Removes items with a specific baseViewType.
    fun removeView(@ViewType viewType: Int) {
        modifyList {
            it.removeAll { item ->
                item.baseViewType == viewType
            }
        }
    }

    // Updates an item at a specified position in the list.
    fun updateItem(position: Int, item: T) {
        modifyList(position) {
            it[position] = item
        }
    }

}