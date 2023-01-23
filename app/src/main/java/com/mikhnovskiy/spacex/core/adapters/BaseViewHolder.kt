package com.mikhnovskiy.spacex.core.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

sealed class BaseViewHolder<BaseType>(view : View) : RecyclerView.ViewHolder(view) {
    abstract class SimpleViewHolder<BaseType>(view: View) : BaseViewHolder<BaseType>(view) {
        abstract fun bind(data: BaseType, pos: Int = 0)
    }
    abstract class MutableViewHolder<Binder : ViewBinding, BaseType>(mainBinder: Binder) : BaseViewHolder<BaseType>(mainBinder.root) {
        abstract fun bind(data: BaseType, pos: Int = 0)
    }
    abstract class ImmutableViewHolder<Binder : ViewBinding>(binder: Binder) : BaseViewHolder<Nothing>(binder.root)
}


