package com.example.vehiclecompanion.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.vehiclecompanion.database.dao.FavoritesDao
import com.example.vehiclecompanion.database.dao.VehicleDAO
import com.example.vehiclecompanion.database.entity.FavoritePlaceEntity
import com.example.vehiclecompanion.database.entity.VehicleEntity

@Database(
    entities = [VehicleEntity::class, FavoritePlaceEntity::class],
    version = 4,
    autoMigrations = [
        AutoMigration(from = 3, to = 4)
    ]
)

abstract class VehicleCompanionDatabase : RoomDatabase() {

    abstract fun getVehicleDao(): VehicleDAO

    abstract fun getFavoritesDao(): FavoritesDao
}