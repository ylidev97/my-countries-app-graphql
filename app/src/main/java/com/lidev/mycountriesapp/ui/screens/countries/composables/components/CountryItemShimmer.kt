package com.lidev.mycountriesapp.ui.screens.countries.composables.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lidev.mycountriesapp.ui.components.shimmer
import com.lidev.mycountriesapp.ui.theme.MyCountriesAppTheme
import com.lidev.mycountriesapp.ui.theme.dimens

@Composable
internal fun CountryItemShimmer() {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(all = MaterialTheme.dimens.small),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier =
                    Modifier
                        .size(40.dp)
                        .clip(MaterialTheme.shapes.small)
                        .shimmer(),
            )
            Spacer(
                modifier =
                    Modifier.width(
                        MaterialTheme.dimens.mediumSmall,
                    ),
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
            ) {
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth(0.7f)
                            .height(24.dp)
                            .clip(MaterialTheme.shapes.small)
                            .shimmer(),
                )
            }
            Spacer(modifier = Modifier.width(MaterialTheme.dimens.small))
            Box(
                modifier =
                    Modifier
                        .size(24.dp)
                        .clip(MaterialTheme.shapes.small)
                        .shimmer(),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CountryItemShimmerPreview() {
    MyCountriesAppTheme {
        CountryItemShimmer()
    }
}
