package com.dieyteixeira.fluxsync.app.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.google.firebase.annotations.concurrent.Background

val LightColor1 = Color(0xFF0FC550)
val LightColor2 = Color(0xFF0C9D40)
val LightColor3 = Color(0xFF0A8630)
val LightColor4 = Color(0xFF086E20)
val LightColor5 = Color(0xFF065810)

val DarkColor1 = Color(0xFFB17512)
val DarkColor2 = Color(0xFF9E650E)
val DarkColor3 = Color(0xFF8B5509)
val DarkColor4 = Color(0xFF774505)
val DarkColor5 = Color(0xFF643500)

val Background = Color(0xFFF5F6F8)

val ColorError = Color(0xFFFF1E00)
val DarkColorError = Color(0xFFAA0000)
val ColorSuccess = Color(0xFF27B238)
val DarkColorSuccess = Color(0xFF006837)

val LightBackground = Brush.linearGradient(
    colors = listOf(LightColor3, LightColor5)
)

val DarkBackground = Brush.linearGradient(
    colors = listOf(DarkColor4, DarkColor5)
)