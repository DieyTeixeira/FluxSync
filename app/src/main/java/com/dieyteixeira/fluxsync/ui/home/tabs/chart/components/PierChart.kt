package com.dieyteixeira.fluxsync.ui.home.tabs.chart.components

import android.util.Log
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieyteixeira.fluxsync.app.di.model.Grafico
import com.dieyteixeira.fluxsync.app.theme.ColorFontesDark
import com.dieyteixeira.fluxsync.app.theme.ColorFontesLight
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun PieChart(
    data: List<Grafico>,
    radiusOuter: Dp = 100.dp,
    chartBarWidth: Dp = 40.dp,
    iconSize: Dp = 20.dp,
    animDuration: Int = 1000,
) {

    if (data.isEmpty()) return

    val totalSum = data.sumOf { it.valor }.takeIf { it > 0 } ?: 1.0

    val floatValue = data.map { grafico ->
        360 * grafico.valor.toFloat() / totalSum.toFloat()
    }

    val colors = data.map { it.color }
    val icon = data.map { it.icon }

    var animationPlayed by remember { mutableStateOf(false) }

    var lastValue = 0f

    val animateSize by animateFloatAsState(
        targetValue = if (animationPlayed) radiusOuter.value * 1.5f else 0f,
        animationSpec = tween(animDuration, 0, LinearOutSlowInEasing)
    )

    val animateRotationGrafic by animateFloatAsState(
        targetValue = if (animationPlayed) 90f * 11f else 0f,
        animationSpec = tween(animDuration, 0, LinearOutSlowInEasing)
    )

    val animateScale by animateFloatAsState(
        targetValue = if (animationPlayed) 1f else 0f,
        animationSpec = tween(animDuration, 300, LinearOutSlowInEasing)
    )

    val animateRotationIcon by animateFloatAsState(
        targetValue = if (animationPlayed) 90f * 11f else 0f,
        animationSpec = tween(animDuration, 300, LinearOutSlowInEasing)
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier.size(animateSize.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier
                    .size(radiusOuter * 2f)
                    .rotate(animateRotationGrafic)
            ) {
                floatValue.forEachIndexed { index, value ->
                    val spacing = 1f
                    drawArc(
                        color = colors.getOrElse(index) { Color.Gray },
                        startAngle = lastValue + spacing,
                        sweepAngle = value - spacing,
                        useCenter = false,
                        style = Stroke(chartBarWidth.toPx(), cap = StrokeCap.Butt)
                    )
                    lastValue += value
                }
            }
            Box(
                modifier = Modifier
                    .scale(animateScale)
                    .rotate(animateRotationIcon)
            ) {
                data.forEachIndexed { index, grafico ->
                    val anguloMedio = floatValue.subList(0, index).sum() + floatValue[index] / 2
                    val raioInterno = ((radiusOuter - chartBarWidth / 2)).value

                    val iconX = raioInterno * cos(Math.toRadians(anguloMedio.toDouble()))
                    val iconY = raioInterno * sin(Math.toRadians(anguloMedio.toDouble()))

                    Image(
                        painter = painterResource(id = icon[index]),
                        contentDescription = null,
                        modifier = Modifier
                            .size(iconSize)
                            .offset(x = iconX.dp, y = iconY.dp)
                            .rotate(90f)
                    )
                }
            }
            Text(
                text = "%\nGASTOS POR\nCATEGORIA",
                style = MaterialTheme.typography.displayMedium,
                color = ColorFontesLight,
                textAlign = TextAlign.Center
            )
        }

        DetailsPieChart(
            data = data,
            colors = colors
        )

    }

}

@Composable
fun DetailsPieChart(
    data: List<Grafico>,
    colors: List<Color>
) {
    Column(
        modifier = Modifier
            .padding(top = 80.dp)
            .fillMaxWidth()
    ) {
        val totalSum = data.sumOf { it.valor }

        data.forEachIndexed { index, grafico ->
            DetailsPieChartItem(
                data = Triple(grafico.nome, grafico.valor, grafico.icon),
                totalSum = totalSum,
                color = colors[index]
            )
        }

    }
}

@Composable
fun DetailsPieChartItem(
    data: Triple<String, Double, Int>,
    totalSum: Double,
    color: Color
) {

    val percentual = (data.second / totalSum) * 100

    Surface(
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 40.dp),
        color = Color.Transparent
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .background(
                        color = color,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .size(25.dp)
            ) {
                Image(
                    painter = painterResource(id = data.third),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(start = 15.dp),
                    text = data.first,
                    style = MaterialTheme.typography.displayMedium,
                    color = ColorFontesDark
                )
                Text(
                    modifier = Modifier.padding(start = 15.dp),
                    text = "%.2f%%".format(percentual),
                    style = MaterialTheme.typography.displayMedium,
                    color = ColorFontesDark
                )
            }
        }
    }
}