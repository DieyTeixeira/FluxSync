package com.dieyteixeira.fluxsync.app.theme

import androidx.compose.ui.graphics.Color

// paleta padrão do app (verde)
val GreenColor1 = Color(0xFF037063) // MaterialTheme.colorScheme.surfaceContainerLowest
val GreenColor2 = Color(0xFF026054) // MaterialTheme.colorScheme.surfaceContainerLow
val GreenColor3 = Color(0xFF025045) // MaterialTheme.colorScheme.surfaceContainer
val GreenColor4 = Color(0xFF013F35) // MaterialTheme.colorScheme.surfaceContainerHigh
val GreenColor5 = Color(0xFF002F26) // MaterialTheme.colorScheme.surfaceContainerHighest

// paleta 2ª opção (azul)
val BlueColor1 = Color(0xFF0a6aac)
val BlueColor2 = Color(0xFF085b9a)
val BlueColor3 = Color(0xFF054c89)
val BlueColor4 = Color(0xFF033c77)
val BlueColor5 = Color(0xFF002d65)

// paleta 3ª opção (vermelho)
val RedColor1 = Color(0xFFa74935)
val RedColor2 = Color(0xFF943728)
val RedColor3 = Color(0xFF81251b)
val RedColor4 = Color(0xFF6d120d)
val RedColor5 = Color(0xFF5a0000)

// paleta 4ª opção (amarelo)
val YellowColor1 = Color(0xFFc6994a)
val YellowColor2 = Color(0xFFb38838)
val YellowColor3 = Color(0xFFa07725)
val YellowColor4 = Color(0xFF8c6613)
val YellowColor5 = Color(0xFF795500)

// paleta 5ª opção (cinza)
val GrayColor1 = Color(0xFF848892)
val GrayColor2 = Color(0xFF747881)
val GrayColor3 = Color(0xFF636771)
val GrayColor4 = Color(0xFF535760)
val GrayColor5 = Color(0xFF42464f)

// cores para contas
val BlackCont = Color(0xFF2D2E29)
val DarkCont = Color(0xFF999A95)
val GrayCont = Color(0xFFCACBC6)
val RedCont = Color(0xFFFF4D4D)
val YellowCont = Color(0xFFFFD700)
val GreenCont = Color(0xFF76C442)
val BlueCont = Color(0xFF0080CF)

// cores para categorias
val ColorCategory1 = Color(0xFFD4A98A)  // Bege Claro
val ColorCategory2 = Color(0xFFE2CB92)  // Amarelo Pastel
val ColorCategory3 = Color(0xFFFF9696)  // Coral Claro
val ColorCategory4 = Color(0xFFEFAE54)  // Laranja Dourado
val ColorCategory5 = Color(0xFFFCC380)  // Laranja Pastel
val ColorCategory6 = Color(0xFFF7DB15)  // Amarelo
val ColorCategory7 = Color(0xFFFCE78C)  // Amarelo Claro
val ColorCategory8 = Color(0xFF84ABAA)  // Verde Acinzentado
val ColorCategory9 = Color(0xFFA2CAA5)  // Verde Menta
val ColorCategory10 = Color(0xFF94E0B0) // Verde Suave
val ColorCategory11 = Color(0xFFC0DE7E) // Verde Claro
val ColorCategory12 = Color(0xFF84E0EF) // Azul Suave
val ColorCategory13 = Color(0xFF9DD3DF) // Azul Pastel
val ColorCategory14 = Color(0xFF57C5C7) // Azul Água
val ColorCategory15 = Color(0xFFE084DC) // Rosa Neon
val ColorCategory16 = Color(0xFFD6B4E8) // Roxo Lavanda

// cores auxiliares
val ColorGrayDark = Color(0xFFCACBC6)
val ColorGrayMedium = Color(0xFFDEDFDD)
val ColorGrayLight = Color(0xFFE6E7E6)
val ColorBackground = Color(0xFFF0F1F2)
val ColorLine = Color(0xFFF3F4F5)
val ColorCards = Color(0xFFFDFDFD)

// cores de fonte
val ColorFontesLight = Color(0xFF999A95)
val ColorFontesMedium = Color(0xFF666666)
val ColorFontesDark = Color(0xFF2D2E29)

// cores negativo e positivo
val ColorPositive = Color(0xFF1B9C54)
val ColorPositiveLight = Color(0xFF71C58A)
val ColorNegative = Color(0xFFBE0B19)
val ColorNegativeLight = Color(0xFFF08A90)
val ColorAviso = Color(0xFFFFC000)
val ColorCardAviso2 = Color(0xFFFFFDBE)

// cores de tratamento de erros
val ColorError = Color(0xFFFF1E00)
val DarkColorError = Color(0xFFAA0000)
val ColorSuccess = Color(0xFF27B238)
val DarkColorSuccess = Color(0xFF006837)

enum class ColorPalette(val colorName: String, val colors: List<Color>) {
    GREEN("GREEN",listOf(GreenColor1, GreenColor2, GreenColor3, GreenColor4, GreenColor5)),
    BLUE("BLUE", listOf(BlueColor1, BlueColor2, BlueColor3, BlueColor4, BlueColor5)),
    RED("RED", listOf(RedColor1, RedColor2, RedColor3, RedColor4, RedColor5)),
    YELLOW("YELLOW", listOf(YellowColor1, YellowColor2, YellowColor3, YellowColor4, YellowColor5)),
    GRAY("GRAY", listOf(GrayColor1, GrayColor2, GrayColor3, GrayColor4, GrayColor5));

    companion object {
        fun fromString(colorName: String): ColorPalette {
            return entries.find { it.colorName.equals(colorName, ignoreCase = true) } ?: GREEN
        }
    }
}