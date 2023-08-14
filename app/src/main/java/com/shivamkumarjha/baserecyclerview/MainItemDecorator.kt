package com.shivamkumarjha.baserecyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MainItemDecorator(
    private val onMargin: (Int, Rect) -> Unit,
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount

        // Apply margins only to items
        if (position != RecyclerView.NO_POSITION && position <= itemCount - 1) {
            onMargin(position, outRect)
        }
    }
}
