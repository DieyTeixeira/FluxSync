package com.dieyteixeira.fluxsync.app.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp

@Composable
fun FluxSyncTheme(
    selectedPalette: ColorPalette = ColorPalette.GREEN,
    fontSize: Int,
    content: @Composable () -> Unit
) {
    val colorScheme = lightColorScheme(
        surfaceContainerLowest = selectedPalette.colors[0],
        surfaceContainerLow = selectedPalette.colors[1],
        surfaceContainer = selectedPalette.colors[2],
        surfaceContainerHigh = selectedPalette.colors[3],
        surfaceContainerHighest = selectedPalette.colors[4],

        primary = selectedPalette.colors[0],
        secondary = selectedPalette.colors[0],
        tertiary = selectedPalette.colors[0],
        background = selectedPalette.colors[0],
        surface = selectedPalette.colors[0],
    )

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