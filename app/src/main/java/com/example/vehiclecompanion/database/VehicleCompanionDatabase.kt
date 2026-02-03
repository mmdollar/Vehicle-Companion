package com.example.vehiclecompanion.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.vehiclecompanion.database.dao.VehicleDAO
import com.example.vehiclecompanion.database.entity.VehicleEntity

@Database(
    entities = [VehicleEntity::class],
    version = 3,
    autoMigrations = [
        AutoMigration(from = 2, to = 3)
    ]
)

abstract class VehicleCompanionDatabase : RoomDatabase() {

    abstract fun getVehicleDao(): VehicleDAO
}