package com.lidev.mycountriesapp.ui.widget

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.lidev.mycountriesapp.R
import com.lidev.mycountriesapp.domain.model.AppLanguage
import com.lidev.mycountriesapp.domain.model.Country
import com.lidev.mycountriesapp.domain.usecases.GetCountriesUseCase
import com.lidev.mycountriesapp.domain.usecases.GetLanguageUseCase
import com.lidev.mycountriesapp.ui.MainActivity
import kotlinx.coroutines.flow.first
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.Locale

class MyAppWidget :
    GlanceAppWidget(),
    KoinComponent {
    private val getLanguageUseCase: GetLanguageUseCase by inject()
    private val getCountriesUseCase: GetCountriesUseCase by inject()

    override suspend fun provideGlance(
        context: Context,
        id: GlanceId,
    ) {
        val appLanguage = getLanguageUseCase().first()
        val localizedContext = getLocalizedContext(context, appLanguage)

        // Fetch dynamic data
        val countriesResult = getCountriesUseCase()
        val randomCountry = countriesResult.getOrNull()?.randomOrNull()

        provideContent {
            // GlanceTheme default uses Material3 color mapping from the system
            GlanceTheme {
                WidgetContent(localizedContext, randomCountry)
            }
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun getLocalizedContext(
        baseContext: Context,
        language: AppLanguage,
    ): Context {
        val locale =
            when (language) {
                AppLanguage.System -> Locale.getDefault()
                else -> Locale.forLanguageTag(language.tag)
            }

        val config = Configuration(baseContext.resources.configuration)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val localeList = LocaleList(locale)
            config.setLocales(localeList)
        } else {
            config.setLocale(locale)
        }
        return baseContext.createConfigurationContext(config)
    }

    @Composable
    private fun WidgetContent(
        context: Context,
        country: Country?,
    ) {
        Box(
            modifier =
                GlanceModifier
                    .fillMaxSize()
                    .background(GlanceTheme.colors.primaryContainer)
                    .padding(12.dp)
                    .clickable(actionStartActivity<MainActivity>()),
            contentAlignment = Alignment.TopStart,
        ) {
            Column(modifier = GlanceModifier.fillMaxSize()) {
                Row(
                    modifier = GlanceModifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = context.getString(R.string.app_name),
                        modifier = GlanceModifier.defaultWeight(),
                        style =
                            TextStyle(
                                color = GlanceTheme.colors.onPrimaryContainer,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                            ),
                    )
                    Box(
                        modifier =
                            GlanceModifier
                                .size(24.dp)
                                .clickable(actionRunCallback<RefreshAction>()),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text("🔄", style = TextStyle(fontSize = 14.sp))
                    }
                }

                Spacer(modifier = GlanceModifier.height(8.dp))

                if (country != null) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = country.emoji,
                            style = TextStyle(fontSize = 32.sp),
                        )
                        Spacer(modifier = GlanceModifier.padding(4.dp))
                        Column {
                            Text(
                                text = country.name,
                                style =
                                    TextStyle(
                                        color = GlanceTheme.colors.onPrimaryContainer,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                    ),
                            )
                            Text(
                                text = country.continent,
                                style =
                                    TextStyle(
                                        color = GlanceTheme.colors.onPrimaryContainer,
                                        fontSize = 12.sp,
                                    ),
                            )
                        }
                    }
                }

                Spacer(modifier = GlanceModifier.defaultWeight())

                Text(
                    text = "→ " + context.getString(R.string.widget_description),
                    style =
                        TextStyle(
                            color = GlanceTheme.colors.onPrimaryContainer,
                            fontSize = 10.sp,
                        ),
                )

                Text(
                    text = "→ " + context.getString(R.string.widget_developer),
                    style =
                        TextStyle(
                            color = GlanceTheme.colors.onPrimaryContainer,
                            fontSize = 10.sp,
                        ),
                )
            }
        }
    }
}

class RefreshAction : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters,
    ) {
        MyAppWidget().update(context, glanceId)
    }
}

class MyAppWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = MyAppWidget()
}
