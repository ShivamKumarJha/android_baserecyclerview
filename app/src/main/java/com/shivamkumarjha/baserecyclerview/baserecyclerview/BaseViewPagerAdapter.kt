package com.shivamkumarjha.baserecyclerview.baserecyclerview

import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

abstract class BaseViewPagerAdapter<T : BaseViewType>(
    private val items: List<T>
) : PagerAdapter() {

    private val supportedViewBindersMap: SparseArray<ViewDataBinder<*, *>> = SparseArray(1)

    override fun getCount(): Int = items.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val viewType = items[position].baseViewType
        val viewDataBinder = supportedViewBindersMap[viewType]
            ?: throw IllegalArgumentException("No view binder found for viewType: $viewType")
        val binding = viewDataBinder.createBinder(container)
        container.addView(binding.root)
        viewDataBinder.bindData(binding, items[position], position, count)
        binding.executePendingBindings()
        return binding.root
    }

    override fun isViewFromObject(view: View, objectView: Any): Boolean {
        return view === objectView
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