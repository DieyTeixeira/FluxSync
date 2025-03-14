package com.dieyteixeira.fluxsync.ui.home.tabs.chart.screen

import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.dieyteixeira.fluxsync.ui.home.viewmodel.HomeViewModel
import kotlin.math.cos
import kotlin.math.sin
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.scale
import com.dieyteixeira.fluxsync.ui.home.tabs.chart.components.PieChart

@Composable
fun ChartTab(homeViewModel: HomeViewModel) {

    val somaPorCategoria by homeViewModel.somaPorCategoria.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            PieChart(
                data = somaPorCategoria
            )
        }
    }
}

@Composable
fun ChartPie(
    categorias: Array<String>,
    icon: Array<Int>,
    color: Array<Color>,
    porcentajes: Array<Float>
) {
    if (categorias.isEmpty() || porcentajes.isEmpty() || porcentajes.sum() == 0f) {
        Log.e("ChartPie", "Nenhum dado válido para desenhar o gráfico.")
        return
    }

    val context = LocalContext.current

    val iconSize = 50
    val iconBitmaps by rememberUpdatedState(
        icon.map { resId ->
            val bitmap = BitmapFactory.decodeResource(context.resources, resId)
            val scaledBitmap = bitmap.scale(iconSize, iconSize)
            scaledBitmap.asImageBitmap()
        }
    )

    val total = porcentajes.sum()
    var anguloAtual = -90f
    val limiteFatiaPequena = 36f

    Canvas(modifier = Modifier.fillMaxSize()) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val raio = size.width / 2.5f

        val textPaintCtg = Paint().asFrameworkPaint().apply {
            textSize = 30f
            isFakeBoldText = true
            textAlign = android.graphics.Paint.Align.CENTER
        }

        val textPaintPct = Paint().asFrameworkPaint().apply {
            textSize = 45f
            isFakeBoldText = true
            textAlign = android.graphics.Paint.Align.CENTER
        }

        porcentajes.forEachIndexed { index, valor ->
            val anguloFinal = (valor / total) * 360

            if (index in icon.indices && index in color.indices && index in categorias.indices) {
                val corFatia = color[index]
                val categoriaNome = categorias[index]
                val percentual = valor / total * 100

                drawArc(
                    color = corFatia,
                    startAngle = anguloAtual,
                    sweepAngle = anguloFinal,
                    useCenter = true,
                    style = Fill
                )

                val anguloMedio = anguloAtual + anguloFinal / 2
                val isPequena = anguloFinal < limiteFatiaPequena
                Log.d("ChartPie", "angFinal: $anguloFinal, angAtual: $anguloAtual, isPequena: $isPequena")

                val raioTexto = if (isPequena) raio * 1.60f else raio * 0.7f
                val textX =
                    centerX + raioTexto * cos(Math.toRadians(anguloMedio.toDouble())).toFloat()
                val textY =
                    centerY + raioTexto * sin(Math.toRadians(anguloMedio.toDouble())).toFloat()

                val raioLine = if (isPequena) raio * 1.35f else raio * 0.7f
                val lineX =
                    centerX + raioLine * cos(Math.toRadians(anguloMedio.toDouble())).toFloat()
                val lineY =
                    centerY + raioLine * sin(Math.toRadians(anguloMedio.toDouble())).toFloat()

                if (isPequena) {
                    drawLine(
                        color = Color.Black,
                        start = Offset(
                            centerX + raio * 1f * cos(Math.toRadians(anguloMedio.toDouble())).toFloat(),
                            centerY + raio * 1f * sin(Math.toRadians(anguloMedio.toDouble())).toFloat()
                        ),
                        end = Offset(lineX, lineY),
                        strokeWidth = 3f
                    )

                    drawContext.canvas.nativeCanvas.apply {
                        drawImage(
                            iconBitmaps[index],
                            Offset(textX - iconSize / 2, (textY - iconSize / 2) - 60)
                        )
                        drawText(categoriaNome, textX, textY, textPaintCtg)
                        drawText("%.2f%%".format(percentual), textX, textY + 55, textPaintPct)
                    }
                } else {
                    drawContext.canvas.nativeCanvas.apply {
                        drawImage(
                            iconBitmaps[index],
                            Offset(textX - iconSize / 2, (textY - iconSize / 2) - 60)
                        )
                        drawText(categoriaNome, textX, textY, textPaintCtg)
                        drawText("%.2f%%".format(percentual), textX, textY + 55, textPaintPct)
                    }
                }

                anguloAtual += anguloFinal
            }
        }
    }
}