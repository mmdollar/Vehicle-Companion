package com.example.vehiclecompanion.base.ui.generalerror

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.vehiclecompanion.base.ui.theme.VehicleCompanionTheme

@Composable
fun GeneralError() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Oops, something went wrong.", color = MaterialTheme.colorScheme.error)
    }
}

@Preview(showBackground = true)
@Composable
private fun GeneralErrorPreview() {
    VehicleCompanionTheme {
        GeneralError()
    }
}