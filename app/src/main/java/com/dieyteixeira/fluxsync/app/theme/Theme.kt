package com.dieyteixeira.fluxsync.app.theme

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowInsetsControllerCompat
import com.dieyteixeira.fluxsync.R
import com.dieyteixeira.fluxsync.app.theme.typography

private val DarkColorScheme = darkColorScheme(
    primary = LightColor1,
    secondary = LightColor2,
    tertiary = LightColor3,
    background = LightColor4
)

private val LightColorScheme = lightColorScheme(
    primary = LightColor1,
    secondary = LightColor2,
    tertiary = LightColor3,
    background = LightColor4
)

@Composable
fun FluxSyncTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    fontSize: Int,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val dynamicTypography = typography.copy(

        labelSmall = typography.labelSmall.copy(fontSize = (12 + fontSize).sp), //100
        labelMedium = typography.labelMedium.copy(fontSize = (14 + fontSize).sp), //100
        labelLarge = typography.labelLarge.copy(fontSize = (16 + fontSize).sp), //100

        bodySmall = typography.bodySmall.copy(fontSize = (14 + fontSize).sp), //300
        bodyMedium = typography.bodyMedium.copy(fontSize = (16 + fontSize).sp), //300
        bodyLarge = typography.bodyLarge.copy(fontSize = (18 + fontSize).sp), //300

        displaySmall = typography.displaySmall.copy(fontSize = (16 + fontSize).sp), //500
        displayMedium = typography.displayMedium.copy(fontSize = (18 + fontSize).sp), //500
        displayLarge = typography.displayLarge.copy(fontSize = (20 + fontSize).sp), //500

        headlineSmall = typography.headlineSmall.copy(fontSize = (18 + fontSize).sp), //700
        headlineMedium = typography.headlineMedium.copy(fontSize = (20 + fontSize).sp), //700
        headlineLarge = typography.headlineLarge.copy(fontSize = (22 + fontSize).sp), //700

        titleSmall = typography.titleSmall.copy(fontSize = (18 + fontSize).sp), //900
        titleMedium = typography.titleMedium.copy(fontSize = (20 + fontSize).sp), //900
        titleLarge = typography.titleLarge.copy(fontSize = (24 + fontSize).sp) //900
    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = dynamicTypography,
        content = content
    )
}