package com.example.vehiclecompanion.garage.data

sealed interface GarageIntent {
    data object ShowSheet : GarageIntent
    data object HideSheet : GarageIntent
    data class DeleteVehicle(val id: Long) : GarageIntent
    data class EditVehicle(val vehicle: VehicleUi) : GarageIntent
    data class SaveVehicles(val vehicles: List<VehicleUi>) : GarageIntent
}