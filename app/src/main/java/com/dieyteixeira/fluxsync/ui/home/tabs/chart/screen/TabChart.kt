package com.dieyteixeira.fluxsync.ui.home.tabs.chart.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dieyteixeira.fluxsync.app.di.model.Grafico
import com.dieyteixeira.fluxsync.ui.home.viewmodel.HomeViewModel
import com.dieyteixeira.fluxsync.ui.home.tabs.chart.components.PieChart

@Composable
fun ChartTab(homeViewModel: HomeViewModel) {

    val categorias = homeViewModel.categorias.value
    val transacoes = homeViewModel.transacoes.value

    val transacoesPorCategoria = categorias.map { categoria ->
        val transacoesDaCategoria = transacoes.filter {
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
                data = graficoData
            )
        }
    }
}