package com.dieyteixeira.fluxsync.app.di.model

import java.util.Date

data class Relatorio(
    val descricao: String,
    val valor: Double,
    val tipo: String,
    val situacao: String,
    val categoria: String,
    val subcategoria: String,
    val conta: String,
    val data: Date,
    val lancamento: String,
    val parcelas: String,
    val observacao: String
)