package com.example.vehiclecompanion.garage.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vehiclecompanion.base.ui.data.UiState
import com.example.vehiclecompanion.garage.data.GarageIntent
import com.example.vehiclecompanion.garage.data.GarageUi
import com.example.vehiclecompanion.garage.data.VehicleUi
import com.example.vehiclecompanion.garage.repository.VehicleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GarageViewModel @Inject constructor(
    private val repository: VehicleRepository
) : ViewModel() {
    private val isSheetOpen = MutableStateFlow(value = false)
    private val vehicleForEdit = MutableStateFlow<VehicleUi?>(value = null)
    private val _uiState = MutableStateFlow<UiState<GarageUi>>(value = UiState.Loading)
    val uiState: StateFlow<UiState<GarageUi>> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            delay(timeMillis = 1000L)
            getAllVehicles()
        }
    }

    fun onSubmitIntent(intent: GarageIntent): Unit =
        when (intent) {
            is GarageIntent.DeleteVehicle -> deleteVehicle(id = intent.id)
            is GarageIntent.SaveVehicles -> {
                saveVehicles(vehicle = intent.vehicles)
                vehicleForEdit.value = null
                isSheetOpen.value = false
            }

            is GarageIntent.HideSheet -> {
                vehicleForEdit.value = null
                isSheetOpen.value = false
            }
            is GarageIntent.ShowSheet -> isSheetOpen.value = true
            is GarageIntent.EditVehicle -> {
                vehicleForEdit.value = intent.vehicle
                isSheetOpen.value = true
            }
        }

    private fun saveVehicles(vehicle: List<VehicleUi>) {
        viewModelScope.launch(context = Dispatchers.IO) {
            repository.saveVehicle(vehiclesUi = vehicle)
        }
    }

    private fun deleteVehicle(id: Long) {
        viewModelScope.launch(context = Dispatchers.IO) {
            repository.deleteVehicle(vehicleId = id)
        }
    }

    private fun getAllVehicles() {
        viewModelScope.launch(context = Dispatchers.IO) {
            repository.getAllVehicles()
                .combine(flow = isSheetOpen) { vehicles, isOpen ->
                    vehicles to isOpen
                }
                .combine(flow = vehicleForEdit) { (vehicles, isOpen), editVehicle ->
                    UiState.Success(
                        data = GarageUi(
                            vehicles = vehicles,
                            isSheetOpen = isOpen,
                            vehicleForEdit = editVehicle
                        )
                    )
                }
                .onStart { _uiState.value = UiState.Loading }
                .catch { e ->
                    _uiState.value = UiState.Error(message = e.message ?: "Unknown Error")
                }
                .collect { newState ->
                    _uiState.value = newState
                }
        }
    }
}