package com.dieyteixeira.fluxsync.app.configs

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.FormatListBulleted
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Output
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dieyteixeira.fluxsync.app.theme.DarkColor1
import com.dieyteixeira.fluxsync.app.theme.LightColor1
import com.dieyteixeira.fluxsync.app.theme.LightColor2
import com.dieyteixeira.fluxsync.app.theme.LightColor3
import kotlin.random.Random

@Composable
fun BottomNavigationBarWithCenterHighlight(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    var isDialogOpen by remember { mutableStateOf(false) }
    val icons = listOf(
        Icons.Outlined.Home,
        Icons.Outlined.FormatListBulleted,
        Icons.Filled.Add, // Ícone central
        Icons.Outlined.Settings,
        Icons.Outlined.Output
    )

    val rotations = listOf(-50f, -30f, 0f, 30f, 50f)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(35.dp, 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        icons.forEachIndexed { index, icon ->
            val isSelected = selectedIndex == index
            BottomNavigationItem(
                icon = icon,
                isSelected = isSelected,
                onClick = {
                    if (index == 2) {
                        isDialogOpen = true
                    } else {
                        onItemSelected(index)
                    }
                },
                isCentral = index == 2,
                rotationAngle = rotations[index]
            )
        }
    }

    if (isDialogOpen) {
        AddTransactionDialog(onDismiss = { isDialogOpen = false })
    }
}

@Composable
fun BottomNavigationItem(
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    isCentral: Boolean,
    rotationAngle: Float
) {
    val iconColor = if (isSelected) Color.White else Color.LightGray
    val backColor = if (isSelected) LightColor2 else Color.Transparent

    val rotation by animateFloatAsState(
        targetValue = if (isSelected) rotationAngle else 0f,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
    )

    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.2f else 0.8f,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
    )

    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isCentral) {
            Box(
                modifier = Modifier
                    .height(50.dp)
                    .width(65.dp)
                    .background(LightColor1, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = "Action Icon",
                    modifier = Modifier.size(30.dp),
                    tint = Color.White
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .size(25.dp)
                    .background(Color.Transparent),
                contentAlignment = Alignment.BottomCenter
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(25.dp)
                        .rotate(rotation)
                        .scale(scale)
                        .background(
                            color = backColor,
                            shape = RoundedCornerShape(20)
                        )
                )
                Icon(
                    imageVector = icon,
                    contentDescription = "Navigation Icon",
                    modifier = Modifier.size(25.dp),
                    tint = iconColor
                )
            }
        }
    }
}

@Composable
fun AddTransactionDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Adicionar Transação") },
        text = { Text("Aqui você pode adicionar uma nova transação.") },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Fechar")
            }
        }
    )
}