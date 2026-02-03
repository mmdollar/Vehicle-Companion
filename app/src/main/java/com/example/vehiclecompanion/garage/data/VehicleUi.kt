package com.example.vehiclecompanion.garage.data

data class VehicleUi(
    val id: Long = 0,
    val name: String,
    val make: String,
    val model: String,
    val year: Int,
    val vin: String,
    val photoUri: String,
    val fuelType: FuelType = FuelType.UNKNOWN
)