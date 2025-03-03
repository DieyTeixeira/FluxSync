package com.dieyteixeira.fluxsync.app.components

import java.util.Calendar

fun mesAtual(): Int {

    val mesAtual = Calendar.getInstance().get(Calendar.MONTH)

    return mesAtual
}

fun anoAtual(): Int {

    val anoAtual = Calendar.getInstance().get(Calendar.YEAR)

    return anoAtual
}

fun nomeMesAtual(): String {

    val mesAtual = Calendar.getInstance().get(Calendar.MONTH)
    val meses = listOf(
        "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
        "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
    )

    return meses[mesAtual]
}

fun listarMeses(
    anoAtual: Int
): List<String> {

    val meses = listOf(
        "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
        "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
    )
    val listaMeses = mutableListOf<String>()

    for (i in 0 until 12) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.MONTH, i)
        }
        val mes = meses[i]
        val ano = calendar.get(Calendar.YEAR)

        if (ano == anoAtual) {
            listaMeses.add(mes)
        } else {
            val anoAbreviado = anoAtual.toString().takeLast(2)
            listaMeses.add("${mes.take(3)}-$anoAbreviado")
        }
    }

    return listaMeses
}