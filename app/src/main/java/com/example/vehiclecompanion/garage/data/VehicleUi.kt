package com.example.vehiclecompanion.garage.data

data class VehicleUi(
    val name: String,
    val make: String,
    val model: String,
    val year: Int,
    val vin: String,
    val photo: String,
    val fuelType: FuelType = FuelType.UNKNOWN
)
