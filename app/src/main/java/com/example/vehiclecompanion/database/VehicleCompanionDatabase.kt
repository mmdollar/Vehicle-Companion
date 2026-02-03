package com.example.vehiclecompanion.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.vehiclecompanion.database.dao.VehicleDAO
import com.example.vehiclecompanion.database.entity.VehicleEntity

@Database(
    entities = [VehicleEntity::class],
    version = 2,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ]
)

abstract class VehicleCompanionDatabase : RoomDatabase() {

    abstract fun getVehicleDao(): VehicleDAO
}