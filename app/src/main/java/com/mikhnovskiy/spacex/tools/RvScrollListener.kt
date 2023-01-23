package com.mikhnovskiy.spacex.tools

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RvScrollListener(
    private val callback: ((Int, ScrollDirection) -> Unit)
) : RecyclerView.OnScrollListener() {

    private var lastVisibleItem: Int = -1

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val itemPos = (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
        if (itemPos != lastVisibleItem) {
            val direction = recognizeDirection(dx, dy)
            callback.invoke(itemPos, direction)
            lastVisibleItem = itemPos
        }
    }

    private fun recognizeDirection(dx: Int, dy: Int) = when {
        dx < 0 -> ScrollDirection.LEFT
        dx > 0 -> ScrollDirection.RIGHT
        dy > 0 -> ScrollDirection.DOWN
        dy < 0 -> ScrollDirection.UP
        else -> ScrollDirection.NONE
    }

    enum class ScrollDirection { UP, DOWN, LEFT, RIGHT, NONE }
}

fun RecyclerView.onLastVisibleItemPos(callback: (Int, RvScrollListener.ScrollDirection) -> Unit) {
    val rvScrollListener = RvScrollListener(callback)
    this.addOnScrollListener(rvScrollListener)
}