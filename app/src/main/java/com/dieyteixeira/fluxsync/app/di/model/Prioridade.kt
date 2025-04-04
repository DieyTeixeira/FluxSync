package com.dieyteixeira.fluxsync.app.di.model

import androidx.compose.ui.graphics.Color

data class Prioridade(
    var id: String = "",
    var color: Color = Color.Transparent,
    var descricao: String = ""
)