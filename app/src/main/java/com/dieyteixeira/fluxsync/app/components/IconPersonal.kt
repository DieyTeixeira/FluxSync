package com.dieyteixeira.fluxsync.app.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun IconPersonal(
    color: Color,
    icon: ImageVector
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .offset(y = -2.dp)
                .size(25.dp)
                .background(
                    color = color,
                    shape = RoundedCornerShape(100)
                )
        )
        Icon(
            imageVector = icon,
            contentDescription = "Ícone de banco",
            tint = Color.Black,
            modifier = Modifier
                .offset(y = 3.dp)
                .size(25.dp)
        )
    }
}