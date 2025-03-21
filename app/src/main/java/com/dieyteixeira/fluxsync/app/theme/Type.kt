package com.dieyteixeira.fluxsync.app.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.dieyteixeira.fluxsync.R

val typography = Typography(

    labelSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.museo_sans_100))
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.museo_sans_100))
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.museo_sans_100))
    ),


    bodySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.museo_sans_300))
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.museo_sans_300))
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.museo_sans_300))
    ),

    displaySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.museo_sans_500))
    ),
    displayMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.museo_sans_500))
    ),
    displayLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.museo_sans_500))
    ),

    headlineSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.museo_sans_700))
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.museo_sans_700))
    ),
    headlineLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.museo_sans_700))
    ),

    titleSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.museo_sans_900))
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.museo_sans_900))
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.museo_sans_900))
    )
)