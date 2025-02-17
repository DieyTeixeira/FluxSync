package com.dieyteixeira.fluxsync.ui.home.screen.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ChartTab() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "Chart"
        )
    }
}