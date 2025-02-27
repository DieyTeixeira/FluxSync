package com.dieyteixeira.fluxsync.app.di.replace

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.toColorInt
import com.dieyteixeira.fluxsync.R

fun iconToStringCategoria(icon: Int): String {
    return when (icon) {
        R.drawable.icon_cifrao -> "icon_cifrao"
        R.drawable.icon_hamburguer -> "icon_hamburguer"
        R.drawable.icon_livro -> "icon_livro"
        R.drawable.icon_caiaque -> "icon_caiaque"
        R.drawable.icon_coracao -> "icon_coracao"
        R.drawable.icon_carro -> "icon_carro"
        R.drawable.icon_aplicativos -> "icon_aplicativos"
        R.drawable.icon_banco -> "icon_banco"
        R.drawable.icon_investimento -> "icon_investimento"
        R.drawable.icon_ferramenta -> "icon_ferramenta"
        R.drawable.icon_cafe -> "icon_cafe"
        R.drawable.icon_computador -> "icon_computador"
        R.drawable.icon_editar -> "icon_editar"
        R.drawable.icon_ciclismo -> "icon_ciclismo"
        R.drawable.icon_envelope -> "icon_envelope"
        R.drawable.icon_academia -> "icon_academia"
        R.drawable.icon_casa -> "icon_casa"
        R.drawable.icon_compras -> "icon_compras"
        R.drawable.icon_talheres -> "icon_talheres"
        R.drawable.icon_mapa -> "icon_mapa"
        R.drawable.icon_fatura -> "icon_fatura"
        R.drawable.icon_musica -> "icon_musica"
        R.drawable.icon_aviao -> "icon_aviao"
        R.drawable.icon_carrinho -> "icon_carrinho"
        R.drawable.icon_praia -> "icon_praia"
        R.drawable.icon_documento -> "icon_documento"
        R.drawable.icon_cama -> "icon_cama"
        R.drawable.icon_nadador -> "icon_nadador"
        else -> "icon_aplicativos"
    }
}

fun stringToIconCategoria(iconString: String): Int {
    return when (iconString) {
        "icon_cifrao" -> R.drawable.icon_cifrao
        "icon_hamburguer" -> R.drawable.icon_hamburguer
        "icon_livro" -> R.drawable.icon_livro
        "icon_caiaque" -> R.drawable.icon_caiaque
        "icon_coracao" -> R.drawable.icon_coracao
        "icon_carro" -> R.drawable.icon_carro
        "icon_aplicativos" -> R.drawable.icon_aplicativos
        "icon_banco" -> R.drawable.icon_banco
        "icon_investimento" -> R.drawable.icon_investimento
        "icon_ferramenta" -> R.drawable.icon_ferramenta
        "icon_cafe" -> R.drawable.icon_cafe
        "icon_computador" -> R.drawable.icon_computador
        "icon_editar" -> R.drawable.icon_editar
        "icon_ciclismo" -> R.drawable.icon_ciclismo
        "icon_envelope" -> R.drawable.icon_envelope
        "icon_academia" -> R.drawable.icon_academia
        "icon_casa" -> R.drawable.icon_casa
        "icon_compras" -> R.drawable.icon_compras
        "icon_talheres" -> R.drawable.icon_talheres
        "icon_mapa" -> R.drawable.icon_mapa
        "icon_fatura" -> R.drawable.icon_fatura
        "icon_musica" -> R.drawable.icon_musica
        "icon_aviao" -> R.drawable.icon_aviao
        "icon_carrinho" -> R.drawable.icon_carrinho
        "icon_praia" -> R.drawable.icon_praia
        "icon_documento" -> R.drawable.icon_documento
        "icon_cama" -> R.drawable.icon_cama
        "icon_nadador" -> R.drawable.icon_nadador
        else -> R.drawable.icon_aplicativos
    }
}

fun colorToStringCategoria(color: Color): String {
    return "#${Integer.toHexString(color.toArgb())}"
}

fun stringToColorCategoria(colorString: String): Color {
    return Color(colorString.toColorInt())
}