package com.mikhnovskiy.spacex.presentation.models.list_items

import com.mikhnovskiy.spacex.presentation.models.AdapterListItem

data class HeaderModel(val title: String) : AdapterListItem {
    companion object {
        const val HEADER_TYPE = 1
    }

    override fun getType() = HEADER_TYPE
}