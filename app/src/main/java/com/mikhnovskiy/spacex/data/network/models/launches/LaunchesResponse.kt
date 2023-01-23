package com.mikhnovskiy.spacex.data.network.models.launches

data class LaunchesResponse(
	val docs: List<LaunchItemResponse>,
	val limit: Int,
	val page: Int,
	val totalPages: Int,
	val nextPage: Int?,
)








