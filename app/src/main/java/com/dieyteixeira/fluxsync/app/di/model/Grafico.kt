package com.dieyteixeira.fluxsync.app.di.model

import androidx.compose.ui.graphics.Color
import com.dieyteixeira.fluxsync.R

data class Grafico(
    val id: String = "",
    val nome: String = "",
    val valor: Double = 0.0,
    val icon: Int = R.drawable.icon_mais,
    val color: Color = Color.Transparent
)