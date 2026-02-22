package com.lidev.mycountriesapp.ui.components

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.lidev.mycountriesapp.ui.theme.MyCountriesAppTheme


@Composable
internal fun LoadingDialog(
    isLoading: Boolean
) {
    if (isLoading) {
        Dialog(
            onDismissRequest = {},
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
                usePlatformDefaultWidth = false
            )
        ) {
            Card(
                modifier = Modifier
                    .wrapContentSize()
            ) {
                LoadingLottie()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingDialogPreview() {
    MyCountriesAppTheme {
        LoadingDialog(isLoading = true)
    }
}