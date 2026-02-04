package com.example.vehiclecompanion.places.data


sealed interface PlacesIntent {
    data object CloseSheet : PlacesIntent
    data class ToggleFavorite(val place: PlaceUi, val isFavorite: Boolean) : PlacesIntent
    data class SelectPlace(val place: PlaceUi) : PlacesIntent
}