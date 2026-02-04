package com.example.vehiclecompanion

import com.example.vehiclecompanion.database.entity.VehicleEntity
import com.example.vehiclecompanion.garage.data.FuelType
import com.example.vehiclecompanion.garage.data.VehicleUi
import com.example.vehiclecompanion.garage.mapper.VehicleMapper
import org.junit.Assert.assertEquals
import org.junit.Test

class VehicleMapperTest {

    private val mapper = VehicleMapper()

    @Test
    fun `mapEntityToUi maps entity correctly to UI model`() {
        // Given
        val entity = VehicleEntity(
            id = 1L,
            name = "My Car",
            make = "Toyota",
            model = "Corolla",
            year = 2022,
            vin = "VIN123",
            photo = "path/to/photo",
            fuelType = FuelType.GASOLINE
        )

        val expectedUi = VehicleUi(
            id = 1L,
            name = "My Car",
            make = "Toyota",
            model = "Corolla",
            year = 2022,
            vin = "VIN123",
            photoUri = "path/to/photo",
            fuelType = FuelType.GASOLINE
        )

        // When
        val result = mapper.mapEntityToUi(entity)

        // Then
        assertEquals(expectedUi, result)
    }

    @Test
    fun `mapUiToEntity maps list of UI models to list of entities`() {
        // Given
        val uiList = listOf(
            VehicleUi(10L, "Truck", "Ford", "F-150", 2021, "VIN789", "uri/truck", FuelType.DIESEL)
        )

        val expectedEntities = listOf(
            VehicleEntity(
                10L,
                "Truck",
                "Ford",
                "F-150",
                2021,
                "VIN789",
                "uri/truck",
                FuelType.DIESEL
            )
        )

        // When
        val result = mapper.mapUiToEntity(uiList)

        // Then
        assertEquals(expectedEntities, result)
    }
}