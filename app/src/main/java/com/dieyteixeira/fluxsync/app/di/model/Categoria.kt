package com.dieyteixeira.fluxsync.app.di.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.dieyteixeira.fluxsync.R

data class Categoria(
    var id: String = "",
    var icon: Int = R.drawable.icon_mais,
    var color: Color = Color.Transparent,
    var descricao: String = ""
)