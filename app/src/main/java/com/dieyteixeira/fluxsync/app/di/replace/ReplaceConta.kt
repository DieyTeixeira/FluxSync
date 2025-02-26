package com.dieyteixeira.fluxsync.app.di.replace

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.toColorInt
import com.dieyteixeira.fluxsync.R

fun iconToStringConta(icon: Int): String {
    return when (icon) {
        R.drawable.banco_abc -> "banco_abc"
        R.drawable.banco_ailos -> "banco_ailos"
        R.drawable.banco_amazonia -> "banco_amazonia"
        R.drawable.banco_asaas -> "banco_asaas"
        R.drawable.banco_banese -> "banco_banese"
        R.drawable.banco_banestes -> "banco_banestes"
        R.drawable.banco_bankofamerica -> "banco_bankofamerica"
        R.drawable.banco_banpara -> "banco_banpara"
        R.drawable.banco_banrisul -> "banco_banrisul"
        R.drawable.banco_bib -> "banco_bib"
        R.drawable.banco_bnb -> "banco_bnb"
        R.drawable.banco_bradesco -> "banco_bradesco"
        R.drawable.banco_brasil -> "banco_brasil"
        R.drawable.banco_brb -> "banco_brb"
        R.drawable.banco_bs2 -> "banco_bs2"
        R.drawable.banco_btg -> "banco_btg"
        R.drawable.banco_c6 -> "banco_c6"
        R.drawable.banco_caixa -> "banco_caixa"
        R.drawable.banco_capitual -> "banco_capitual"
        R.drawable.banco_cora -> "banco_cora"
        R.drawable.banco_credisis -> "banco_credisis"
        R.drawable.banco_cresol -> "banco_cresol"
        R.drawable.banco_csimples -> "banco_csimples"
        R.drawable.banco_daycoval -> "banco_daycoval"
        R.drawable.banco_efibank -> "banco_efibank"
        R.drawable.banco_grafeno -> "banco_grafeno"
        R.drawable.banco_inter -> "banco_inter"
        R.drawable.banco_itau -> "banco_itau"
        R.drawable.banco_letsbank -> "banco_letsbank"
        R.drawable.banco_mercadopago -> "banco_mercadopago"
        R.drawable.banco_mercantil -> "banco_mercantil"
        R.drawable.banco_nubank -> "banco_nubank"
        R.drawable.banco_omie -> "banco_omie"
        R.drawable.banco_original -> "banco_original"
        R.drawable.banco_pag -> "banco_pag"
        R.drawable.banco_pine -> "banco_pine"
        R.drawable.banco_quality -> "banco_quality"
        R.drawable.banco_rendimento -> "banco_rendimento"
        R.drawable.banco_safra -> "banco_safra"
        R.drawable.banco_santander -> "banco_santander"
        R.drawable.banco_sicoob -> "banco_sicoob"
        R.drawable.banco_sicredi -> "banco_sicredi"
        R.drawable.banco_sofisa -> "banco_sofisa"
        R.drawable.banco_stone -> "banco_stone"
        R.drawable.banco_topazio -> "banco_topazio"
        R.drawable.banco_tribanco -> "banco_tribanco"
        R.drawable.banco_unicred -> "banco_unicred"
        else -> "icon_mais"
    }
}

fun stringToIconConta(iconString: String): Int {
    return when (iconString) {
        "banco_abc" -> R.drawable.banco_abc
        "banco_ailos" -> R.drawable.banco_ailos
        "banco_amazonia" -> R.drawable.banco_amazonia
        "banco_asaas" -> R.drawable.banco_asaas
        "banco_banese" -> R.drawable.banco_banese
        "banco_banestes" -> R.drawable.banco_banestes
        "banco_bankofamerica" -> R.drawable.banco_bankofamerica
        "banco_banpara" -> R.drawable.banco_banpara
        "banco_banrisul" -> R.drawable.banco_banrisul
        "banco_bib" -> R.drawable.banco_bib
        "banco_bnb" -> R.drawable.banco_bnb
        "banco_bradesco" -> R.drawable.banco_bradesco
        "banco_brasil" -> R.drawable.banco_brasil
        "banco_brb" -> R.drawable.banco_brb
        "banco_bs2" -> R.drawable.banco_bs2
        "banco_btg" -> R.drawable.banco_btg
        "banco_c6" -> R.drawable.banco_c6
        "banco_caixa" -> R.drawable.banco_caixa
        "banco_capitual" -> R.drawable.banco_capitual
        "banco_cora" -> R.drawable.banco_cora
        "banco_credisis" -> R.drawable.banco_credisis
        "banco_cresol" -> R.drawable.banco_cresol
        "banco_csimples" -> R.drawable.banco_csimples
        "banco_daycoval" -> R.drawable.banco_daycoval
        "banco_efibank" -> R.drawable.banco_efibank
        "banco_grafeno" -> R.drawable.banco_grafeno
        "banco_inter" -> R.drawable.banco_inter
        "banco_itau" -> R.drawable.banco_itau
        "banco_letsbank" -> R.drawable.banco_letsbank
        "banco_mercadopago" -> R.drawable.banco_mercadopago
        "banco_mercantil" -> R.drawable.banco_mercantil
        "banco_nubank" -> R.drawable.banco_nubank
        "banco_omie" -> R.drawable.banco_omie
        "banco_original" -> R.drawable.banco_original
        "banco_pag" -> R.drawable.banco_pag
        "banco_pine" -> R.drawable.banco_pine
        "banco_quality" -> R.drawable.banco_quality
        "banco_rendimento" -> R.drawable.banco_rendimento
        "banco_safra" -> R.drawable.banco_safra
        "banco_santander" -> R.drawable.banco_santander
        "banco_sicoob" -> R.drawable.banco_sicoob
        "banco_sicredi" -> R.drawable.banco_sicredi
        "banco_sofisa" -> R.drawable.banco_sofisa
        "banco_stone" -> R.drawable.banco_stone
        "banco_topazio" -> R.drawable.banco_topazio
        "banco_tribanco" -> R.drawable.banco_tribanco
        "banco_unicred" -> R.drawable.banco_unicred
        else -> R.drawable.icon_mais
    }
}

fun colorToStringConta(color: Color): String {
    return "#${Integer.toHexString(color.toArgb())}"
}

fun stringToColorConta(colorString: String): Color {
    return Color(colorString.toColorInt())
}