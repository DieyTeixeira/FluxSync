package com.dieyteixeira.fluxsync.app.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun IconCategoria(
    color: Color,
    icon: Int
) {
    Box(
        modifier = Modifier
            .size(35.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .offset(y = -2.dp)
                .size(28.dp)
                .background(
                    color = color,
                    shape = RoundedCornerShape(100)
                )
        )
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier
                .offset(y = 3.dp)
                .size(23.dp),
            colorFilter = ColorFilter.tint(Color.Black)
        )
    }
}

@Composable
fun IconConta(
    color: Color,
    icon: Int
) {
    Box(
        modifier = Modifier
            .size(35.dp)
            .background(
                color = color,
                shape = RoundedCornerShape(100)
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = "Ícone",
            modifier = Modifier
                .size(23.dp)
        )
    }
}