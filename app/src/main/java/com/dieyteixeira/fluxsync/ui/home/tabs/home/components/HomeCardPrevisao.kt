package com.dieyteixeira.fluxsync.ui.home.tabs.home.components

import android.graphics.Paint
import android.graphics.PointF
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import com.dieyteixeira.fluxsync.app.theme.ColorCards
import com.dieyteixeira.fluxsync.app.theme.ColorFontesDark
import com.dieyteixeira.fluxsync.app.theme.ColorFontesLight
import com.dieyteixeira.fluxsync.app.theme.ColorLine
import com.dieyteixeira.fluxsync.ui.home.state.formatarValor
import com.dieyteixeira.fluxsync.ui.home.viewmodel.HomeViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeCardPrevisao(
    homeViewModel: HomeViewModel,
    isSaldoVisivel: Boolean
) {

    val contas = homeViewModel.contas.value
    val saldoAtual = contas.sumOf { it.saldo }
    val listaValores = remember { homeViewModel.getValoresPorDia().orEmpty() }

    val saldoPorDia = listaValores.runningFold(saldoAtual) { saldoAcumulado, (_, valor) ->
        saldoAcumulado + valor
    }
    Log.d("HomeCardPrevisao", "Lista de Valores: $saldoPorDia")

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
                    text = "Previsão Financeira",
                    style = MaterialTheme.typography.headlineMedium,
                    color = ColorFontesDark,
                    modifier = Modifier.padding(0.dp, 3.dp)
                )
            }
        }
        Box(
            modifier = Modifier.fillMaxWidth(0.95f).height(1.dp).background(ColorLine)
                .align(Alignment.CenterHorizontally)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (listaValores.isEmpty()) {
                Text(
                    text = "Nenhum registro encontrado",
                    style = MaterialTheme.typography.bodyMedium,
                    color = ColorFontesLight
                )
            } else {
                LinearChart(
                    dataValue = saldoPorDia.map { it.toInt() },
                    dataText = saldoPorDia.map { it },
                    isSaldoVisivel = isSaldoVisivel
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Estimativa de valores do mês",
                    style = MaterialTheme.typography.bodySmall,
                    color = ColorFontesLight
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LinearChart(
    dataValue: List<Int>,
    dataText: List<Double>,
    isSaldoVisivel: Boolean
) {

    val colorLine = MaterialTheme.colorScheme.surfaceContainerLow

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(vertical = 10.dp)
    ) {
        if (dataValue.isEmpty()) return@Canvas

        val minValue = dataValue.minOrNull() ?: 0
        val maxValue = dataValue.maxOrNull() ?: 1 // Evita divisão por zero
        val range = maxValue - minValue

        val distance = size.width / (dataValue.size + 1)
        var currentX = 0F
        val points = mutableListOf<PointF>()

        dataValue.forEachIndexed { index, currentData ->
            if (dataValue.size >= index + 2) {
                val normalizedY =
                    if (range == 0) 0.5f else (currentData - minValue) / range.toFloat()
                val y0 = size.height * (1 - normalizedY) // Inverte para manter origem no topo
                val x0 = currentX + distance
                points.add(PointF(x0, y0))
                currentX += distance
            }
        }

        if (points.isEmpty()) return@Canvas

        val cubicPoints1 = mutableListOf<PointF>()
        val cubicPoints2 = mutableListOf<PointF>()

        for (i in 1 until points.size) {
            cubicPoints1.add(PointF((points[i].x + points[i - 1].x) / 2, points[i - 1].y))
            cubicPoints2.add(PointF((points[i].x + points[i - 1].x) / 2, points[i].y))
        }

        val path = Path().apply {
            moveTo(points.first().x, points.first().y)
            for (i in 1 until points.size) {
                cubicTo(
                    cubicPoints1[i - 1].x,
                    cubicPoints1[i - 1].y,
                    cubicPoints2[i - 1].x,
                    cubicPoints2[i - 1].y,
                    points[i].x,
                    points[i].y
                )
            }
        }

        drawPath(path, color = colorLine, style = Stroke(width = 7f))

        val textPaint = Paint().apply {
            textSize = 30f
            textAlign = Paint.Align.CENTER
            color = android.graphics.Color.BLACK
        }

        if (points.isNotEmpty()) {
            val firstPoint = points.first()
            val lastPoint = points.last()

            drawContext.canvas.nativeCanvas.drawText(
                if (isSaldoVisivel) formatarValor(dataText.first()) else "R$ *****",
                firstPoint.x + 50,
                firstPoint.y - 15,
                textPaint
            )

            drawContext.canvas.nativeCanvas.drawText(
                if (isSaldoVisivel) formatarValor(dataText.last()) else "R$ *****",
                lastPoint.x - 50,
                lastPoint.y - 15,
                textPaint
            )
        }
    }
}