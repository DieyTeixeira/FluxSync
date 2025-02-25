package com.dieyteixeira.fluxsync.app.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ButtonPersonalFilled(
    onClick: () -> Unit,
    text: String,
    colorText: Color,
    color: Color,
    colorBorder: Color = Color.Transparent,
    height: Dp,
    width: Dp
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .background(
                color = color,
                shape = RoundedCornerShape(15.dp)
            )
            .border(
                width = 1.3.dp,
                color = colorBorder,
                shape = RoundedCornerShape(15.dp)
            )
            .height(height)
            .width(width)
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = colorText,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ButtonPersonalOutline(
    onClick: () -> Unit,
    text: String,
    colorText: Color,
    color: Color,
    height: Dp,
    width: Dp
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .border(
                width = 1.3.dp,
                color = color,
                shape = RoundedCornerShape(15.dp)
            )
            .height(height)
            .width(width)
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = colorText,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ButtonPersonalText(
    onClick: () -> Unit,
    text: String,
    colorText: Color,
    height: Dp,
    width: Dp
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .height(height)
            .width(width)
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = colorText,
            fontWeight = FontWeight.Bold
        )
    }
}