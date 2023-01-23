package com.mikhnovskiy.spacex.tools

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class DefaultItemDecorator(
    private val topSpace: Int = 0,
    private val bottomSpace: Int = 0,
    private val leftSpace: Int = 0,
    private val rightSpace: Int = 0,
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = topSpace
        outRect.bottom = bottomSpace
        outRect.left = leftSpace
        outRect.right = rightSpace
    }

}