package com.mikhnovskiy.spacex.tools

import android.os.SystemClock
import android.view.View

abstract class SingleClickListener : View.OnClickListener {
    companion object {
        const val CLICK_INTERVAL_MS = 500
    }

    private var lastNotifyTime = 0L

    override fun onClick(v: View?) {
        val currentClickTime = SystemClock.uptimeMillis()
        val elapsedTime = currentClickTime - lastNotifyTime

        if (elapsedTime <= CLICK_INTERVAL_MS) {
            return
        } else {
            lastNotifyTime = currentClickTime
            onSingleClick(v)
        }
    }

    abstract fun onSingleClick(v: View?)
}

fun View.onSingleClick(onClick: () -> Unit) {
    setOnClickListener(object : SingleClickListener() {
        override fun onSingleClick(v: View?) {
            onClick()
        }
    })
}
