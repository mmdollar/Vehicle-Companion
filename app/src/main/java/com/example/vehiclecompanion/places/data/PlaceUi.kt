package com.example.vehiclecompanion.places.data

data class PlaceUi(
    val id: Int,
    val name: String,
    val category: String,
    val rating: Double,
    val imageUrl: String?,
    val longitude: Double,
    val latitude: Double,
    val url: String?
)