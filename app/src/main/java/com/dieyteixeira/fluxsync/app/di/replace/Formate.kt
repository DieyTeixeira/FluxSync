package com.dieyteixeira.fluxsync.app.di.replace

import java.text.NumberFormat
import java.util.Locale

fun formatarValorEdit(valor: Double): String {
    val formato = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    return formato.format(valor).replace("R$", "").trim()
}