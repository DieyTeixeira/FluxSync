package com.dieyteixeira.fluxsync.ui.home.tabs.graphic.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.Dp
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

@Composable
fun GraphicCardSpiral(
    homeViewModel: HomeViewModel,
    mesSelecionado: String,
    anoSelecionado: Int,
    contasSelecionadas: List<String>
) {

    val categorias = homeViewModel.categorias.value

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

    val graficoData = transacoesPorCategoria.map { (categoria, total) ->
        Grafico(
            id = categoria.id,
            nome = categoria.descricao,
            valor = total,
            color = categoria.color,
            icon = categoria.icon
        )
    }

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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp, 3.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Despesas por categoria",
                    style = MaterialTheme.typography.headlineMedium,
                    color = ColorFontesDark,
                    modifier = Modifier.padding(0.dp, 3.dp)
                )
            }
        }
        Box(modifier = Modifier.fillMaxWidth(0.95f).height(1.dp).background(ColorLine).align(Alignment.CenterHorizontally))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp, 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GraphicSpiral(
                data = graficoData
            )
            GraphicDetailsSpiral(
                data = graficoData
            )
        }
    }
}

@Composable
fun GraphicSpiral(
    data: List<Grafico>
) {

    if (data.isEmpty()) return
    val radiusOuter = 100.dp
    val chartBarWidth = 40.dp

    val totalSum = data.sumOf { it.valor }.takeIf { it > 0 } ?: 1.0
    val floatValue = data.map { grafico ->
        360 * grafico.valor.toFloat() / totalSum.toFloat()
    }
    val colors = data.map { it.color }

    var animationPlayed by remember { mutableStateOf(false) }
    var lastValue = 0f

    val animatedProgress by animateFloatAsState(
        targetValue = if (animationPlayed) 1f else 0f,
        animationSpec = tween(1000, easing = LinearOutSlowInEasing) // Ajuste o tempo da animação aqui
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    val totalAngle = 360 * animatedProgress

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 160.dp)
            .padding(vertical = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .rotate(180f),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier
                    .size(radiusOuter)
            ) {
                floatValue.forEachIndexed { index, value ->
                    val spacing = 1f
                    val sweepAngle = value * (totalAngle / 360)
                    if (totalAngle >= sweepAngle + lastValue) {
                        drawArc(
                            color = colors.getOrElse(index) { Color.Gray },
                            startAngle = 90f + lastValue + spacing,
                            sweepAngle = sweepAngle - spacing,
                            useCenter = false,
                            style = Stroke(chartBarWidth.toPx(), cap = StrokeCap.Butt)
                        )
                        lastValue += sweepAngle
                    }
                }
            }
        }
    }
}

@Composable
fun GraphicDetailsSpiral(
    data: List<Grafico>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        val totalSum = data.sumOf { it.valor }

        data.forEachIndexed { index, details ->
            val percentual = (details.valor / totalSum) * 100

            Surface(
                modifier = Modifier
                    .padding(vertical = 5.dp),
                color = Color.Transparent
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.weight(2f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    color = details.color,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .size(25.dp)
                        ) {
                            Image(
                                painter = painterResource(id = details.icon),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(20.dp)
                            )
                        }
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                modifier = Modifier.padding(start = 15.dp),
                                text = details.nome,
                                style = MaterialTheme.typography.displayMedium,
                                color = ColorFontesDark
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .padding(start = 3.dp)
                                    .background(ColorGrayMedium)
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .border(
                                width = 1.dp,
                                color = ColorGrayMedium,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(10.dp, 5.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = "%.2f%%".format(percentual),
                            style = MaterialTheme.typography.headlineSmall,
                            color = ColorFontesDark
                        )
                        Text(
                            text = "R$ %.2f".format(details.valor),
                            style = MaterialTheme.typography.displaySmall,
                            color = ColorFontesLight
                        )
                    }
                }
            }
        }
    }
}