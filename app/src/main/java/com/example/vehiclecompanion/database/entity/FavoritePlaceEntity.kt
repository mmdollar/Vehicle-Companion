package com.example.vehiclecompanion.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_places")
data class FavoritePlaceEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val imageUrl: String?
)