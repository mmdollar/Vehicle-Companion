package com.example.vehiclecompanion.base.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen: NavKey {
    @Serializable data object Garage : Screen()
    @Serializable data object GeneralError: Screen()
}