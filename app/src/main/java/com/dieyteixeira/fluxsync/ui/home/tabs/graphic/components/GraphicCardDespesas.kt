package com.dieyteixeira.fluxsync.ui.home.tabs.graphic.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.DonutSmall
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dieyteixeira.fluxsync.app.components.nomeMesAtual
import com.dieyteixeira.fluxsync.app.di.model.Grafico
import com.dieyteixeira.fluxsync.app.di.model.Transacoes
import com.dieyteixeira.fluxsync.app.theme.ColorCards
import com.dieyteixeira.fluxsync.app.theme.ColorFontesDark
import com.dieyteixeira.fluxsync.app.theme.ColorFontesLight
import com.dieyteixeira.fluxsync.app.theme.ColorGrayMedium
import com.dieyteixeira.fluxsync.app.theme.ColorLine
import com.dieyteixeira.fluxsync.ui.home.viewmodel.HomeViewModel

enum class TypeGraphic {
    PIE,
    BAR
}

@Composable
fun GraphicCardDespesas(
    homeViewModel: HomeViewModel,
    mesSelecionado: String,
    anoSelecionado: Int,
    contasSelecionadas: List<String>
) {

    val categorias = homeViewModel.categorias.value
    val subcategorias = homeViewModel.subcategorias.value
    var selectedChartType by remember { mutableStateOf(TypeGraphic.PIE) }
    var selectedFilter by remember { mutableStateOf("Categoria") }

    var transacoesFiltradas by remember { mutableStateOf(emptyList<Transacoes>()) }

    LaunchedEffect(homeViewModel.transacoes.value, mesSelecionado, anoSelecionado, contasSelecionadas) {
        transacoesFiltradas = homeViewModel.transacoes.value.filter { transacao ->
            val dataTransacao = transacao.data
            val anoCorreto = dataTransacao.year + 1900
            val contaNome = homeViewModel.contas.value.find { it.id == transacao.contaId }?.descricao ?: ""

            nomeMesAtual(dataTransacao.month) == mesSelecionado &&
                    anoCorreto == anoSelecionado&&
                    (contasSelecionadas.contains("Todas") || contasSelecionadas.contains(contaNome))
        }.toList()
    }

    val transacoesPorCategoria = categorias.map { categoria ->
        val transacoesDaCategoria = transacoesFiltradas.filter {
            it.categoriaId == categoria.id && it.tipo == "despesa" && it.valor > 0
        }
        val totalCategoria = transacoesDaCategoria.sumOf { it.valor }

        categoria to totalCategoria
    }.filter { it.second > 0 }

    val graficoDataCategoria = transacoesPorCategoria.map { (categoria, total) ->
        Grafico(
            id = categoria.id,
            nome = categoria.descricao,
            valor = total,
            color = categoria.color,
            icon = categoria.icon
        )
    }

    val transacoesPorSubcategoria = subcategorias.map { subcategoria ->
        val transacoesDaSubcategoria = transacoesFiltradas.filter {
            it.subcategoriaId == subcategoria.id && it.tipo == "despesa" && it.valor > 0
        }
        val totalSubcategoria = transacoesDaSubcategoria.sumOf { it.valor }

        subcategoria to totalSubcategoria
    }.filter { it.second > 0 }

    val graficoDataSubcategoria = transacoesPorSubcategoria.map { (subcategoria, total) ->
        Grafico(
            id = subcategoria.id,
            nome = subcategoria.descricao,
            valor = total,
            color = subcategoria.color,
            icon = subcategoria.icon
        )
    }

    val selectedGrafico = if (selectedFilter == "Categoria") graficoDataCategoria else graficoDataSubcategoria

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 0.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(10.dp),
                ambientColor = Color.Gray,
                spotColor = Color.Gray
            )
            .background(
                color = ColorCards,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp, 3.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Despesas por",
                    style = MaterialTheme.typography.headlineMedium,
                    color = ColorFontesDark,
                    modifier = Modifier.padding(0.dp, 3.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surfaceContainerLow,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clickable { selectedFilter = if (selectedFilter == "Categoria") "Subcategoria" else "Categoria" },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = selectedFilter,
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        modifier = Modifier.padding(vertical = 3.dp)
                    )
                }
            }
        }
        Box(modifier = Modifier.fillMaxWidth(0.95f).height(1.dp).background(ColorLine).align(Alignment.CenterHorizontally))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilterChip(
                    selected = selectedChartType == TypeGraphic.PIE,
                    onClick = { selectedChartType = TypeGraphic.PIE },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.DonutSmall,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    },
                    label = { Text("Rosca") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                        selectedLabelColor = Color.White,
                        selectedLeadingIconColor = Color.White
                    )
                )
                Spacer(modifier = Modifier.width(10.dp))
                FilterChip(
                    selected = selectedChartType == TypeGraphic.BAR,
                    onClick = { selectedChartType = TypeGraphic.BAR },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.BarChart,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    },
                    label = { Text("Barras") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                        selectedLabelColor = Color.White,
                        selectedLeadingIconColor = Color.White
                    )
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            when (selectedChartType) {
                TypeGraphic.PIE ->
                    GraphicSpiral(
                        data = selectedGrafico,
                        mes = mesSelecionado,
                        ano = anoSelecionado
                    )
                TypeGraphic.BAR ->
                    GraphicBar(
                        data = selectedGrafico,
                        mes = mesSelecionado,
                        ano = anoSelecionado
                    )
            }
        }
    }
}