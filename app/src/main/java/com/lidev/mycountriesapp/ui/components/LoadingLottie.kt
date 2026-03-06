package com.lidev.mycountriesapp.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.lidev.mycountriesapp.R

@Composable
internal fun LoadingLottie() {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.loading),
    )

    val progress by animateLottieCompositionAsState(
        // pass the composition created above
        composition,
        // Iterates Forever
        iterations = LottieConstants.IterateForever,
    )

    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier =
            Modifier
                .size(120.dp),
        contentScale = ContentScale.Crop,
    )
}
