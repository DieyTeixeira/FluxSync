package com.dieyteixeira.fluxsync.ui.home.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.dieyteixeira.fluxsync.app.theme.ColorBackground
import com.dieyteixeira.fluxsync.app.theme.LightColor1
import com.dieyteixeira.fluxsync.app.theme.LightColor2

@Composable
fun CustomBottomNavigation(
    selectedRoute: String,
    onItemSelected: (Screens) -> Unit
) {
    val items = listOf(
        Screens.Home,
        Screens.List,
        Screens.Add,
        Screens.Settings,
        Screens.Output
    )

    val navShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)

    var previousIndex by remember { mutableStateOf(items.indexOfFirst { it.route == selectedRoute }) }
    val currentIndex = items.indexOfFirst { it.route == selectedRoute }

    val transition = updateTransition(targetState = currentIndex, label = "Indicator Transition")

    // Controla a largura do círculo durante a animação
    val circleWidth by transition.animateDp(
        transitionSpec = { tween(durationMillis = 300, easing = FastOutSlowInEasing) },
        label = "Circle Width"
    ) { index ->
        if (index == previousIndex) 40.dp else 60.dp  // Se estiver mudando, alonga
    }

    // Controla a altura do círculo durante a animação
    val circleHeight by transition.animateDp(
        transitionSpec = { tween(durationMillis = 300, easing = FastOutSlowInEasing) },
        label = "Circle Height"
    ) { index ->
        if (index == previousIndex) 40.dp else 30.dp // Fica mais oval no movimento
    }

    LaunchedEffect(selectedRoute) {
        previousIndex = currentIndex
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(navShape)
            .background(ColorBackground)
            .padding(vertical = 18.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Círculo animado de seleção
        items.forEachIndexed { index, screen ->
            val isSelected = screen.route == selectedRoute

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(55.dp)
            ) {
                // Círculo animado de fundo
                if (isSelected) {
                    Box(
                        modifier = Modifier
                            .size(circleWidth, circleHeight)
                            .clip(CircleShape)
                            .background(LightColor2)
                    )
                }

                IconButton(
                    onClick = {
                        if (!isSelected) {
                            onItemSelected(screen)
                        }
                    }
                ) {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = screen.route,
                        tint = if (isSelected) LightColor1 else LightColor2.copy(alpha = 0.5f),
                        modifier = Modifier.size(if (isSelected) 30.dp else 26.dp)
                    )
                }
            }
        }
    }
}

sealed class Screens(val route: String, val icon: ImageVector) {
    object Home : Screens("home", Icons.Rounded.Home)
    object List : Screens("list", Icons.Rounded.FormatListBulleted)
    object Add : Screens("add", Icons.Rounded.Add)
    object Settings : Screens("settings", Icons.Rounded.Settings)
    object Output : Screens("output", Icons.Rounded.Output)
}