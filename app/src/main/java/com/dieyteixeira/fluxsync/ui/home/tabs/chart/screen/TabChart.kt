package com.dieyteixeira.fluxsync.ui.home.tabs.chart.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dieyteixeira.fluxsync.app.components.ButtonPersonalOutline
import com.dieyteixeira.fluxsync.app.components.listarMeses
import com.dieyteixeira.fluxsync.app.components.nomeMesAtual
import com.dieyteixeira.fluxsync.app.di.model.Grafico
import com.dieyteixeira.fluxsync.app.di.model.Transacoes
import com.dieyteixeira.fluxsync.ui.home.viewmodel.HomeViewModel
import com.dieyteixeira.fluxsync.ui.home.tabs.chart.components.PieChart
import com.dieyteixeira.fluxsync.ui.home.tabs.transaction.components.SelectAnoDialog
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import kotlin.collections.plus

@Composable
fun ChartTab(homeViewModel: HomeViewModel) {

    var showDialogAno by remember { mutableStateOf(false) }

    var anoSelecionado by remember { mutableStateOf(Calendar.getInstance().get(Calendar.YEAR)) }
    val mesesComEspacos = listOf("") + listarMeses() + listOf(" ")

    val lazyListStateMes = rememberLazyListState()
    val snapBehaviorMes = rememberSnapFlingBehavior(lazyListStateMes)
    val horizontalFadeMes = Brush.horizontalGradient(
        0f to Color.Transparent,
        0.35f to Color.Gray,
        0.5f to Color.Gray,
        0.5f to Color.Gray,
        0.65f to Color.Gray,
        1f to Color.Transparent
    )
    val mesCentral by remember {
        derivedStateOf {
            val middleIndex = if (lazyListStateMes.firstVisibleItemScrollOffset == 0) {
                lazyListStateMes.firstVisibleItemIndex + 1
            } else {
                lazyListStateMes.firstVisibleItemIndex + 2
            }
            mesesComEspacos.getOrNull(middleIndex) ?: nomeMesAtual(Calendar.getInstance().get(Calendar.MONTH))
        }
    }

    val categorias = homeViewModel.categorias.value
    val transacoes = homeViewModel.transacoes.value

    var transacoesFiltradas by remember { mutableStateOf(emptyList<Transacoes>()) }

    LaunchedEffect(homeViewModel.transacoes.value, mesCentral, anoSelecionado) {
        transacoesFiltradas = homeViewModel.transacoes.value.filter { transacao ->
            val dataTransacao = transacao.data
            val anoCorreto = dataTransacao.year + 1900
            nomeMesAtual(dataTransacao.month) == mesCentral &&
                    anoCorreto == anoSelecionado
        }.toList()
    }

    val transacoesPorCategoria = categorias.map { categoria ->
        val transacoesDaCategoria = transacoesFiltradas.filter {
            it.categoriaId == categoria.id && it.tipo == "despesa" && it.valor > 0
        }
        val totalCategoria = transacoesDaCategoria.sumOf { it.valor }

        categoria to totalCategoria
    }.filter { it.second > 0 }

    val graficoData = transacoesPorCategoria.map { (categoria, total) ->
        Grafico(
            id = categoria.id,
            nome = categoria.descricao,
            valor = total,
            color = categoria.color,
            icon = categoria.icon
        )
    }

    LaunchedEffect(Unit) {
        val mesAtualIndex = Calendar.getInstance().get(Calendar.MONTH)
        lazyListStateMes.scrollToItem(mesAtualIndex, -100)
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(MaterialTheme.colorScheme.surfaceContainerLowest),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Fluxo de caixa",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 20.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                ButtonPersonalOutline(
                    onClick = { showDialogAno = true },
                    text = "$anoSelecionado",
                    colorText = Color.White,
                    color = Color.White,
                    height = 25.dp,
                    width = 60.dp,
                    rounded = 10.dp
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(MaterialTheme.colorScheme.surfaceContainer)
        ) {
            LazyRow(
                state = lazyListStateMes,
                flingBehavior = snapBehaviorMes,
                modifier = Modifier
                    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
                    .drawWithContent {
                        drawContent()
                        drawRect(brush = horizontalFadeMes, blendMode = BlendMode.DstIn)
                    }
                    .fillMaxWidth()
                    .height(50.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(mesesComEspacos) { mes ->
                    if (mes == "") { Spacer(modifier = Modifier.width(35.dp)) }
                    Text(
                        text = mes,
                        style = if (mes == mesCentral) {
                            MaterialTheme.typography.displayLarge
                        } else {
                            MaterialTheme.typography.labelLarge
                        },
                        color = Color.White,
                        modifier = Modifier
                            .height(30.dp)
                            .width(100.dp)
                            .wrapContentHeight(),
                        textAlign = TextAlign.Center
                    )
                    if (mes == " ") { Spacer(modifier = Modifier.width(35.dp)) }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            PieChart(
                data = graficoData
            )
        }

        if (showDialogAno) {
            SelectAnoDialog(
                anoSelecionado = anoSelecionado,
                onClickAno = { anoSelecionado = it },
                onClickClose = { showDialogAno = false }
            )
        }
    }
}