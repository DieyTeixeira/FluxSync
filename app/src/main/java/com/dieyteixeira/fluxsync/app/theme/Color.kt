package com.dieyteixeira.fluxsync.app.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val LightColor1 = Color(0xFF037063)
val LightColor2 = Color(0xFF026054)
val LightColor3 = Color(0xFF025045)
val LightColor4 = Color(0xFF013F35)
val LightColor5 = Color(0xFF002F26)

val DarkColor1 = Color(0xFFB17512)
val DarkColor2 = Color(0xFF9E650E)
val DarkColor3 = Color(0xFF8B5509)
val DarkColor4 = Color(0xFF774505)
val DarkColor5 = Color(0xFF643500)

val ColorBackground = Color(0xFFF0F1F2)
val ColorCards = Color(0xFFFDFDFD)
val ColorFontesLight = Color(0xFF999A95)
val ColorFontesDark = Color(0xFF2D2E29)
val ColorPositive = Color(0xFF108770)
val ColorNegative = Color(0xFFBE0B19)


val ColorError = Color(0xFFFF1E00)
val DarkColorError = Color(0xFFAA0000)
val ColorSuccess = Color(0xFF27B238)
val DarkColorSuccess = Color(0xFF006837)

val LightBackground = Brush.linearGradient(
    colors = listOf(LightColor2, LightColor4)
)

val DarkBackground = Brush.linearGradient(
    colors = listOf(DarkColor4, DarkColor5)
)