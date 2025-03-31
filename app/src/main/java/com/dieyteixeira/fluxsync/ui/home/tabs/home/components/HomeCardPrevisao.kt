package com.dieyteixeira.fluxsync.ui.home.tabs.home.components

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
import com.dieyteixeira.fluxsync.app.theme.ColorNegative
import com.dieyteixeira.fluxsync.app.theme.ColorPositive
import com.dieyteixeira.fluxsync.ui.home.state.formatarValor
import com.dieyteixeira.fluxsync.ui.home.viewmodel.HomeViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeCardPrevisao(
    homeViewModel: HomeViewModel
) {

    val contas = homeViewModel.contas.value
    val saldoAtual = contas.sumOf { it.saldo }

    val transacoes = homeViewModel.transacoes.value
    val receitasFuturas = transacoes.filter {
        it.tipo == "receita" && it.situacao == "pendente" && it.data.month > mesAtual() && it.data.year == anoAtual()
    }.sumOf { it.valor }
    val despesasFuturas = transacoes.filter {
        it.tipo == "despesa" && it.situacao == "pendente" && it.data.month > mesAtual() && it.data.year == anoAtual()
    }.sumOf { it.valor }
    val previsaoSaldo = saldoAtual + receitasFuturas - despesasFuturas

    val diasDoMes = LocalDate.now().lengthOfMonth()
    val previsoesDiarias = MutableList(diasDoMes) { saldoAtual.toFloat() }
    transacoes.forEach { transacao ->
        val dia = transacao.data.day
        if (dia in previsoesDiarias.indices) {
            if (transacao.tipo == "receita" && transacao.situacao == "pendente") {
                previsoesDiarias[dia] += transacao.valor.toFloat()
            }
            if (transacao.tipo == "despesa" && transacao.situacao == "pendente") {
                previsoesDiarias[dia] -= transacao.valor.toFloat()
            }
        }
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
            Log.d("PrevisaoFinanceira", "Previsoes Diarias: $previsoesDiarias")
            PrevisaoFinanceiraGraph(previsoes = previsoesDiarias)
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = formatarValor(previsaoSaldo),
                style = MaterialTheme.typography.headlineLarge,
                fontSize = 25.sp,
                color = if (previsaoSaldo >= 0) ColorPositive else ColorNegative
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Saldo estimado para o fim do mês",
                style = MaterialTheme.typography.bodySmall,
                color = ColorFontesLight
            )
        }
    }
}

@Composable
fun PrevisaoFinanceiraGraph(
    previsoes: List<Float> // Lista de saldos projetados para cada dia do mês
) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp) // Altura do gráfico
    ) {
        val width = size.width
        val height = size.height
        val stepX = width / previsoes.size // Espaçamento entre os pontos
        val minSaldo = previsoes.minOrNull() ?: 0f
        val maxSaldo = previsoes.maxOrNull() ?: 1f
        val range = maxSaldo - minSaldo

        // Criando o caminho do gráfico
        val path = Path().apply {
            for (i in previsoes.indices) {
                val x = i * stepX
                val y = height - ((previsoes[i] - minSaldo) / range * height) // Normaliza os valores no eixo Y

                if (i == 0) moveTo(x, y) else lineTo(x, y)
            }
        }

        // Desenha o gráfico
        drawPath(
            path = path,
            color = Color.Green, // Cor da linha
            style = Stroke(width = 3.dp.toPx())
        )
    }
}