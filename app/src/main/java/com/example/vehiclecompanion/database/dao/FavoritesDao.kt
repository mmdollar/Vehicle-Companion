package com.example.vehiclecompanion.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vehiclecompanion.database.entity.FavoritePlaceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {

    @Query(value = "SELECT * FROM favorite_places")
    fun getAll(): Flow<List<FavoritePlaceEntity>>

    @Query(value = "SELECT * FROM favorite_places WHERE id = :placeId")
    suspend fun getById(placeId: Int): FavoritePlaceEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(place: FavoritePlaceEntity)

    @Query(value = "DELETE FROM favorite_places WHERE id = :id")
    suspend fun deleteById(id: Int)
}