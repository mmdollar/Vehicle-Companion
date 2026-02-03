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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.NavDisplay
import com.example.vehiclecompanion.base.ui.generalerror.GeneralError
import com.example.vehiclecompanion.garage.ui.GarageScreen
import com.example.vehiclecompanion.garage.ui.GarageViewModel
import com.example.vehiclecompanion.places.ui.PlacesScreen
import com.example.vehiclecompanion.places.ui.PlacesViewModel

@Composable
fun BottomNavigationBar(backStack: NavBackStack<NavKey>) {
    NavigationBar {
        NavigationBarItem(
            selected = backStack.last() is Screen.Garage,
            onClick = {
                backStack.clear()
                backStack.add(Screen.Garage)
            },
            icon = { Icon(imageVector = Icons.Default.Garage, contentDescription = null) },
            label = { Text(text = "Garage") }
        )
        NavigationBarItem(
            selected = backStack.last() is Screen.Places,
            onClick = {
                backStack.clear()
                backStack.add(Screen.Places)
            },
            icon = { Icon(imageVector = Icons.Default.Place, contentDescription = null) },
            label = { Text(text = "Places") }
        )
        NavigationBarItem(
            selected = backStack.last() is Screen.Garage,
            onClick = {
                backStack.clear()
                backStack.add(Screen.Garage)
            },
            icon = { Icon(imageVector = Icons.Default.Favorite, contentDescription = null) },
            label = { Text(text = "Favorites") }
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
                Screen.Garage -> NavEntry(key = screen) {
                    val viewModel: GarageViewModel = hiltViewModel()
                    GarageScreen(viewModel = viewModel)
                }

                Screen.Places -> NavEntry(key = screen) {
                    val viewModel: PlacesViewModel = hiltViewModel()
                    PlacesScreen(viewModel = viewModel)
                }

                else -> NavEntry(key = screen) {
                    GeneralError()
                }
            }
        }
    )
}