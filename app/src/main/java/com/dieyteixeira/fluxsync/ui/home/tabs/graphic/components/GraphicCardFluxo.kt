package com.dieyteixeira.fluxsync.ui.home.tabs.graphic.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dieyteixeira.fluxsync.R
import com.dieyteixeira.fluxsync.app.components.nomeMesAtual
import com.dieyteixeira.fluxsync.app.di.model.Transacoes
import com.dieyteixeira.fluxsync.app.theme.ColorCards
import com.dieyteixeira.fluxsync.app.theme.ColorFontesDark
import com.dieyteixeira.fluxsync.app.theme.ColorFontesLight
import com.dieyteixeira.fluxsync.app.theme.ColorFontesMedium
import com.dieyteixeira.fluxsync.app.theme.ColorGrayMedium
import com.dieyteixeira.fluxsync.app.theme.ColorLine
import com.dieyteixeira.fluxsync.app.theme.ColorNegative
import com.dieyteixeira.fluxsync.app.theme.ColorNegativeLight
import com.dieyteixeira.fluxsync.app.theme.ColorPositive
import com.dieyteixeira.fluxsync.app.theme.ColorPositiveLight
import com.dieyteixeira.fluxsync.ui.home.viewmodel.HomeViewModel

@Composable
fun GraphicCardFluxo(
    homeViewModel: HomeViewModel,
    mesSelecionado: String,
    anoSelecionado: Int,
    contasSelecionadas: List<String>
) {

    var isVisible by remember { mutableStateOf(true) }
    var transacoesFiltradas by remember { mutableStateOf(emptyList<Transacoes>()) }

    LaunchedEffect(homeViewModel.transacoes.value, mesSelecionado, anoSelecionado, contasSelecionadas) {
        transacoesFiltradas = homeViewModel.transacoes.value
    }

    val meses = listOf(
        getMesAnterior(mesSelecionado, anoSelecionado),
        mesSelecionado to anoSelecionado,
        getMesPosterior(mesSelecionado, anoSelecionado)
    )

    val valoresPorMes = meses.map { (mes, ano) ->
        val transacoesMes = transacoesFiltradas.filter { transacao ->
            val dataTransacao = transacao.data
            val anoCorreto = dataTransacao.year + 1900
            val contaNome = homeViewModel.contas.value.find { it.id == transacao.contaId }?.descricao ?: ""
            nomeMesAtual(dataTransacao.month) == mes && anoCorreto == ano &&
                    (contasSelecionadas.contains("Todas") || contasSelecionadas.contains(contaNome))
        }
        val receitas = transacoesMes.filter { it.tipo == "receita" }.sumOf { it.valor }.toFloat()
        val despesas = transacoesMes.filter { it.tipo == "despesa" }.sumOf { it.valor }.toFloat()
        receitas to despesas
    }

    val maxValor = valoresPorMes.flatMap { listOf(it.first, it.second) }.maxOrNull()?.times(1.2f) ?: 1f

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
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Fluxo de caixa",
                    style = MaterialTheme.typography.headlineMedium,
                    color = ColorFontesDark,
                    modifier = Modifier.padding(0.dp, 3.dp)
                )
                Image(
                    painter = painterResource(id = if (isVisible) R.drawable.icon_visivel else R.drawable.icon_invisivel),
                    contentDescription = "Alternar visibilidade do saldo",
                    modifier = Modifier
                        .size(22.dp)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                        ) { isVisible = !isVisible },
                    colorFilter = ColorFilter.tint(ColorFontesLight)
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .height(1.dp)
                .background(ColorLine)
                .align(Alignment.CenterHorizontally)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp, 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                horizontalArrangement = if (isVisible) Arrangement.SpaceBetween else Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ) {
                meses.forEachIndexed { index, (mes, ano) ->
                    if (isVisible || index == 1) {
                        val (receitas, despesas) = valoresPorMes[index]
                        GraphicChartColumn(
                            receitas = receitas,
                            despesas = despesas,
                            maxValor = maxValor,
                            mesText = mes,
                            ano = ano,
                            isPrincipal = index == 1
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = ColorGrayMedium,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(10.dp, 5.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val (receitas, despesas) = valoresPorMes[1]
                val saldo = receitas - despesas

                GraphicDetailsColumn(
                    value = receitas,
                    text = "receitas"
                )
                GraphicDetailsColumn(
                    value = despesas,
                    text = "despesas"
                )
                GraphicDetailsColumn(
                    value = saldo,
                    text = "saldo"
                )
            }
        }
    }
}

@Composable
fun GraphicChartColumn(
    receitas: Float,
    despesas: Float,
    maxValor: Float,
    mesText: String,
    ano: Int,
    isPrincipal: Boolean = false
) {

    val barWidth = 50f
    val heightSize = 100f
    val heightRatio = if (maxValor > 0) heightSize / maxValor else 0f
    val anoAtual = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)

    val animatedReceitasHeight by animateDpAsState(
        targetValue = if (receitas == 0f) 0.dp else (receitas * heightRatio).dp,
        animationSpec = tween(durationMillis = 1000)
    )

    val animatedDespesasHeight by animateDpAsState(
        targetValue = if (despesas == 0f) 0.dp else (despesas * heightRatio).dp,
        animationSpec = tween(durationMillis = 1000)
    )

    Column(
        modifier = Modifier
            .scale(if (isPrincipal) 1f else 0.7f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .height(heightSize.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Box(
                modifier = Modifier
                    .size(barWidth.dp, heightSize.dp)
                    .clip(RoundedCornerShape(5.dp)),
                contentAlignment = Alignment.BottomCenter
            ) {
                Canvas(modifier = Modifier.matchParentSize()) {
                    val lineSpacing = 5.dp.toPx()
                    val lineColor = Color.Gray.copy(alpha = 0.2f)

                    for (i in 0..(size.width + size.height).toInt() step lineSpacing.toInt()) {
                        drawLine(
                            color = lineColor,
                            start = Offset(i.toFloat(), 0f),
                            end = Offset(0f, i.toFloat()),
                            strokeWidth = 2f
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .size(barWidth.dp, animatedReceitasHeight)
                        .background(if (isPrincipal) ColorPositive else ColorPositiveLight)
                )
            }
            Spacer(modifier = Modifier.width(2.dp))
            Box(
                modifier = Modifier
                    .size(barWidth.dp, heightSize.dp)
                    .clip(RoundedCornerShape(5.dp)),
                contentAlignment = Alignment.BottomCenter
            ) {
                Canvas(modifier = Modifier.matchParentSize()) {
                    val lineSpacing = 5.dp.toPx()
                    val lineColor = Color.Gray.copy(alpha = 0.2f)

                    for (i in 0..(size.width + size.height).toInt() step lineSpacing.toInt()) {
                        drawLine(
                            color = lineColor,
                            start = Offset(i.toFloat(), 0f),
                            end = Offset(0f, i.toFloat()),
                            strokeWidth = 2f
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .size(barWidth.dp, animatedDespesasHeight)
                        .background(if (isPrincipal) ColorNegative else ColorNegativeLight)
                )
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = if (ano == anoAtual) {
                mesText.take(3)
            } else {
                "${mesText.take(3)}/${ano.toString().takeLast(2)}"
            },
            style = MaterialTheme.typography.displaySmall,
            color = ColorFontesMedium
        )
    }
}

@Composable
fun GraphicDetailsColumn(
    value: Float,
    text: String
) {
    val color = when (text) {
        "receitas" -> { ColorPositive }
        "despesas" -> { ColorNegative }
        "saldo" -> { ColorFontesDark }
        else -> { ColorFontesDark }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "R$ ${"%.2f".format(value)}",
            style = MaterialTheme.typography.headlineSmall,
            color = color
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = ColorFontesMedium
        )
    }
}

fun getMesAnterior(mesAtual: String, anoAtual: Int): Pair<String, Int> {
    val meses = listOf(
        "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
        "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
    )
    val index = meses.indexOf(mesAtual)
    return if (index == 0) {
        "Dezembro" to (anoAtual - 1)
    } else {
        meses[index - 1] to anoAtual
    }
}

fun getMesPosterior(mesAtual: String, anoAtual: Int): Pair<String, Int> {
    val meses = listOf(
        "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
        "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
    )
    val index = meses.indexOf(mesAtual)
    return if (index == 11) {
        "Janeiro" to (anoAtual + 1)
    } else {
        meses[index + 1] to anoAtual
    }
}