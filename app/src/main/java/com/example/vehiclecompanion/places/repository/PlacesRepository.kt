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
            swCorner = SW_CORNER,
            neCorner = NE_CORNER
        )

        val places = response.pois.map { mapper.mapDtoToUiModel(placeDto = it) }
        emit(value = places)

    }.flowOn(context = Dispatchers.IO)


    fun getFavorites(): Flow<List<FavoritePlaceEntity>> = favoritesDao.getAll()

    suspend fun toggleFavorite(place: PlaceUi, isFavorite: Boolean) {
        if (isFavorite) {
            favoritesDao.insert(place = mapper.mapUiToEntity(placeUi = place))
        } else {
            favoritesDao.deleteById(id = place.id)
        }
    }

    private companion object {
        const val SW_CORNER = "-84.540499,39.079888"
        const val NE_CORNER = "-84.494260,39.113254"
    }
}