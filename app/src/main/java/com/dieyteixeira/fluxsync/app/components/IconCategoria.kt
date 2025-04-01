package com.dieyteixeira.fluxsync.app.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
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
    icon: Int,
    sizeBox: Int = 35,
    sizeIcon: Int = 18,
    rounded: Int = 14,
    border: Double = 4.0
) {

    Box(
        modifier = Modifier
            .size(sizeBox.dp)
            .border(
                width = border.dp,
                color = color,
                shape = RoundedCornerShape(rounded.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier
                .size(sizeIcon.dp),
            colorFilter = ColorFilter.tint(Color.Black)
        )
    }
}

@Composable
fun IconConta(
    color: Color,
    icon: Int,
    sizeBox: Int = 35,
    sizeIcon: Int = 23
) {
    Box(
        modifier = Modifier
            .size(sizeBox.dp)
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
                .size(sizeIcon.dp)
        )
    }
}