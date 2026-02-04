package com.example.vehiclecompanion.places.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vehiclecompanion.base.ui.data.UiState
import com.example.vehiclecompanion.places.data.PlaceUi
import com.example.vehiclecompanion.places.data.PlacesIntent
import com.example.vehiclecompanion.places.data.PlacesUi
import com.example.vehiclecompanion.places.repository.PlacesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlacesViewModel @Inject constructor(
    private val repository: PlacesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<PlacesUi>>(value = UiState.Loading)
    val uiState: StateFlow<UiState<PlacesUi>> = _uiState.asStateFlow()

    init {
        observePlacesAndFavorites()
    }

    fun onSubmitIntent(intent: PlacesIntent): Unit =
        when (intent) {
            is PlacesIntent.ToggleFavorite -> {
                toggleFavorite(place = intent.place, isFavorite = intent.isFavorite)
            }

            is PlacesIntent.SelectPlace -> {
                updateUi { copy(selectedPlace = intent.place, isSheetOpen = true) }
            }

            PlacesIntent.CloseSheet -> {
                updateUi { copy(selectedPlace = null, isSheetOpen = false) }
            }
        }

    private fun observePlacesAndFavorites() {
        combine(
            flow = repository.getPlaces(),
            flow2 = repository.getFavorites()
        ) { places, favorites ->
            PlacesUi(
                places = places,
                favoriteIds = favorites.map { it.id }.toSet()
            )
        }
            .onStart { _uiState.value = UiState.Loading }
            .onEach { placesUi ->
                _uiState.value = UiState.Success(data = placesUi)
            }
            .catch { throwable ->
                _uiState.value = UiState.Error(message = throwable.message.orEmpty())
            }
            .launchIn(viewModelScope)
    }

    private fun toggleFavorite(place: PlaceUi, isFavorite: Boolean) {
        viewModelScope.launch {
            repository.toggleFavorite(place, isFavorite)
        }
    }

    private inline fun updateUi(transform: PlacesUi.() -> PlacesUi) {
        val currentState = _uiState.value
        if (currentState is UiState.Success) {
            _uiState.value = UiState.Success(data = currentState.data.transform())
        }
    }
}