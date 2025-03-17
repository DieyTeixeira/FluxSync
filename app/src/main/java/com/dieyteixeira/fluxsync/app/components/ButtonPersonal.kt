package com.dieyteixeira.fluxsync.app.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieyteixeira.fluxsync.app.theme.ColorGrayLight
import com.dieyteixeira.fluxsync.app.theme.ColorNegative
import com.dieyteixeira.fluxsync.app.theme.ColorPositive
import com.dieyteixeira.fluxsync.app.theme.LightColor2

@Composable
fun ButtonSinal(
    typeLancamento: String
) {
    Box(
        modifier = Modifier
            .size(17.dp)
            .border(
                width = 1.5.dp,
                color = if (typeLancamento == "Despesa") ColorNegative else ColorPositive,
                shape = RoundedCornerShape(100)
            ),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(9.dp)
                .background(
                    color = if (typeLancamento == "Despesa") ColorNegative else ColorPositive,
                    shape = RoundedCornerShape(100)
                )
        )
    }
}

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
    width: Dp,
    rounded: Dp = 15.dp
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .border(
                width = 1.3.dp,
                color = color,
                shape = RoundedCornerShape(rounded)
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

@Composable
fun ButtonPersonalMaxWidth(
    onClick: () -> Unit,
    text: String,
    colorText: Color,
    color: Color = Color.Transparent,
    colorBorder: Color = Color.Transparent,
    height: Dp,
    width: Float = 1f,
    icon: Boolean = true
) {
    val interactionSource = remember { MutableInteractionSource() }
    val iconVisivel = remember { mutableStateOf(icon) }

    Row(
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
            .fillMaxWidth(width)
            .height(height)
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) {
                onClick()
            },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (iconVisivel.value) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Ícone de adição",
                tint = colorText
            )
            Spacer(modifier = Modifier.width(5.dp))
        }
        Text(
            text = text,
            color = colorText,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ButtonPersonalIcon(
    onClick: () -> Unit,
    icon: Int,
    color: Color,
    size: Dp,
    sizeIcon: Dp = 20.dp
) {
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = Modifier
            .background(
                color = ColorGrayLight,
                shape = CircleShape
            )
            .size(size)
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) {
                onClick()
            },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = "Icon",
            modifier = Modifier.size(sizeIcon),
            colorFilter = ColorFilter.tint(color)
        )
    }
}