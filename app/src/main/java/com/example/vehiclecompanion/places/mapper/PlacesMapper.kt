package com.example.vehiclecompanion.places.mapper

import com.example.vehiclecompanion.database.entity.FavoritePlaceEntity
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
        longitude = placeDto.loc.getOrNull(index = 0) ?: 0.0,
        latitude = placeDto.loc.getOrNull(index = 1) ?: 0.0,
        url = placeDto.url
    )

    fun mapUiToEntity(placeUi: PlaceUi): FavoritePlaceEntity = FavoritePlaceEntity(
        id = placeUi.id,
        name = placeUi.name,
        imageUrl = placeUi.imageUrl,
        category = placeUi.category,
        rating = placeUi.rating,
        longitude = placeUi.longitude,
        latitude = placeUi.latitude,
        url = placeUi.url
    )
}