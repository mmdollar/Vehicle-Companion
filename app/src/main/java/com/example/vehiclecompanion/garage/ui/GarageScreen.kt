package com.example.vehiclecompanion.garage.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.vehiclecompanion.base.ui.theme.VehicleCompanionTheme

@Composable
fun GarageScreen() {
    Text(text = "Garage screen")
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VehicleCompanionTheme {
        GarageScreen()
    }
}