package com.shivamkumarjha.baserecyclerview.baserecyclerview.decorator

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecorator(
    private val leftMargin: Int = 0,
    private val topMargin: Int = 0,
    private val rightMargin: Int = 0,
    private val bottomMargin: Int = 0,
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
        if (position != RecyclerView.NO_POSITION && position < itemCount - 1) {
            outRect.left = leftMargin
            outRect.top = topMargin
            outRect.right = rightMargin
            outRect.bottom = bottomMargin
        }
    }
}
