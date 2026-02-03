package com.example.vehiclecompanion.places.repository

import com.example.vehiclecompanion.database.dao.FavoritesDao
import com.example.vehiclecompanion.database.entity.FavoritePlaceEntity
import com.example.vehiclecompanion.network.services.PlacesApiService
import com.example.vehiclecompanion.places.data.PlaceUi
import com.example.vehiclecompanion.places.mapper.PlacesMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PlacesRepository @Inject constructor(
    private val api: PlacesApiService,
    private val favoritesDao: FavoritesDao,
    private val mapper: PlacesMapper
) {

    fun getPlaces(): Flow<List<PlaceUi>> = flow {
        val response = api.discoverPois(
            swCorner = "-84.540499,39.079888",
            neCorner = "-84.494260,39.113254"
        )

        val places = response.pois.map { mapper.mapDtoToUiModel(placeDto = it) }
        emit(value = places)

    }.flowOn(context = Dispatchers.IO)


    fun getFavorites(): Flow<List<FavoritePlaceEntity>> = favoritesDao.getAll()

    suspend fun toggleFavorite(place: PlaceUi, isFavorite: Boolean) {
        val entity = FavoritePlaceEntity(
            id = place.id,
            name = place.name,
            imageUrl = place.imageUrl
        )

        if (isFavorite) {
            favoritesDao.insert(place = entity)
        } else {
            favoritesDao.delete(place = entity)
        }
    }
}