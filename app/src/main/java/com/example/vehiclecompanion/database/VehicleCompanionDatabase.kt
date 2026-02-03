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
    version = 5,
    autoMigrations = [
        AutoMigration(from = 4, to = 5)
    ]
)

abstract class VehicleCompanionDatabase : RoomDatabase() {

    abstract fun getVehicleDao(): VehicleDAO

    abstract fun getFavoritesDao(): FavoritesDao
}