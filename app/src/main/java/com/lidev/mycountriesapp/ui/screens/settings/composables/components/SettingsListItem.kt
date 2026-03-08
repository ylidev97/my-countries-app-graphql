package com.lidev.mycountriesapp.ui.screens.settings.composables.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lidev.mycountriesapp.ui.theme.MyCountriesAppTheme
import com.lidev.mycountriesapp.ui.theme.MyIcons

@Composable
fun SettingsListItem(
    title: String,
    subtitle: String? = null,
    icon: ImageVector,
    iconContainerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    iconContentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    trailingContent: @Composable (() -> Unit)? = null,
    onClick: () -> Unit,
) {
    ListItem(
        headlineContent = {
            Text(
                title,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
            )
        },
        supportingContent =
            subtitle?.let {
                {
                    Text(
                        it,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            },
        leadingContent = {
            Surface(
                modifier = Modifier.size(40.dp),
                shape = CircleShape,
                color = iconContainerColor,
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = iconContentColor,
                    )
                }
            }
        },
        trailingContent = trailingContent,
        modifier = Modifier.clickable { onClick() },
        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
    )
}

@Preview(showBackground = true)
@Composable
private fun SettingsListItemPreview() {
    MyCountriesAppTheme {
        SettingsListItem(
            title = "Settings Item",
            subtitle = "This is a subtitle",
            icon = MyIcons.settingsIcon,
            onClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsListItemNoSubtitlePreview() {
    MyCountriesAppTheme {
        SettingsListItem(
            title = "Settings Item",
            icon = MyIcons.languageIcon,
            onClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsListItemWithTrailingPreview() {
    MyCountriesAppTheme {
        SettingsListItem(
            title = "Notifications",
            subtitle = "Enable or disable notifications",
            icon = MyIcons.notificationsIcon,
            trailingContent = {
                Text("On")
            },
            onClick = {},
        )
    }
}
