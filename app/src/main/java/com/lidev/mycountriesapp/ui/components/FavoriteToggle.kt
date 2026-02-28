package com.lidev.mycountriesapp.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import com.lidev.mycountriesapp.ui.theme.localColors

@Composable
internal fun FavoriteToggle(
    isFavorite: Boolean,
    onToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scale = remember { Animatable(1f) }

    LaunchedEffect(isFavorite) {
        scale.animateTo(
            targetValue = if (isFavorite) 2f else 1.0f,
            animationSpec = tween(durationMillis = 200),
        )
        scale.animateTo(
            targetValue = 1.0f,
            animationSpec = tween(durationMillis = 200),
        )
    }

    IconButton(onClick = {
        onToggle(!isFavorite)
    }, modifier = modifier) {
        Icon(
            imageVector = if (isFavorite) Icons.Filled.Star else Icons.Filled.StarBorder,
            contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
            tint = if (isFavorite) MaterialTheme.localColors.favorite else MaterialTheme.localColors.favoriteEmpty,
            modifier =
                Modifier
                    .graphicsLayer {
                        scaleX = scale.value
                        scaleY = scale.value
                    },
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
