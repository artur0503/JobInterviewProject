package com.mikhnovskiy.spacex.presentation.models

import android.os.Parcelable
import com.mikhnovskiy.spacex.SortTypes
import kotlinx.parcelize.Parcelize

@Parcelize
data class PredicatesModel(
    var sortType: String = SortTypes.ASC,
    var isSuccess: Boolean? = null,
    var yearStart: Int? = null,
    var yearEnd: Int? = null,
    var isUpcoming: Boolean? = null
) : Parcelable {

    override fun equals(other: Any?): Boolean {
        return if (other is PredicatesModel) {
            (this.isSuccess == other.isSuccess
                    && this.sortType == other.sortType
                    && this.isUpcoming == other.isUpcoming
                    && this.yearStart == other.yearStart
                    && this.yearEnd == other.yearEnd)
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        var result = sortType.hashCode()
        result = 31 * result + (isSuccess?.hashCode() ?: 0)
        result = 31 * result + (yearStart ?: 0)
        result = 31 * result + (yearEnd ?: 0)
        result = 31 * result + (isUpcoming?.hashCode() ?: 0)
        return result
    }
}