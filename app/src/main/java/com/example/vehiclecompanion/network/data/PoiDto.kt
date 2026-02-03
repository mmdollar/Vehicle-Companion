package com.example.vehiclecompanion.network.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PoiDto(
    val id: Int,
    val name: String,
    val url: String?,
    @param:Json(name = "primary_category_display_name")
    val category: String?,
    val rating: Double?,
    @param:Json(name = "v_320x320_url")
    val imageUrl: String?,
    val loc: List<Double>
)