package com.mikhnovskiy.spacex.data.network.models.launches

import com.google.gson.annotations.SerializedName

data class LaunchItemResponse(
	val id: String,
	val name: String? = null,
	val payloads: List<RocketItemResponse>? = null,
	@SerializedName("date_local")
	val dateLocal: String? = null,
	@SerializedName("date_unix")
	val dateUnix: Long? = null,
	@SerializedName("date_utc")
	val dateUtc: String? = null,
	val success: Boolean? = null,
	val links: LaunchLinksResponse? = null,
	val upcoming: Boolean? = null
)
