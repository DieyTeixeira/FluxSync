package com.dieyteixeira.fluxsync.ui.home.state

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.Kayaking
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.dieyteixeira.fluxsync.R
import com.dieyteixeira.fluxsync.app.di.model.Categoria
import com.dieyteixeira.fluxsync.app.theme.BlueCategory
import com.dieyteixeira.fluxsync.app.theme.BrownCategory
import com.dieyteixeira.fluxsync.app.theme.GreenCategory
import com.dieyteixeira.fluxsync.app.theme.OrangeCategory
import com.dieyteixeira.fluxsync.app.theme.RedCategory
import com.dieyteixeira.fluxsync.app.theme.YellowCategory
import java.text.NumberFormat
import java.util.Locale

fun formatarValor(valor: Double): String {
    val formato = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    return formato.format(valor)
}

data class Transacao(
    val descricao: String,
    val valor: String,
    val tipo: String
)

val transacoes = listOf(
    Transacao("Supermercado", "R$ 250,00", "despesa"),
    Transacao("Salário", "R$ 5.000,00", "receita"),
    Transacao("Internet", "R$ 100,00", "despesa"),
    Transacao("Transporte", "R$ 50,00", "despesa"),
    Transacao("Venda Online", "R$ 300,00", "receita"),
    Transacao("Energia Elétrica", "R$ 180,00", "despesa"),
    Transacao("Aluguel", "R$ 1.200,00", "despesa"),
    Transacao("Restaurante", "R$ 90,00", "despesa"),
    Transacao("Freelance Design", "R$ 1.500,00", "receita"),
    Transacao("Plano de Saúde", "R$ 250,00", "despesa"),
    Transacao("Assinatura Netflix", "R$ 39,90", "despesa"),
    Transacao("Venda de Móveis", "R$ 700,00", "receita"),
    Transacao("Academia", "R$ 120,00", "despesa"),
    Transacao("Seguro do Carro", "R$ 500,00", "despesa"),
    Transacao("Dividendos Ações", "R$ 200,00", "receita"),
    Transacao("Curso Online", "R$ 300,00", "despesa"),
    Transacao("Reembolso Viagem", "R$ 400,00", "receita"),
    Transacao("Cinema", "R$ 60,00", "despesa"),
    Transacao("Jogo Steam", "R$ 80,00", "despesa"),
    Transacao("Venda de Celular", "R$ 1.000,00", "receita")
)

data class Conta(
    val icon: Int,
    val color: Color,
    val descricao: String,
    val valor: Double
)

val contas = listOf(
    Conta(R.drawable.banco_c6, Color(0xFF000000), "C6 Bank", 253.16),
    Conta(R.drawable.banco_santander, Color.LightGray.copy(alpha = 0.5f), "Banco Santander", 857.49)
)

data class ItemAjuste(
    val text: String
)

val itemsAjuste = listOf(
    ItemAjuste("Contas"),
    ItemAjuste("Categorias")
)