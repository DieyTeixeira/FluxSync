package com.dieyteixeira.fluxsync.ui.home.state

import java.text.NumberFormat
import java.util.Locale

fun formatarValor(valor: Double): String {
    val formato = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    return formato.format(valor)
}

data class ItemAjuste(
    val text: String
)

val itemsAjuste = listOf(
    ItemAjuste("Contas"),
    ItemAjuste("Categorias"),
    ItemAjuste("Subcategorias")
)