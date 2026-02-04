package com.example.vehiclecompanion.places.data

data class PlacesUi(
    val places: List<PlaceUi>,
    val selectedPlace: PlaceUi? = null,
    val isSheetOpen: Boolean = false,
    val favoriteIds: Set<Int>
)