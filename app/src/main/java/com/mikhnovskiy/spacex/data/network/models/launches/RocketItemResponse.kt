package com.mikhnovskiy.spacex.data.network.models.launches

data class RocketItemResponse(
	val name: String? = null,
	val id: String? = null,
	val type: String? = null
) {
	override fun toString(): String {
		return "$name/$type"
	}
}