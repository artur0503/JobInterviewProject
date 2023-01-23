package com.mikhnovskiy.spacex.data.network.models.launches

data class LaunchLinksResponse(
	val patch: ImagesItemResponse? = null,
	val webcast: String? = null,
	val wikipedia: String? = null,
	val youtubeId: String? = null,
	val presskit: String? = null,
	val article: String? = null
)

data class ImagesItemResponse(
	val small: String? = null,
	val large: String? = null
)