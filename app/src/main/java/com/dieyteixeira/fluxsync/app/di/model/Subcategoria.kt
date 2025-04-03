package com.dieyteixeira.fluxsync.app.di.model

import androidx.compose.ui.graphics.Color
import com.dieyteixeira.fluxsync.R

data class Subcategoria(
    var id: String = "",
    var icon: Int = R.drawable.icon_mais,
    var color: Color = Color.Transparent,
    var descricao: String = "",
    var tipo: String = ""
)