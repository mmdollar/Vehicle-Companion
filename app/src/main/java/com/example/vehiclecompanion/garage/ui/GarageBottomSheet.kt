package com.example.vehiclecompanion.garage.ui

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.vehiclecompanion.garage.data.FuelType
import com.example.vehiclecompanion.garage.data.GarageIntent
import com.example.vehiclecompanion.garage.data.VehicleUi
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GarageBottomSheet(
    isOpen: Boolean,
    vehicleForEdit: VehicleUi?,
    onSubmitIntent: (GarageIntent) -> Unit
) {
    AddEditBottomSheet(
        isVisible = isOpen,
        onDismissRequest = { onSubmitIntent(GarageIntent.HideSheet) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 24.dp),
            verticalArrangement = spacedBy(space = 8.dp),
        ) {
            VehicleForm(vehicleForEdit = vehicleForEdit, onSubmitIntent = onSubmitIntent)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddEditBottomSheet(
    isVisible: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    sheetMaxWidth: Dp = BottomSheetDefaults.SheetMaxWidth,
    shape: Shape = BottomSheetDefaults.ExpandedShape,
    containerColor: Color = BottomSheetDefaults.ContainerColor,
    contentColor: Color = contentColorFor(backgroundColor = containerColor),
    tonalElevation: Dp = 0.dp,
    scrimColor: Color = BottomSheetDefaults.ScrimColor,
    dragHandle: @Composable (() -> Unit)? = null,
    contentWindowInsets: @Composable () -> WindowInsets = { BottomSheetDefaults.windowInsets },
    properties: ModalBottomSheetProperties = ModalBottomSheetDefaults.properties,
    content: @Composable ColumnScope.() -> Unit,
) {
    LaunchedEffect(key1 = isVisible) {
        if (isVisible) {
            sheetState.expand()
        } else {
            sheetState.hide()
            onDismissRequest()
        }
    }

    if (sheetState.isVisible.not() && isVisible.not()) {
        return
    }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        sheetState = sheetState,
        sheetMaxWidth = sheetMaxWidth,
        shape = shape,
        containerColor = containerColor,
        contentColor = contentColor,
        tonalElevation = tonalElevation,
        scrimColor = scrimColor,
        dragHandle = dragHandle,
        contentWindowInsets = contentWindowInsets,
        properties = properties,
        content = content,
        sheetGesturesEnabled = false,
    )
}

@Composable
private fun VehicleForm(vehicleForEdit: VehicleUi?, onSubmitIntent: (GarageIntent) -> Unit) {
    val context = LocalContext.current
    var name by remember { mutableStateOf(value = vehicleForEdit?.name.orEmpty()) }
    var make by remember { mutableStateOf(value = vehicleForEdit?.make.orEmpty()) }
    var model by remember { mutableStateOf(value = vehicleForEdit?.model.orEmpty()) }
    var year by remember { mutableStateOf(value = vehicleForEdit?.year?.toString().orEmpty()) }
    var vin by remember { mutableStateOf(value = vehicleForEdit?.vin.orEmpty()) }
    var selectedPhotoUri by remember { mutableStateOf<String?>(value = vehicleForEdit?.photoUri) }
    var fuelType by remember {
        mutableStateOf(value = vehicleForEdit?.fuelType ?: FuelType.UNKNOWN)
    }
    val stagedVehicles = remember { mutableStateListOf<VehicleUi>() }

    val isFormValid = name.isNotBlank() && make.isNotBlank() &&
            model.isNotBlank() && year.isNotBlank() && vin.length >= 11

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                selectedPhotoUri = saveImageToInternalStorage(context = context, uri = it).orEmpty()
            }
        }
    )

    fun clearFields() {
        name = ""; make = ""; model = ""; year = ""; vin = ""; selectedPhotoUri = null
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 24.dp)
            .verticalScroll(state = rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = spacedBy(space = 16.dp)
    ) {
        Text(
            text = if (vehicleForEdit == null) "Add New Vehicle" else "Edit Vehicle",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Box(
            modifier = Modifier
                .size(size = 120.dp)
                .clip(CircleShape)
                .background(color = MaterialTheme.colorScheme.surfaceVariant)
                .clickable {
                    photoPickerLauncher.launch(
                        input = PickVisualMediaRequest(
                            mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            if (selectedPhotoUri != null) {
                AsyncImage(
                    model = selectedPhotoUri,
                    contentDescription = "Vehicle Photo",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(imageVector = Icons.Default.AddAPhoto, contentDescription = "Add Photo")
            }
        }

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(text = "Vehicle Nickname (e.g. My Daily)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Row(horizontalArrangement = spacedBy(space = 8.dp)) {
            OutlinedTextField(
                value = make,
                onValueChange = { make = it },
                label = { Text(text = "Make") },
                singleLine = true,
                modifier = Modifier.weight(weight = 1f)
            )
            OutlinedTextField(
                value = model,
                onValueChange = { model = it },
                singleLine = true,
                label = { Text(text = "Model") },
                modifier = Modifier.weight(weight = 1f)
            )
        }

        Row(horizontalArrangement = spacedBy(space = 8.dp)) {
            OutlinedTextField(
                value = year,
                onValueChange = { year = it },
                label = { Text(text = "Year") },
                singleLine = true,
                modifier = Modifier.weight(weight = 1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            OutlinedTextField(
                value = vin,
                singleLine = true,
                onValueChange = { if (it.length <= 17) vin = it.uppercase() },
                label = { Text(text = "VIN") },
                modifier = Modifier.weight(weight = 2f)
            )
        }

        FuelTypeDropdown(
            selectedType = fuelType,
            onTypeSelected = { fuelType = it }
        )

        Spacer(modifier = Modifier.height(height = 24.dp))


        if (stagedVehicles.isNotEmpty()) {
            Text(
                text = "${stagedVehicles.size} vehicle(s) ready to save",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Button(
            onClick = {
                stagedVehicles.add(
                    VehicleUi(
                        name = name,
                        make = make,
                        model = model,
                        year = year.toInt(),
                        vin = vin,
                        photoUri = selectedPhotoUri.orEmpty(),
                        fuelType = fuelType
                    )
                )

                clearFields()
            },
            modifier = Modifier
                .alpha(alpha = if (vehicleForEdit == null) 1f else 0f)
                .fillMaxWidth()
                .height(height = 56.dp),
            shape = RoundedCornerShape(size = 12.dp),
            enabled = isFormValid && vehicleForEdit == null,

            ) {
            Text(text = "Add another vehicle")
        }

        Button(
            onClick = {
                stagedVehicles.add(
                    VehicleUi(
                        name = name,
                        make = make,
                        model = model,
                        year = year.toInt(),
                        vin = vin,
                        photoUri = selectedPhotoUri.orEmpty(),
                        fuelType = fuelType
                    )
                )

                onSubmitIntent(GarageIntent.SaveVehicles(vehicles = stagedVehicles))
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 56.dp),
            enabled = isFormValid,
            shape = RoundedCornerShape(size = 12.dp)
        ) {
            Text(text = if (stagedVehicles.isNotEmpty()) "Save all to Garage" else "Save to Garage")
        }

        Button(
            onClick = { onSubmitIntent(GarageIntent.HideSheet) },
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 56.dp),
            shape = RoundedCornerShape(size = 12.dp)
        ) {
            Text(text = "Cancel")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FuelTypeDropdown(
    selectedType: FuelType,
    onTypeSelected: (FuelType) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val displayText = selectedType.name.lowercase().replaceFirstChar { it.uppercase() }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = displayText,
            onValueChange = {},
            readOnly = true,
            label = { Text(text = "Fuel Type") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            FuelType.entries.forEach { type ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = type.name.lowercase().replaceFirstChar { it.uppercase() })
                    },
                    onClick = {
                        onTypeSelected(type)
                        expanded = false
                    }
                )
            }
        }
    }
}

private fun saveImageToInternalStorage(context: Context, uri: Uri): String? = try {
    val fileName = "vehicle_${System.currentTimeMillis()}.jpg"
    val file = File(context.filesDir, fileName)

    context.contentResolver.openInputStream(uri)?.use { inputStream ->
        FileOutputStream(file).use { outputStream ->
            inputStream.copyTo(outputStream)
        }
    }

    file.absolutePath
} catch (e: Exception) {
    e.printStackTrace()
    null
}