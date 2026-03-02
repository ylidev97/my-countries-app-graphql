package com.lidev.mycountriesapp.ui.screens.settings.composables.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lidev.mycountriesapp.ui.theme.dimens

@Composable
fun SettingsSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column {
        Text(
            text = title.uppercase(),
            style =
                MaterialTheme.typography.labelSmall.copy(
                    letterSpacing = 1.5.sp,
                    fontWeight = FontWeight.Bold,
                ),
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
            modifier =
                Modifier.padding(
                    start = MaterialTheme.dimens.medium,
                    bottom = MaterialTheme.dimens.mediumSmall,
                ),
        )
        Surface(
            shape = RoundedCornerShape(MaterialTheme.dimens.extraLarge),
            color = MaterialTheme.colorScheme.surfaceContainerLowest,
            tonalElevation = 1.dp,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                modifier =
                    Modifier
                        .padding(vertical = MaterialTheme.dimens.mediumSmall)
                        .animateContentSize(),
            ) {
                content()
            }
        }
    }
}
