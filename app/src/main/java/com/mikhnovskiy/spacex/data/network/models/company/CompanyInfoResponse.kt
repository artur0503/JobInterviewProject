package com.mikhnovskiy.spacex.data.network.models.company

import com.google.gson.annotations.SerializedName

data class CompanyInfoResponse(
    val id: String? = null,
    val name: String? = null,
    val founder: String? = null,
    val founded: Int? = null,
    val employees: Int? = null,
    val valuation: Long? = null,
    @SerializedName("launch_sites")
    val launchSites: Int
)


