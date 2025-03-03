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