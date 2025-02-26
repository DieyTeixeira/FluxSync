package com.dieyteixeira.fluxsync.app.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// cores padrão do app (light)
val LightColor1 = Color(0xFF037063)
val LightColor2 = Color(0xFF026054)
val LightColor3 = Color(0xFF025045)
val LightColor4 = Color(0xFF013F35)
val LightColor5 = Color(0xFF002F26)

// cores padrão do app (dark)
val DarkColor1 = Color(0xFFB17512)
val DarkColor2 = Color(0xFF9E650E)
val DarkColor3 = Color(0xFF8B5509)
val DarkColor4 = Color(0xFF774505)
val DarkColor5 = Color(0xFF643500)

// cores para contas
val BlackCont = Color(0xFF2D2E29)
val DarkCont = Color(0xFF999A95)
val GrayCont = Color(0xFFCACBC6)
val RedCont = Color(0xFFFF4D4D)
val YellowCont = Color(0xFFFFD700)
val GreenCont = Color(0xFF76C442)
val BlueCont = Color(0xFF0080CF)

// cores para categorias
val BrownCategory = Color(0xFFD4A98A)
val RedCategory = Color(0xFFFF9696)
val OrangeCategory = Color(0xFFFCC380)
val YellowCategory = Color(0xFFFCE78C)
val GreenCategory = Color(0xFFC0DE7E)
val BlueCategory = Color(0xFF84E0EF)
val PurpleCategory = Color(0xFFD6B4E8)

// cores auxiliares
val ColorGray = Color(0xFFCACBC6)
val ColorBackground = Color(0xFFF0F1F2)
val ColorLine = Color(0xFFF3F4F5)
val ColorCards = Color(0xFFFDFDFD)

// cores de fonte
val ColorFontesLight = Color(0xFF999A95)
val ColorFontesDark = Color(0xFF2D2E29)

// cores negativo e positivo
val ColorPositive = Color(0xFF1B9C54)
val ColorNegative = Color(0xFFBE0B19)

// cores de tratamento de erros
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