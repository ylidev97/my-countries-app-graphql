package com.lidev.mycountriesapp.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lidev.mycountriesapp.ui.theme.MyCountriesAppTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList

@Composable
fun ScrollBubble(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    firstLetters: ImmutableList<Char>
) {
    val isScrolling by remember {
        derivedStateOf {
            lazyListState.isScrollInProgress
        }
    }

    val firstVisibleItemIndex by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex
        }
    }

    val firstLetter by remember(firstLetters, firstVisibleItemIndex) {
        derivedStateOf {
            if (firstVisibleItemIndex < firstLetters.size) {
                firstLetters[firstVisibleItemIndex]
            } else {
                ' '
            }
        }
    }

    AnimatedVisibility(
        modifier = modifier,
        visible = isScrolling,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Bubble(letter = firstLetter.toString())
    }
}

@Composable
private fun Bubble(
    modifier: Modifier = Modifier,
    letter: String
) {
    Box(
        modifier = modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = letter,
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black
        )
    }
}


@Preview
@Composable
private fun ScrollBubblePreview() {
    MyCountriesAppTheme {
        ScrollBubble(
            lazyListState = LazyListState(
                firstVisibleItemIndex = 1
            ),
            firstLetters = listOf('A', 'B', 'C').toPersistentList()
        )
    }
}

@Preview
@Composable
private fun BubblePreview() {
    MyCountriesAppTheme {
        Bubble(letter = "A")
    }
}