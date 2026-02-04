package com.example.vehiclecompanion.garage.repository

import com.example.vehiclecompanion.database.dao.VehicleDAO
import com.example.vehiclecompanion.garage.data.VehicleUi
import com.example.vehiclecompanion.garage.mapper.VehicleMapper
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

    suspend fun deleteVehicle(vehicleId: Long): Unit =
        database.deleteVehicleById(vehicleId = vehicleId)
}