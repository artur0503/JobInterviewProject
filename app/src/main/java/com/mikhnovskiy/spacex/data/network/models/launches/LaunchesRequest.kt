package com.mikhnovskiy.spacex.data.network.models.launches

import android.util.Range
import com.mikhnovskiy.spacex.data.network.ApiConst.RequestKeys.DATE_LOCAL
import com.mikhnovskiy.spacex.data.network.ApiConst.RequestKeys.DATE_UNIX
import com.mikhnovskiy.spacex.data.network.ApiConst.RequestKeys.DATE_UTC
import com.mikhnovskiy.spacex.data.network.ApiConst.RequestKeys.LIMIT
import com.mikhnovskiy.spacex.data.network.ApiConst.RequestKeys.LINKS
import com.mikhnovskiy.spacex.data.network.ApiConst.RequestKeys.NAME
import com.mikhnovskiy.spacex.data.network.ApiConst.RequestKeys.NET
import com.mikhnovskiy.spacex.data.network.ApiConst.RequestKeys.PAGE
import com.mikhnovskiy.spacex.data.network.ApiConst.RequestKeys.PAYLOADS
import com.mikhnovskiy.spacex.data.network.ApiConst.RequestKeys.SUCCESS
import com.mikhnovskiy.spacex.data.network.ApiConst.RequestKeys.TDB
import com.mikhnovskiy.spacex.data.network.ApiConst.RequestKeys.TYPE
import com.mikhnovskiy.spacex.data.network.ApiConst.RequestKeys.UPCOMING
import com.mikhnovskiy.spacex.data.network.ApiConst.RequestParams.FROM_PARAM
import com.mikhnovskiy.spacex.data.network.ApiConst.RequestParams.OPTIONS_PARAM
import com.mikhnovskiy.spacex.data.network.ApiConst.RequestParams.PATH_PARAM
import com.mikhnovskiy.spacex.data.network.ApiConst.RequestParams.POPULATE_PARAM
import com.mikhnovskiy.spacex.data.network.ApiConst.RequestParams.QUERY_PARAM
import com.mikhnovskiy.spacex.data.network.ApiConst.RequestParams.SELECT_PARAM
import com.mikhnovskiy.spacex.data.network.ApiConst.RequestParams.SORT_PARAM
import com.mikhnovskiy.spacex.data.network.ApiConst.RequestParams.TO_PARAM

class LaunchesRequest(
    private val page: Int,
    private val limit: Int,
    private val sortMap: Map<String, Any>? = null,
    private val filterMap: Map<String, Any>? = null
) {

    val requestBody: LinkedHashMap<String, Any?>
        get() {
            val optionsMap = mutableMapOf(
                SELECT_PARAM to launchFieldsList,
                POPULATE_PARAM to mapOf(
                    PATH_PARAM to PAYLOADS,
                    SELECT_PARAM to payloadsFieldsList
                ),
                PAGE to page,
                LIMIT to limit,
            )
            if (sortMap != null) {
                optionsMap[SORT_PARAM] = sortMap
            }
            return linkedMapOf(
                QUERY_PARAM to filterMap,
                OPTIONS_PARAM to optionsMap,
            )
        }

    companion object {
        private val payloadsFieldsList = listOf(NAME, TYPE)
        private val launchFieldsList = listOf(
            LINKS,
            PAYLOADS,
            SUCCESS,
            NAME,
            DATE_UTC,
            DATE_UNIX,
            DATE_LOCAL,
            UPCOMING,
            TDB,
            NET
        )

        fun getSortMap(sortType: String) : Map<String, Any> {
            return mapOf(DATE_UNIX to sortType)
        }

        fun getFilterMap(isSuccess: Boolean? = null, isUpcoming: Boolean? = null, dateRange: Range<Long>? = null) : Map<String, Any> {
            val resultMap = mutableMapOf<String, Any>()
            if (isSuccess != null) {
                resultMap[SUCCESS] = isSuccess
            }
            if (isUpcoming != null) {
                resultMap[UPCOMING] = isUpcoming
            }
            if (dateRange != null) {
                resultMap[DATE_UNIX] = getDatesRangeMap(dateRange.lower, dateRange.upper)
            }
            return resultMap
        }

        private fun getDatesRangeMap(dateUnix1: Long, dateUnix2: Long): Map<String, Any> {
            return mapOf<String, Any>(
                FROM_PARAM to dateUnix1,
                TO_PARAM to dateUnix2
            )
        }

    }
}