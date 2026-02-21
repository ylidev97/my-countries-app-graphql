package com.lidev.mycountriesapp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.lidev.mycountriesapp.ui.theme.MyCountriesAppTheme


@Composable
internal fun LoadingDialog() {
    AlertDialog(
        onDismissRequest = {},
        title = {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                LoadingLottie()
            }
        },
        confirmButton = { },
    )
}

@Preview(showBackground = true)
@Composable
private fun LoadingDialogPreview() {
    MyCountriesAppTheme {
        LoadingDialog()
    }
}