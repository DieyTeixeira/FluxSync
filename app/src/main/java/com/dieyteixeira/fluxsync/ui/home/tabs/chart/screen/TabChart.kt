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
import com.dieyteixeira.fluxsync.ui.home.viewmodel.HomeViewModel
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