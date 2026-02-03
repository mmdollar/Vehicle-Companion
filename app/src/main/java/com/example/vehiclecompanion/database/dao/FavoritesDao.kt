package com.example.vehiclecompanion.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vehiclecompanion.database.entity.FavoritePlaceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {

    @Query(value = "SELECT * FROM favorite_places")
    fun getAll(): Flow<List<FavoritePlaceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(place: FavoritePlaceEntity)

    @Delete
    suspend fun delete(place: FavoritePlaceEntity)
}