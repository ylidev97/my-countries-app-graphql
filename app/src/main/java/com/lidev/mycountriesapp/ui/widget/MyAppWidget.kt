package com.lidev.mycountriesapp.ui.widget

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.LocalContext
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.lidev.mycountriesapp.R
import com.lidev.mycountriesapp.ui.MainActivity

class MyAppWidget : GlanceAppWidget() {
    override suspend fun provideGlance(
        context: Context,
        id: GlanceId,
    ) {
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        val versionName = packageInfo.versionName ?: "1.0"

        provideContent {
            GlanceTheme {
                WidgetContent(versionName)
            }
        }
    }

    @SuppressLint("RestrictedApi")
    @Composable
    private fun WidgetContent(versionName: String) {
        val context = LocalContext.current
        Box(
            modifier =
                GlanceModifier
                    .fillMaxSize()
                    .background(Color(0xFF2196F3))
                    .padding(12.dp)
                    .clickable(actionStartActivity<MainActivity>()),
            contentAlignment = Alignment.TopStart,
        ) {
            Column {
                Text(
                    text = context.getString(R.string.app_name),
                    style =
                        TextStyle(
                            color = ColorProvider(Color.White),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                        ),
                )
                Text(
                    text = context.getString(R.string.widget_version, versionName),
                    style =
                        TextStyle(
                            color = ColorProvider(Color(0xDDFFFFFF)),
                            fontSize = 12.sp,
                        ),
                )
                Text(
                    text = context.getString(R.string.widget_developer),
                    style =
                        TextStyle(
                            color = ColorProvider(Color(0xCCFFFFFF)),
                            fontSize = 11.sp,
                        ),
                )

                Spacer(modifier = GlanceModifier.height(4.dp))

                // Separador
                Box(
                    modifier =
                        GlanceModifier
                            .height(1.dp)
                            .background(Color(0x44FFFFFF)),
                ) {}

                Spacer(modifier = GlanceModifier.height(4.dp))

                Text(
                    text = context.getString(R.string.widget_description),
                    style =
                        TextStyle(
                            color = ColorProvider(Color.White),
                            fontSize = 12.sp,
                        ),
                )
            }
        }
    }
}

class MyAppWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = MyAppWidget()
}
