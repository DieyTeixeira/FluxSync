package com.dieyteixeira.fluxsync.ui.home.tabs.chart

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
import androidx.compose.runtime.getValue
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

    val percentuais by homeViewModel.percentuaisParaGrafico.collectAsState(initial = emptyList())
    val categorias by homeViewModel.categoriasParaGrafico.collectAsState(initial = emptyList())

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

                    }
                )
            )
            if (percentuais.isNotEmpty() && categorias.isNotEmpty()) {
                ChartPie(
                    porcentajes = percentuais.toTypedArray()
                )
            }
        }
    }
}

@Composable
fun ChartPie(
    porcentajes:Array<Float>
) {
    val anguloInicial = -90f
    var anguloActual = anguloInicial
    var anguloFinal = 0f
    val total = porcentajes.sum()

    val cores = listOf(
        BrownCategory,
        RedCategory,
        OrangeCategory,
        YellowCategory,
        GreenCategory,
        BlueCategory,
        PurpleCategory
    )

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(4.dp)){
        porcentajes.forEachIndexed {index, element->
            Canvas(modifier = Modifier.size(400.dp)) {
                val centerX = size.width / 2
                val centerY = size.height / 2
                //cuantos grados va aumentar

                anguloFinal = (element / total) * 360
                val midAngle = anguloActual + anguloFinal / 2

                val corFatia = cores[index % cores.size]

                drawArc(
                    color = corFatia,
                    startAngle = anguloActual,
                    sweepAngle = anguloFinal,
                    useCenter = true,
                    style = Fill
                )

                anguloActual += anguloFinal

                // Calcula las coordenadas para el texto
                val textX =
                    centerX + (size.width / 3) * cos(Math.toRadians(midAngle.toDouble()).toFloat())
                val textY =
                    centerY + (size.height / 3) * sin(Math.toRadians(midAngle.toDouble()).toFloat())

                // Dibuja el texto con el porcentaje
                val textPaint = Paint().asFrameworkPaint()
                textPaint.color = Color.Black.toArgb()
                textPaint.textSize = 55f
                textPaint.isFakeBoldText = true

                // Desenhando o texto
                drawContext.canvas.nativeCanvas.drawText(
                    "${(element / total * 100).toInt()}%",
                    textX - textPaint.measureText("${(element / total * 100).toInt()}%") / 2, // Corrige o deslocamento
                    textY,
                    textPaint
                )
            }
        }
    }
}