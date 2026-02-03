package com.example.vehiclecompanion.places.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vehiclecompanion.base.ui.data.UiState
import com.example.vehiclecompanion.places.data.PlaceUi
import com.example.vehiclecompanion.places.repository.PlacesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class PlacesViewModel @Inject constructor(
    private val repository: PlacesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<PlaceUi>>>(value = UiState.Loading)
    val uiState: StateFlow<UiState<List<PlaceUi>>> = _uiState.asStateFlow()

    init {
        observePlaces()
    }

    private fun observePlaces() {
        repository.getPlaces()
            .onStart { _uiState.value = UiState.Loading }
            .onEach { places ->
                _uiState.value = UiState.Success(data = places)
            }
            .catch { throwable ->
                _uiState.value = UiState.Error(message = throwable.message.orEmpty())
            }
            .launchIn(viewModelScope)
    }

}