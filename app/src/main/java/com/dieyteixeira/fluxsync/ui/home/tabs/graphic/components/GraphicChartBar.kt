package com.dieyteixeira.fluxsync.ui.home.tabs.graphic.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dieyteixeira.fluxsync.app.di.model.Grafico
import com.dieyteixeira.fluxsync.app.theme.ColorBackground
import com.dieyteixeira.fluxsync.app.theme.ColorFontesDark
import com.dieyteixeira.fluxsync.app.theme.ColorFontesLight
import com.dieyteixeira.fluxsync.app.theme.ColorGrayMedium
import kotlinx.coroutines.delay

@Composable
fun GraphicBar(
    data: List<Grafico>,
    mes: String,
    ano: Int
) {
    if (data.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Sem dados para exibir",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }
        return
    }

    val displayData = data.take(10)

    val maxAmount = data.maxOfOrNull { it.valor } ?: 0.0

    var animationPlayed by remember { mutableStateOf(false) }

    LaunchedEffect(mes, ano) {
        animationPlayed = false
        delay(100)
        animationPlayed = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .heightIn(min = 160.dp)
            .padding(bottom = 5.dp),
        verticalArrangement = Arrangement.Center
    ) {

        displayData.forEach { category ->
            val animatedProgress by animateFloatAsState(
                targetValue = if (animationPlayed) (category.valor / maxAmount).toFloat() else 0f,
                animationSpec = tween(if (animationPlayed) 800 else 0, easing = LinearOutSlowInEasing)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(25.dp)
                        .clip(RoundedCornerShape(5.dp)),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Canvas(modifier = Modifier.matchParentSize()) {
                        val lineSpacing = 5.dp.toPx()
                        val lineColor = Color.Gray.copy(alpha = 0.2f)

                        for (i in 0..(size.width + size.height).toInt() step lineSpacing.toInt()) {
                            drawLine(
                                color = lineColor,
                                start = Offset(i.toFloat(), 0f),
                                end = Offset(0f, i.toFloat()),
                                strokeWidth = 2f
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(animatedProgress)
                            .height(25.dp)
                            .background(category.color, RoundedCornerShape(5.dp))
                    )
                }
            }
        }
    }

    GraphicDetails(
        data = displayData,
        mes = mes,
        ano = ano
    )
}