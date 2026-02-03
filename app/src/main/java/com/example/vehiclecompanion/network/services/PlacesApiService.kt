package com.example.vehiclecompanion.network.services

import com.example.vehiclecompanion.network.data.PoiResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

private const val MAX_PAGE_SIZE = 50

interface PlacesApiService {

    @GET("api/v2/pois/discover")
    suspend fun discoverPois(
        @Query(value = "sw_corner") swCorner: String,
        @Query(value = "ne_corner") neCorner: String,
        @Query(value = "page_size") pageSize: Int = MAX_PAGE_SIZE
    ): PoiResponseDto
}