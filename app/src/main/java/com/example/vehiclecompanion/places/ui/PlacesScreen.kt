package com.example.vehiclecompanion.places.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.vehiclecompanion.base.ui.data.UiState
import com.example.vehiclecompanion.base.ui.generalerror.GeneralError
import com.example.vehiclecompanion.places.data.PlaceUi
import com.example.vehiclecompanion.places.data.PlacesUi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlacesScreen(viewModel: PlacesViewModel) {
    val uiState: UiState<PlacesUi> by viewModel.uiState.collectAsState()
    var selectedPlace by remember { mutableStateOf<PlaceUi?>(value = null) }

    when (val state = uiState) {
        is UiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        is UiState.Error -> GeneralError()
        is UiState.Success -> {
            LazyColumn {
                items(items = state.data.places, key = { place -> place.id }) { place ->
                    PlaceCard(
                        place = place,
                        isFavorite = state.data.favoriteIds.contains(place.id),
                        onFavoriteClick = { isFavorite ->
                            viewModel.toggleFavorite(place = place, isFavorite = isFavorite)
                        },
                        onClick = {
                            selectedPlace = place
                        }
                    )
                }
            }

            selectedPlace?.let { place ->
                val sheetState = rememberModalBottomSheetState(
                    skipPartiallyExpanded = false
                )

                ModalBottomSheet(
                    onDismissRequest = { selectedPlace = null },
                    sheetState = sheetState
                ) {
                    val isFavorite = state.data.favoriteIds.contains(place.id)

                    PlaceDetailBottomSheet(
                        place = place,
                        isFavorite = isFavorite,
                        onFavoriteClick = {
                            viewModel.toggleFavorite(place = place, isFavorite = isFavorite.not())
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun PlaceCard(
    place: PlaceUi,
    isFavorite: Boolean,
    onFavoriteClick: (Boolean) -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(size = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(all = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            PlaceImage(
                imageUrl = place.imageUrl,
                modifier = Modifier.size(size = 72.dp)
            )

            Spacer(modifier = Modifier.width(width = 12.dp))

            Column(
                modifier = Modifier.weight(weight = 1f)
            ) {
                Text(
                    text = place.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(height = 4.dp))

                Text(
                    text = place.category,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(height = 6.dp))

                RatingRow(rating = place.rating)
            }

            IconButton(
                onClick = { onFavoriteClick(!isFavorite) }
            ) {
                Icon(
                    imageVector = if (isFavorite) {
                        Icons.Filled.Favorite
                    } else {
                        Icons.Outlined.FavoriteBorder
                    },
                    contentDescription = "Favorite",
                    tint = if (isFavorite) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
            }
        }
    }
}

@Composable
fun PlaceImage(
    imageUrl: String?,
    modifier: Modifier = Modifier
) {
    if (imageUrl != null) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .clip(shape = RoundedCornerShape(size = 12.dp))
        )
    } else {
        Box(
            modifier = modifier
                .clip(shape = RoundedCornerShape(size = 12.dp))
                .background(color = MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Place,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun RatingRow(rating: Double) {
    Row {
        repeat(times = 5) { index ->
            Icon(
                imageVector = if (rating >= index + 1) {
                    Icons.Filled.Star
                } else {
                    Icons.Outlined.StarOutline
                },
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(size = 16.dp)
            )
        }

        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = "$rating/5",
            style = MaterialTheme.typography.labelSmall
        )
    }
}