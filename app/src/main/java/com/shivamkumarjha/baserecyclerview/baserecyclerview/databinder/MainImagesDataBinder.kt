package com.shivamkumarjha.baserecyclerview.baserecyclerview.databinder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.shivamkumarjha.baserecyclerview.baserecyclerview.ITEM_IMAGES
import com.shivamkumarjha.baserecyclerview.baserecyclerview.ViewDataBinder
import com.shivamkumarjha.baserecyclerview.baserecyclerview.decorator.CirclePagerIndicatorDecoration
import com.shivamkumarjha.baserecyclerview.databinding.ItemHorizontalListBinding

class MainImagesDataBinder(
    private val pool: RecyclerView.RecycledViewPool,
) : ViewDataBinder<ItemHorizontalListBinding, MainImages>() {

    // Store the position of each horizontal recycler view
    private val stateMap = mutableMapOf<Int, Int>()

    override val viewType: Int
        get() = ITEM_IMAGES

    override fun createBinder(parent: ViewGroup): ItemHorizontalListBinding {
        val binding = ItemHorizontalListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvHor)
        binding.rvHor.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            setRecycledViewPool(pool)
            addItemDecoration(CirclePagerIndicatorDecoration())

            onFlingListener = object : RecyclerView.OnFlingListener() {
                override fun onFling(velocityX: Int, velocityY: Int): Boolean {
                    val centerView = snapHelper.findSnapView(layoutManager)
                    val itemPosition =
                        centerView?.let { (layoutManager as? LinearLayoutManager)?.getPosition(it) }
                            ?: RecyclerView.NO_POSITION
                    val targetView =
                        (layoutManager as? LinearLayoutManager)?.findViewByPosition(itemPosition + (if (velocityX > 0) 1 else -1))
                    targetView?.let {
                        val targetPosition = (layoutManager as LinearLayoutManager).getPosition(it)
                        val smoothScroller = object : LinearSmoothScroller(binding.rvHor.context) {
                            override fun getHorizontalSnapPreference(): Int {
                                return SNAP_TO_START // or SNAP_TO_END for different behavior
                            }
                        }
                        smoothScroller.targetPosition = targetPosition
                        (layoutManager as? LinearLayoutManager)?.startSmoothScroll(smoothScroller)

                        // Save state
                        val rvPos = binding.itemPosition ?: return true
                        stateMap[rvPos] = targetPosition
                    }
                    return true
                }
            }
        }
        return binding
    }

    override fun bindData(
        binding: ItemHorizontalListBinding,
        data: MainImages,
        position: Int,
        itemCount: Int
    ) {
        binding.data = data
        binding.itemPosition = position

        val mainImageAdapter = MainImageAdapter()
        mainImageAdapter.submitList(data.images)
        binding.rvHor.apply {
            adapter = mainImageAdapter

            // Restore state if available
            stateMap[position]?.let {
                scrollToPosition(it)
            }
        }
    }
}