package com.example.vehiclecompanion.garage.data

data class VehicleEntity(
    val name: String,
    val make: String,
    val model: String,
    val year: String,
    val vin: Int,
    val fuelType: FuelType = FuelType.UNKNOWN
)
