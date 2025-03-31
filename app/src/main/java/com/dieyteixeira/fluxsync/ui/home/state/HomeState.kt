package com.dieyteixeira.fluxsync.ui.home.state

import java.text.NumberFormat
import java.util.Locale

fun formatarValor(valor: Double): String {
    val formato = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    return formato.format(valor)
}

data class Transacao1(
    val descricao: String,
    val valor: String,
    val tipo: String
)

val transacoes1 = listOf(
    Transacao1("Supermercado", "R$ 250,00", "despesa"),
    Transacao1("Salário", "R$ 5.000,00", "receita"),
    Transacao1("Restaurante", "R$ 50,00", "despesa"),
    Transacao1("Cinema", "R$ 30,00", "despesa"),
    Transacao1("Transporte", "R$ 20,00", "despesa"),
    Transacao1("Academia", "R$ 80,00", "despesa"),
)

data class ItemAjuste(
    val text: String
)

val itemsAjuste = listOf(
    ItemAjuste("Contas"),
    ItemAjuste("Categorias")
)