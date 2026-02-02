package com.example.vehiclecompanion.base.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Garage
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.NavDisplay
import com.example.vehiclecompanion.garage.ui.GarageScreen

@Composable
fun BottomNavigationBar(backStack: NavBackStack<NavKey>) {
    NavigationBar {
        NavigationBarItem(
            selected = backStack.last() is Screen.Garage,
            onClick = {
                backStack.clear()
                backStack.add(Screen.Garage)
            },
            icon = { Icon(Icons.Default.Garage, null) },
            label = { Text("Garage") }
        )
        NavigationBarItem(
            selected = backStack.last() is Screen.Garage,
            onClick = {
                backStack.clear()
                backStack.add(Screen.Garage)
            },
            icon = { Icon(Icons.Default.Place, null) },
            label = { Text("Places") }
        )
        NavigationBarItem(
            selected = backStack.last() is Screen.Garage,
            onClick = {
                backStack.clear()
                backStack.add(Screen.Garage)
            },
            icon = { Icon(Icons.Default.Favorite, null) },
            label = { Text("Favorites") }
        )
    }
}

@Composable
fun NavigationDisplay(backStack: NavBackStack<NavKey>) {
    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = { screen ->
            when (screen) {
                Screen.Garage -> NavEntry(key = screen) { GarageScreen() }
                else -> NavEntry(screen) { GarageScreen() }
            }
        }
    )
}