package com.mikhnovskiy.spacex.core.adapters

import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<BaseType> : RecyclerView.Adapter<BaseViewHolder<out BaseType>>() {
    abstract val baseTypeList : List<BaseType>

    @Throws(RuntimeException::class)
    inline fun <reified T : BaseViewHolder<*>> castViewHolder(holder: BaseViewHolder<out BaseType>): T {
        if (holder is T) {
            return holder
        } else {
            throw RuntimeException("Wrong ViewHolder type!!!")
        }
    }

    override fun getItemCount() = baseTypeList.size
}