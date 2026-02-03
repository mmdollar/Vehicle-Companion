package com.example.vehiclecompanion.garage.data

data class GarageUi(
    val vehicles: List<VehicleUi>,
    val isSheetOpen: Boolean = false,
    val vehicleForEdit: VehicleUi? = null
)