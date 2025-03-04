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

fun nomeMesAtual(mesAtual: Int): String {

    val meses = listOf(
        "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
        "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
    )

    return meses[mesAtual]
}

fun listarMeses(): List<String> {

    val meses = listOf(
        "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
        "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
    )
    val listaMeses = mutableListOf<String>()

    for (i in 0 until 12) {
        val mes = meses[i]
        listaMeses.add(mes)
    }

    return listaMeses
}