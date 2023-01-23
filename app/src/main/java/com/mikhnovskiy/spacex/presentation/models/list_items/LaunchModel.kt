package com.mikhnovskiy.spacex.presentation.models.list_items

import android.os.Parcelable
import com.mikhnovskiy.spacex.presentation.models.AdapterListItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class LaunchModel(
    val missionName: String,
    val missionDateTime: String,
    val rockets: String,
    val isSuccess: Boolean?,
    val imgUrl: String?,
    val daysDifference: Int,
    val upcoming: Boolean?,
    val wikipediaUrl: String?,
    val videoUrl: String?
) : Parcelable, AdapterListItem {
    companion object {
        const val LAUNCH_TYPE = 3
    }

    override fun getType() = LAUNCH_TYPE
}