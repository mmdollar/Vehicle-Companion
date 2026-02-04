package com.example.vehiclecompanion.network.services

import com.example.vehiclecompanion.network.data.PoiDto
import com.example.vehiclecompanion.network.data.PoiResponseDto
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.mockito.kotlin.any

class PlacesApiServiceTest {

    private val mockApiService: PlacesApiService = mock()

    @Test
    fun `discoverPois returns successful list of POIs`() = runTest {
        // Given
        val expectedPois = listOf(
            PoiDto(1, "Golden Gate", null, "Bridge", 4.9, null, listOf(37.8, -122.4))
        )
        val response = PoiResponseDto(pois = expectedPois)

        whenever(mockApiService.discoverPois(any(), any(), any()))
            .thenReturn(response)

        // When
        val result = mockApiService.discoverPois("37,-122", "38,-121", 20)

        // Then
        assertEquals(1, result.pois.size)
        assertEquals("Golden Gate", result.pois[0].name)
    }

    @Test
    fun `discoverPois returns empty list`() = runTest {
        // Given
        whenever(mockApiService.discoverPois(any(), any(), any()))
            .thenReturn(PoiResponseDto(pois = emptyList()))

        // When
        val result = mockApiService.discoverPois(swCorner = "0,0", neCorner = "0,0")

        // Then
        assertTrue(result.pois.isEmpty())
    }

    @Test(expected = Exception::class)
    fun `discoverPois throws exception on network failure`() = runTest {
        // Given
        whenever(mockApiService.discoverPois(any(), any(), any()))
            .thenThrow(RuntimeException("404 Not Found"))

        // When
        mockApiService.discoverPois("any", "any")
    }
}