package com.example.vehiclecompanion.network.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PoiResponseDto(
    val pois: List<PoiDto>
)