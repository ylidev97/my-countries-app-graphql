package com.lidev.mycountriesapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@Composable
internal fun FavoriteToggle(
    isFavorite: Boolean,
    onToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(onClick = { onToggle(!isFavorite) }, modifier = modifier) {
        Icon(
            imageVector = if (isFavorite) Icons.Filled.Star else Icons.Filled.StarBorder,
            contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
            tint = if (isFavorite) Color.Yellow else Color.Gray
        )
    }
}

@Preview
@Composable
private fun FavoriteTogglePreview() {
    FavoriteToggle(isFavorite = true, onToggle = {})
}

@Preview
@Composable
private fun FavoriteTogglePreviewNotFavorite() {
    FavoriteToggle(isFavorite = false, onToggle = {})
}
