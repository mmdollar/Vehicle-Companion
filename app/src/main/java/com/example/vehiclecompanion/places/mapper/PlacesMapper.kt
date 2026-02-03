package com.example.vehiclecompanion.places.mapper

import com.example.vehiclecompanion.network.data.PoiDto
import com.example.vehiclecompanion.places.data.PlaceUi
import javax.inject.Inject

class PlacesMapper @Inject constructor() {

    fun mapDtoToUiModel(placeDto: PoiDto): PlaceUi = PlaceUi(
        id = placeDto.id,
        name = placeDto.name,
        category = placeDto.category ?: "Unknown",
        rating = placeDto.rating ?: 0.0,
        imageUrl = placeDto.imageUrl,
        longitude = placeDto.loc.getOrNull(0) ?: 0.0,
        latitude = placeDto.loc.getOrNull(1) ?: 0.0,
        url = placeDto.url
    )
}