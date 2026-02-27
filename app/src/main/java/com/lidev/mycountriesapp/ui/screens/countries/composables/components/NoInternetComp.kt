package com.lidev.mycountriesapp.ui.screens.countries.composables.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lidev.mycountriesapp.R
import com.lidev.mycountriesapp.ui.components.NoInternetLottie
import com.lidev.mycountriesapp.ui.theme.MyCountriesAppTheme
import com.lidev.mycountriesapp.ui.theme.dimens

@Composable
fun NoInternetComp(
    modifier: Modifier = Modifier,
    onRetry: () -> Unit = {},
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(
                    top = MaterialTheme.dimens.extraExtraLarge,
                ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        NoInternetLottie(
            modifier =
                Modifier.size(
                    NoInternetCompDefault.NO_INTERNET_SIZE,
                ),
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.medium))
        Button(onClick = onRetry) {
            Text(text = stringResource(id = R.string.retry))
        }
    }
}

private object NoInternetCompDefault {
    val NO_INTERNET_SIZE = 360.dp
}

@Preview(showBackground = true)
@Composable
private fun NoInternetCompPreview() {
    MyCountriesAppTheme {
        NoInternetComp()
    }
}
