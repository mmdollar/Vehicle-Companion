package com.example.vehiclecompanion.garage.repository

import com.example.vehiclecompanion.database.dao.VehicleDAO
import com.example.vehiclecompanion.database.entity.VehicleEntity
import com.example.vehiclecompanion.garage.data.VehicleUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class VehicleRepository @Inject constructor(
    private val database: VehicleDAO,
    private val vehicleMapper: VehicleMapper
) {

    fun getAllVehicles(): Flow<List<VehicleUi>> = database.getAllVehicles()
        .map { entities ->
            entities.map { vehicleMapper.mapEntityToUi(vehicleEntity = it) }
        }

    suspend fun saveVehicle(vehiclesUi: List<VehicleUi>) {
        val entities = vehicleMapper.mapUiToEntity(vehiclesUi = vehiclesUi)
        database.insertVehicles(vehicles = entities)
    }
}

class VehicleMapper @Inject constructor() {

    fun mapEntityToUi(vehicleEntity: VehicleEntity): VehicleUi = VehicleUi(
        name = vehicleEntity.name,
        make = vehicleEntity.make,
        model = vehicleEntity.model,
        year = vehicleEntity.year,
        vin = vehicleEntity.vin,
        photo = vehicleEntity.photo
    )

    fun mapUiToEntity(vehiclesUi: List<VehicleUi>): List<VehicleEntity> =
        vehiclesUi.map { vehicle ->
            VehicleEntity(
                name = vehicle.name,
                make = vehicle.make,
                model = vehicle.model,
                year = vehicle.year,
                vin = vehicle.vin,
                photo = vehicle.photo
            )
        }
}