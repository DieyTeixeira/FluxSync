package com.dieyteixeira.fluxsync.app.di.model

import java.util.Date

data class Transacoes(
    var id: String = "",
    var descricao: String = "", // descricao do lançamento
    var valor: String = "", // valor do lançamento
    var tipo: String = "", // tipo do lançamento (entrada ou saída)
    var situacao: String = "", // situação do lançamento (pendente, paga, etc.)
    var categoriaId: String = "", // ID da categoria do lançamento
    var contaId: String = "", // ID da conta do lançamento
    var data: Date = Date(), // data do lançamento
    var lancamento: String = "", // tipo de lançamento (único, fixo ou parcelado)
    var parcelas: Int = 0, // número de parcelas do lançamento
    var dataVencimento: Date = Date(), // data de vencimento do lançamento
    var dataPagamento: Date = Date(), // data de pagamento do lançamento
    var observacao: String = "" // observação do lançamento
)