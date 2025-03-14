package com.dieyteixeira.fluxsync.ui.home.tabs.chart

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.dieyteixeira.fluxsync.app.theme.BlueCategory
import com.dieyteixeira.fluxsync.app.theme.BrownCategory
import com.dieyteixeira.fluxsync.app.theme.GreenCategory
import com.dieyteixeira.fluxsync.app.theme.LightColor1
import com.dieyteixeira.fluxsync.app.theme.LightColor2
import com.dieyteixeira.fluxsync.app.theme.LightColor3
import com.dieyteixeira.fluxsync.app.theme.OrangeCategory
import com.dieyteixeira.fluxsync.app.theme.PurpleCategory
import com.dieyteixeira.fluxsync.app.theme.RedCategory
import com.dieyteixeira.fluxsync.app.theme.YellowCategory
import com.dieyteixeira.fluxsync.ui.home.viewmodel.HomeViewModel
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun ChartTab(homeViewModel: HomeViewModel) {

    val somaPorCategoria by homeViewModel.somaPorCategoria.collectAsState()
    val categorias = homeViewModel.categorias.value.associateBy { it.id }

    val labels = somaPorCategoria.keys.mapNotNull { categorias[it]?.descricao }.toTypedArray()
    val valores = somaPorCategoria.values.map { it.toFloat() }.toTypedArray()
    Log.d("ChartTab", "labels: ${labels.joinToString()}")
    Log.d("ChartTab", "valores: ${valores.joinToString()}")

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(200.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Atualizar",
                color = LightColor1,
                modifier = Modifier.clickable(
                    onClick = {
                        homeViewModel.calcularSomaPorCategoria()
                    }
                )
            )
            if (labels.isNotEmpty() && valores.isNotEmpty() && valores.sum() > 0) {
                ChartPie(categorias = labels, porcentajes = valores)
            } else {
                Text("Nenhuma transação encontrada para exibir o gráfico.", color = Color.Gray)
            }
        }
    }
}

@Composable
fun ChartPie(
    categorias: Array<String>,
    porcentajes: Array<Float>
) {
    if (categorias.isEmpty() || porcentajes.isEmpty() || porcentajes.sum() == 0f) {
        Log.e("ChartPie", "Nenhum dado válido para desenhar o gráfico.")
        return
    }

    val total = porcentajes.sum()
    var anguloAtual = -90f

    val cores = listOf(
        BrownCategory, RedCategory, OrangeCategory, YellowCategory,
        GreenCategory, BlueCategory, PurpleCategory
    )

    Box(modifier = Modifier.fillMaxSize().padding(4.dp)) {
        porcentajes.forEachIndexed { index, valor ->
            Canvas(modifier = Modifier.size(400.dp)) {
                val anguloFinal = (valor / total) * 360
                val midAngle = anguloAtual + anguloFinal / 2
                val corFatia = cores[index % cores.size]
                val categoriaNome = categorias[index]
                val percentual = (valor / total) * 100

                drawArc(
                    color = corFatia,
                    startAngle = anguloAtual,
                    sweepAngle = anguloFinal,
                    useCenter = true,
                    style = Fill
                )

                // Exibir texto no centro da fatia
                val centerX = size.width / 2
                val centerY = size.height / 2
                val textX = centerX + (size.width / 3) * cos(Math.toRadians(midAngle.toDouble()).toFloat())
                val textY = centerY + (size.height / 3) * sin(Math.toRadians(midAngle.toDouble()).toFloat())

                val textPaint = Paint().asFrameworkPaint().apply {
                    color = Color.Black.toArgb()
                    textSize = 30f
                    isFakeBoldText = true
                    textAlign = android.graphics.Paint.Align.CENTER
                }

                drawContext.canvas.nativeCanvas.apply {
                    drawText(categoriaNome, textX, textY, textPaint)
                    drawText("%.1f%%".format(percentual), textX, textY + 30, textPaint)
                }

                anguloAtual += anguloFinal
            }
        }
    }
}