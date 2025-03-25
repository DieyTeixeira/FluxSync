package com.dieyteixeira.fluxsync.ui.home.tabs.graphic.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dieyteixeira.fluxsync.app.components.ButtonPersonalOutline
import com.dieyteixeira.fluxsync.app.components.listarMeses
import com.dieyteixeira.fluxsync.app.components.nomeMesAtual
import com.dieyteixeira.fluxsync.app.di.model.Grafico
import com.dieyteixeira.fluxsync.app.di.model.Transacoes
import com.dieyteixeira.fluxsync.app.theme.ColorCards
import com.dieyteixeira.fluxsync.ui.home.tabs.graphic.components.GraphicCardColumns
import com.dieyteixeira.fluxsync.ui.home.tabs.graphic.components.GraphicCardFilters
import com.dieyteixeira.fluxsync.ui.home.tabs.graphic.components.GraphicCardSpiral
import com.dieyteixeira.fluxsync.ui.home.tabs.graphic.components.GraphicDetailsSpiral
import com.dieyteixeira.fluxsync.ui.home.viewmodel.HomeViewModel
import com.dieyteixeira.fluxsync.ui.home.tabs.graphic.components.GraphicSpiral
import com.dieyteixeira.fluxsync.ui.home.tabs.transaction.components.SelectAnoDialog
import java.util.Calendar
import kotlin.collections.plus

@Composable
fun GraphicTabScreen(homeViewModel: HomeViewModel) {

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

    LaunchedEffect(Unit) {
        val mesAtualIndex = Calendar.getInstance().get(Calendar.MONTH)
        lazyListStateMes.scrollToItem(mesAtualIndex, -100)
    }

    val contasDisponiveis = listOf("Todas") + homeViewModel.contas.value.map { it.descricao }
    var contasSelecionadas by remember { mutableStateOf(listOf("Todas")) }

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
                    text = "Relatórios",
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
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(1.dp))
            }
            item {
                GraphicCardFilters(
                    contasDisponiveis = contasDisponiveis,
                    contasSelecionadas = contasSelecionadas,
                    onSelecionarContas = { selecionadas ->
                        contasSelecionadas = selecionadas
                    }
                )
            }
            item {
                GraphicCardColumns(
                    homeViewModel = homeViewModel,
                    mesSelecionado = mesCentral,
                    anoSelecionado = anoSelecionado,
                    contasSelecionadas = contasSelecionadas
                )
            }
            item {
                GraphicCardSpiral(
                    homeViewModel = homeViewModel,
                    mesSelecionado = mesCentral,
                    anoSelecionado = anoSelecionado,
                    contasSelecionadas = contasSelecionadas
                )
            }
            item {
                Spacer(modifier = Modifier.height(64.dp))
            }
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