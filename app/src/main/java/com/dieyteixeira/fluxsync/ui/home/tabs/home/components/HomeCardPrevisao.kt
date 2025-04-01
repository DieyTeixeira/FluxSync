package com.dieyteixeira.fluxsync.ui.home.tabs.home.components

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieyteixeira.fluxsync.app.components.anoAtual
import com.dieyteixeira.fluxsync.app.components.mesAtual
import com.dieyteixeira.fluxsync.app.theme.ColorCards
import com.dieyteixeira.fluxsync.app.theme.ColorFontesDark
import com.dieyteixeira.fluxsync.app.theme.ColorFontesLight
import com.dieyteixeira.fluxsync.app.theme.ColorLine
import com.dieyteixeira.fluxsync.ui.home.viewmodel.HomeViewModel
import java.time.LocalDate
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeCardPrevisao(
    homeViewModel: HomeViewModel
) {

    val contas = homeViewModel.contas.value
    val saldoAtual = contas.sumOf { it.saldo }
    val listaGastos = remember { homeViewModel.getGastosPorDia().orEmpty() }
    Log.d("HomeCardPrevisao", "Lista de Gastos: $listaGastos")

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
            if (listaGastos.isEmpty()) {
                Text(
                    text = "Nenhum gasto registrado",
                    style = MaterialTheme.typography.bodyMedium,
                    color = ColorFontesLight
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(listaGastos.size) { index ->
                        val (dia, gasto) = listaGastos[index]
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Dia $dia",
                                style = MaterialTheme.typography.bodyMedium,
                                color = ColorFontesDark
                            )
                            Text(
                                text = "R$ ${"%.2f".format(gasto)}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (gasto > 0) Color.Red else ColorFontesLight
                            )
                        }
                    }
                }
            }
            LinearChart(
                data = listaGastos.map { it.second },
                labels = listaGastos.map { it.first.toString() }
            )
            // Criar lista aqui
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Saldo estimado para o fim do mês",
                style = MaterialTheme.typography.bodySmall,
                color = ColorFontesLight
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LinearChart(
    data: List<Double>,
    labels: List<String>
) {
    if (data.isEmpty() || labels.isEmpty() || data.size != labels.size) return

    val mes = mesAtual()
    val ano = anoAtual()

    val semanasLabels = getSemanasDoMesComDias(mes, ano, labels.map { it.toInt() })

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val width = size.width
                val height = size.height

                if (width == 0f || height == 0f) return@Canvaspackage com.dieyteixeira.fluxsync.ui.home.tabs.home.components

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
                        import androidx.compose.foundation.layout.width
                        import androidx.compose.foundation.lazy.LazyColumn
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
                        import androidx.compose.ui.unit.dp
                        import androidx.compose.ui.unit.sp
                        import com.dieyteixeira.fluxsync.app.components.anoAtual
                        import com.dieyteixeira.fluxsync.app.components.mesAtual
                        import com.dieyteixeira.fluxsync.app.theme.ColorCards
                        import com.dieyteixeira.fluxsync.app.theme.ColorFontesDark
                        import com.dieyteixeira.fluxsync.app.theme.ColorFontesLight
                        import com.dieyteixeira.fluxsync.app.theme.ColorLine
                        import com.dieyteixeira.fluxsync.ui.home.viewmodel.HomeViewModel
                        import java.time.LocalDate
                        import java.util.Locale

                        @RequiresApi(Build.VERSION_CODES.O)
                        @Composable
                        fun HomeCardPrevisao(
                            homeViewModel: HomeViewModel
                        ) {

                            val contas = homeViewModel.contas.value
                            val saldoAtual = contas.sumOf { it.saldo }
                            val listaGastos = remember { homeViewModel.getGastosPorDia().orEmpty() }
                            Log.d("HomeCardPrevisao", "Lista de Gastos: $listaGastos")

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
                                    if (listaGastos.isEmpty()) {
                                        Text(
                                            text = "Nenhum gasto registrado",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = ColorFontesLight
                                        )
                                    } else {
                                        LazyColumn(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(100.dp),
                                            verticalArrangement = Arrangement.spacedBy(10.dp)
                                        ) {
                                            items(listaGastos.size) { index ->
                                                val (dia, gasto) = listaGastos[index]
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.SpaceBetween
                                                ) {
                                                    Text(
                                                        text = "Dia $dia",
                                                        style = MaterialTheme.typography.bodyMedium,
                                                        color = ColorFontesDark
                                                    )
                                                    Text(
                                                        text = "R$ ${"%.2f".format(gasto)}",
                                                        style = MaterialTheme.typography.bodyMedium,
                                                        color = if (gasto > 0) Color.Red else ColorFontesLight
                                                    )
                                                }
                                            }
                                        }
                                    }
                                    LinearChart(
                                        data = listaGastos.map { it.second },
                                        labels = listaGastos.map { it.first.toString() }
                                    )
                                    // Criar lista aqui
                                    Spacer(modifier = Modifier.height(5.dp))
                                    Text(
                                        text = "Saldo estimado para o fim do mês",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = ColorFontesLight
                                    )
                                }
                            }
                        }

                @RequiresApi(Build.VERSION_CODES.O)
                @Composable
                fun LinearChart(
                    data: List<Double>,
                    labels: List<String>
                ) {
                    if (data.isEmpty() || labels.isEmpty() || data.size != labels.size) return

                    val mes = mesAtual()
                    val ano = anoAtual()

                    val semanasLabels = getSemanasDoMesComDias(mes, ano, labels.map { it.toInt() })

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                val width = size.width
                                val height = size.height

                                if (width == 0f || height == 0f) return@Canvas

                                val maxValue = data.maxOrNull() ?: 1.0
                                val minValue = data.minOrNull() ?: 0.0

                                val distance = width / (data.size - 1).coerceAtLeast(1)
                                val points = mutableListOf<PointF>()

                                data.forEachIndexed { index, value ->
                                    val x = index * distance
                                    val y = height - ((value - minValue) / (maxValue - minValue) * height).toFloat()
                                    points.add(PointF(x, y))
                                }

                                if (points.isNotEmpty()) {
                                    val path = Path().apply {
                                        moveTo(points.first().x, points.first().y)
                                        for (i in 1 until points.size) {
                                            lineTo(points[i].x, points[i].y)
                                        }
                                    }

                                    drawPath(path, color = Color.Red, style = Stroke(width = 4f))
                                }
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Box(
                                modifier = Modifier
                                    .height(10.dp)
                                    .width(1.dp)
                                    .background(ColorLine)
                            )
                            semanasLabels.forEach { semana ->
                                Text(
                                    text = semana,
                                    style = MaterialTheme.typography.bodySmall,
                                    fontSize = 10.sp,
                                    color = ColorFontesDark
                                )
                                Box(
                                    modifier = Modifier
                                        .height(10.dp)
                                        .width(1.dp)
                                        .background(ColorLine)
                                )
                            }
                        }
                    }
                }

                @RequiresApi(Build.VERSION_CODES.O)
                fun getSemanasDoMesComDias(mes: Int, ano: Int, diasDoMes: List<Int>): List<String> {
                    val semanas = mutableListOf<String>()
                    val primeiroDia = LocalDate.of(ano, mes, 1)
                    val ultimoDia = primeiroDia.withDayOfMonth(primeiroDia.lengthOfMonth())

                    var dataAtual = primeiroDia
                    var semanaAtual = 1
                    var semanaLabel = "Semana 1"
                    val semanaMap = mutableMapOf<Int, String>()

                    while (!dataAtual.isAfter(ultimoDia)) {
                        semanaMap[dataAtual.dayOfMonth] = semanaLabel

                        if (dataAtual.dayOfWeek.value == 7 || dataAtual.plusDays(1).monthValue != mes) {
                            semanaAtual++
                            semanaLabel = "Semana $semanaAtual"
                        }
                        dataAtual = dataAtual.plusDays(1)
                    }

                    diasDoMes.forEach { dia ->
                        semanas.add(semanaMap[dia] ?: "Semana ?")
                    }

                    return semanas.distinct()
                }

                val maxValue = data.maxOrNull() ?: 1.0
                val minValue = data.minOrNull() ?: 0.0

                val distance = width / (data.size - 1).coerceAtLeast(1)
                val points = mutableListOf<PointF>()

                data.forEachIndexed { index, value ->
                    val x = index * distance
                    val y = height - ((value - minValue) / (maxValue - minValue) * height).toFloat()
                    points.add(PointF(x, y))
                }

                if (points.isNotEmpty()) {
                    val path = Path().apply {
                        moveTo(points.first().x, points.first().y)
                        for (i in 1 until points.size) {
                            lineTo(points[i].x, points[i].y)
                        }
                    }

                    drawPath(path, color = Color.Red, style = Stroke(width = 4f))
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Box(
                modifier = Modifier
                    .height(10.dp)
                    .width(1.dp)
                    .background(ColorLine)
            )
            semanasLabels.forEach { semana ->
                Text(
                    text = semana,
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 10.sp,
                    color = ColorFontesDark
                )
                Box(
                    modifier = Modifier
                        .height(10.dp)
                        .width(1.dp)
                        .background(ColorLine)
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun getSemanasDoMesComDias(mes: Int, ano: Int, diasDoMes: List<Int>): List<String> {
    val semanas = mutableListOf<String>()
    val primeiroDia = LocalDate.of(ano, mes, 1)
    val ultimoDia = primeiroDia.withDayOfMonth(primeiroDia.lengthOfMonth())

    var dataAtual = primeiroDia
    var semanaAtual = 1
    var semanaLabel = "Semana 1"
    val semanaMap = mutableMapOf<Int, String>()

    while (!dataAtual.isAfter(ultimoDia)) {
        semanaMap[dataAtual.dayOfMonth] = semanaLabel

        if (dataAtual.dayOfWeek.value == 7 || dataAtual.plusDays(1).monthValue != mes) {
            semanaAtual++
            semanaLabel = "Semana $semanaAtual"
        }
        dataAtual = dataAtual.plusDays(1)
    }

    diasDoMes.forEach { dia ->
        semanas.add(semanaMap[dia] ?: "Semana ?")
    }

    return semanas.distinct()
}