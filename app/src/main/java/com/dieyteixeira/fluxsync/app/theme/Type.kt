package com.dieyteixeira.fluxsync.app.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.dieyteixeira.fluxsync.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.museo_sans_100))
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.museo_sans_300))
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.museo_sans_500))
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.museo_sans_700))
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.museo_sans_900))
    )
)