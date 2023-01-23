package com.mikhnovskiy.spacex.presentation.models.list_items

import com.mikhnovskiy.spacex.presentation.models.AdapterListItem

data class CompanyInfoModel(
    val name: String,
    val founderName: String,
    val foundationYear: Int,
    val employeesCount: Int,
    val launchSites : Int,
    val price: String
) : AdapterListItem {

    companion object {
        const val COMPANY_TYPE = 2
    }

    override fun getType() = COMPANY_TYPE

}