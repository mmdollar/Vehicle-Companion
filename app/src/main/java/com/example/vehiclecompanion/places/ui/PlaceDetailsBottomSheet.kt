package com.example.vehiclecompanion.places.ui

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.example.vehiclecompanion.BuildConfig
import com.example.vehiclecompanion.places.data.PlaceUi

private const val MAPBOX_BASE_URL = "https://api.mapbox.com/styles/v1/mapbox/streets-v12/static/"

@Composable
fun PlaceDetailBottomSheet(
    place: PlaceUi,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(state = rememberScrollState())
            .padding(all = 16.dp)
    ) {
        place.imageUrl?.let { imageUrl ->
            AsyncImage(
                model = imageUrl,
                contentDescription = place.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 200.dp)
                    .clip(shape = RoundedCornerShape(size = 12.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(height = 16.dp))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = place.name,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.weight(weight = 1f)
            )

            IconButton(
                onClick = onFavoriteClick,
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = if (isFavorite) Color.Red
                    else MaterialTheme.colorScheme.onSurface
                )
            ) {
                Icon(
                    imageVector = if (isFavorite) {
                        Icons.Filled.Favorite
                    } else {
                        Icons.Outlined.FavoriteBorder
                    },
                    contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites"
                )
            }
        }

        Spacer(modifier = Modifier.height(height = 8.dp))

        Text(
            text = place.category,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(height = 8.dp))
        RatingRow(rating = place.rating)
        Spacer(modifier = Modifier.height(height = 16.dp))

        StaticMapImage(
            latitude = place.latitude,
            longitude = place.longitude,
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 200.dp)
                .clip(shape = RoundedCornerShape(size = 12.dp))
        )

        Spacer(modifier = Modifier.height(height = 16.dp))

        place.url?.let { url ->
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Open in Browser")
            }
        }

        Spacer(modifier = Modifier.height(height = 32.dp))
    }
}

@Composable
private fun StaticMapImage(
    latitude: Double,
    longitude: Double,
    modifier: Modifier = Modifier
) {
    val mapUrl = MAPBOX_BASE_URL +
            "pin-s+ff0000($longitude,$latitude)/" +
            "$longitude,$latitude,15/600x400" +
            "?access_token=${BuildConfig.MAPBOX_TOKEN}"

    AsyncImage(
        model = mapUrl,
        contentDescription = "Map location",
        modifier = modifier,
        contentScale = ContentScale.Crop,
        error = painterResource(id = android.R.drawable.ic_menu_mapmode)
    )
}

@Preview(
    showBackground = true,
    name = "Place Detail Bottom Sheet"
)
@Composable
fun PlaceDetailBottomSheetPreview() {
    MaterialTheme {
        PlaceDetailBottomSheet(
            place = PlaceUi(
                id = 1,
                name = "Central Park",
                category = "Park",
                rating = 4.6,
                imageUrl = "https://images.unsplash.com/photo-1508609349937-5ec4ae374ebf",
                latitude = 40.785091,
                longitude = -73.968285,
                url = "https://www.centralparknyc.org"
            ),
            isFavorite = true,
            onFavoriteClick = {}
        )
    }
}