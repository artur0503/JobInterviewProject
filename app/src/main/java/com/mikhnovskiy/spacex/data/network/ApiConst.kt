package com.mikhnovskiy.spacex.data.network

object ApiConst {

    object SortTypes {
        const val DESC = "desc"
        const val ASC = "asc"
    }

    object RequestParams {
        const val QUERY_PARAM = "query"
        const val OPTIONS_PARAM = "options"
        const val FROM_PARAM = "${'$'}gte"
        const val TO_PARAM = "${'$'}lte"
        const val SELECT_PARAM = "select"
        const val POPULATE_PARAM = "populate"
        const val PATH_PARAM = "path"
        const val SORT_PARAM = "sort"
    }

    object RequestKeys {
        const val PAGE = "page"
        const val LIMIT = "limit"
        const val SUCCESS = "success"
        const val LINKS = "links"
        const val PAYLOADS = "payloads"
        const val NAME = "name"
        const val DATE_UTC = "date_utc"
        const val DATE_UNIX = "date_unix"
        const val DATE_LOCAL = "date_local"
        const val UPCOMING = "upcoming"
        const val NET = "net"
        const val TDB = "tbd"
        const val TYPE = "type"
    }
}