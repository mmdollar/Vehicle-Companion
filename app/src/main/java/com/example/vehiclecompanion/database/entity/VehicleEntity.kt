package com.example.vehiclecompanion.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.vehiclecompanion.garage.data.FuelType

@Entity(tableName = "vehicles")
data class VehicleEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var name: String,
    var make: String,
    var model: String,
    var year: Int,
    var vin: String,
    var photo: String,
    var fuelType: FuelType = FuelType.UNKNOWN
)