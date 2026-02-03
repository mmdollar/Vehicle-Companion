package com.example.vehiclecompanion.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_places")
data class FavoritePlaceEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val imageUrl: String?,
    val category: String,
    val rating: Double,
    val longitude: Double,
    val latitude: Double,
    val url: String?
)