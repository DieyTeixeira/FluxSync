package com.dieyteixeira.fluxsync.app.di.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class Categoria(
    var icon: ImageVector = Icons.Default.AddCircleOutline,
    var color: Color = Color.Transparent,
    var descricao: String = ""
)