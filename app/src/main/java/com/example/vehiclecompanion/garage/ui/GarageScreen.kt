package com.example.vehiclecompanion.garage.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.vehiclecompanion.base.ui.data.UiState
import com.example.vehiclecompanion.base.ui.theme.VehicleCompanionTheme
import com.example.vehiclecompanion.garage.data.FuelType
import com.example.vehiclecompanion.garage.data.GarageIntent
import com.example.vehiclecompanion.garage.data.GarageUi
import com.example.vehiclecompanion.garage.data.VehicleUi
import kotlin.text.lowercase

@Composable
fun GarageScreen(viewModel: GarageViewModel) {
    val uiState: UiState<GarageUi> by viewModel.uiState.collectAsState()

    GarageContent(
        uiState = uiState,
        onSubmitIntent = { intent -> viewModel.onSubmitIntent(intent) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GarageContent(uiState: UiState<GarageUi>, onSubmitIntent: (GarageIntent) -> Unit) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.size(size = 48.dp))

            Text(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .weight(weight = 1f),
                text = "My Garage",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )

            IconButton(
                onClick = { onSubmitIntent(GarageIntent.ShowSheet) },
                modifier = Modifier.size(size = 48.dp),
                enabled = uiState is UiState.Success
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Vehicle",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        when (uiState) {
            is UiState.Loading -> LoadingView()
            is UiState.Error -> ErrorView()
            is UiState.Success -> {
                if (uiState.data.vehicles.isEmpty()) {
                    AddVehicleContent(onSubmitIntent = onSubmitIntent)
                } else {
                    VehicleList(vehicles = uiState.data.vehicles, onSubmitIntent = onSubmitIntent)
                }

                GarageBottomSheet(
                    isOpen = uiState.data.isSheetOpen,
                    vehicleForEdit = uiState.data.vehicleForEdit,
                    onSubmitIntent = onSubmitIntent
                )
            }
        }
    }
}

@Composable
private fun LoadingView() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun VehicleList(vehicles: List<VehicleUi>, onSubmitIntent: (GarageIntent) -> Unit) {
    LazyColumn {
        items(count = vehicles.count()) { vehicle ->
            VehicleCard(vehicle = vehicles[vehicle], onSubmitIntent = onSubmitIntent)
        }
    }
}

@Composable
private fun AddVehicleContent(onSubmitIntent: (GarageIntent) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.padding(bottom = 16.dp),
            text = "You currently do not have any vehicles in your garage. Please add new vehicles.",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge
        )

        Button(onClick = { onSubmitIntent(GarageIntent.ShowSheet) }) { Text(text = "Add Vehicle") }
    }
}

@Composable
private fun ErrorView() {
    Text("Error Z")
}

@Composable
private fun VehicleCard(
    vehicle: VehicleUi,
    modifier: Modifier = Modifier,
    onSubmitIntent: (GarageIntent) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(all = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            VehicleImage(
                photoUri = vehicle.photoUri,
                modifier = Modifier
                    .size(size = 80.dp)
                    .clip(shape = RoundedCornerShape(size = 8.dp))
            )

            Spacer(modifier = Modifier.width(width = 16.dp))

            Column {
                Text(
                    text = vehicle.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                )
                Text(
                    text = "Model: ${vehicle.model}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                )
                Text(
                    text = "Year: ${vehicle.year} Make: ${vehicle.make}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                )
                Text(
                    text = "VIN: ${vehicle.vin}",
                    style = MaterialTheme.typography.labelSmall,
                    maxLines = 1,
                )
                Text(
                    text = "Fuel: ${
                        vehicle.fuelType.toString().lowercase().replaceFirstChar { it.uppercase() }
                    }",
                    style = MaterialTheme.typography.labelSmall
                )
            }

            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
                IconButton(onClick = { onSubmitIntent(GarageIntent.EditVehicle(vehicle)) }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Vehicle",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                IconButton(onClick = { onSubmitIntent(GarageIntent.DeleteVehicle(vehicle.id)) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Vehicle",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Composable
private fun VehicleImage(photoUri: String?, modifier: Modifier) {
    if (photoUri != null) {
        AsyncImage(
            model = photoUri,
            contentDescription = "Vehicle Photo",
            modifier = modifier,
            contentScale = ContentScale.Crop
        )
    } else {
        Box(
            modifier = modifier.background(color = MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = Icons.Default.DirectionsCar, contentDescription = null)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GaragePreview_Success() {
    val mockedVehicles = listOf(
        VehicleUi(
            name = "My Truck",
            make = "Ford",
            model = "F-150",
            year = 2022,
            vin = "1234535678",
            fuelType = FuelType.GASOLINE,
            photoUri = "test"
        ),
        VehicleUi(
            name = "My Truck 2",
            make = "Ford",
            model = "F-150 E",
            year = 2025,
            vin = "1234355489",
            fuelType = FuelType.ELECTRIC,
            photoUri = "test"
        )
    )

    VehicleCompanionTheme {
        GarageContent(
            uiState = UiState.Success(data = GarageUi(vehicles = mockedVehicles)),
            onSubmitIntent = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GaragePreview_Empty() {
    VehicleCompanionTheme {
        GarageContent(
            uiState = UiState.Success(data = GarageUi(vehicles = emptyList())),
            onSubmitIntent = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GaragePreview_Loading() {
    VehicleCompanionTheme {
        GarageContent(
            uiState = UiState.Loading,
            onSubmitIntent = {}
        )
    }
}