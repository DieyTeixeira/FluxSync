package com.dieyteixeira.fluxsync.ui.home.tabs.home.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieyteixeira.fluxsync.R
import com.dieyteixeira.fluxsync.app.theme.LightColor2
import com.dieyteixeira.fluxsync.app.theme.LightColor3
import com.dieyteixeira.fluxsync.app.theme.LightColor4
import com.dieyteixeira.fluxsync.ui.home.viewmodel.HomeViewModel
import java.util.Calendar

@Composable
fun HomeTopBar(
    homeViewModel: HomeViewModel
) {

    val fontSize by homeViewModel.adjustedFontSize.collectAsState()
    val interactionSource = remember { MutableInteractionSource() }
    var rotationAngle by remember { mutableStateOf(0f) }

    val rotation by animateFloatAsState(
        targetValue = rotationAngle,
        animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
    )

    fun saudacao(): String {
        val hora = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return when {
            hora in 5..11 -> "Bom dia,"
            hora in 12..17 -> "Boa tarde,"
            else -> "Boa noite,"
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 0.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .border(
                    width = 2.dp,
                    color = LightColor3,
                    shape = RoundedCornerShape(100)
                ),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(100)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_usuario),
                    contentDescription = "Bottom Bar Icon",
                    modifier = Modifier.size(28.dp),
                    colorFilter = ColorFilter.tint(LightColor3)
                )
            }
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(15.dp, 0.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = saudacao(),
                style = MaterialTheme.typography.titleSmall,
                fontSize = (16 + fontSize).sp,
                color = Color.White
            )
            Text(
                text = "Diey Teixeira",
                style = MaterialTheme.typography.titleMedium,
                fontSize = (20 + fontSize).sp,
                color = Color.White
            )
        }
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = LightColor4.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(16.dp)
                )
                .clickable(
                    indication = null,
                    interactionSource = interactionSource
                ) {
                    rotationAngle += 720f
                    homeViewModel.getAtualizar()
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_giro),
                contentDescription = "Bottom Bar Icon",
                modifier = Modifier
                    .size(22.dp)
                    .graphicsLayer(rotationZ = rotation),
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
    }
}