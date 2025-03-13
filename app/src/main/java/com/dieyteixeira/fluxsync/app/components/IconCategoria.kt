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
    icon: Int,
    sizeBoxExt: Int = 35,
    sizeBoxInt: Int = 28,
    sizeIcon: Int = 23
) {
    Box(
        modifier = Modifier
            .size(sizeBoxExt.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .offset(y = -2.dp)
                .size(sizeBoxInt.dp)
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