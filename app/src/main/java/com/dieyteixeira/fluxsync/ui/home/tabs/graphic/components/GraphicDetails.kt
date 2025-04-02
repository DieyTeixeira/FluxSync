package com.dieyteixeira.fluxsync.ui.home.tabs.graphic.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dieyteixeira.fluxsync.app.components.IconCategoria
import com.dieyteixeira.fluxsync.app.di.model.Grafico
import com.dieyteixeira.fluxsync.app.theme.ColorFontesDark
import com.dieyteixeira.fluxsync.app.theme.ColorFontesLight
import com.dieyteixeira.fluxsync.app.theme.ColorGrayMedium
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun GraphicDetails(
    data: List<Grafico>,
    mes: String,
    ano: Int
) {

    var animationPlayed by remember { mutableStateOf(false) }

    LaunchedEffect(mes, ano) {
        animationPlayed = false
        delay(100)
        animationPlayed = true
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 7.dp)
    ) {
        val totalSum = data.sumOf { it.valor }

        data.forEachIndexed { index, details ->
            val percentual = (details.valor / totalSum) * 100

            val animScope = rememberCoroutineScope()

            val animatedProgress = remember { Animatable(0f) }
            val animatedProgress2 = remember { Animatable(0f) }

            LaunchedEffect(Unit) {
                animScope.launch {
                    animatedProgress.animateTo(
                        targetValue = if (animationPlayed) 1f else 0f,
                        animationSpec = tween(if (animationPlayed) 300 else 0, easing = LinearOutSlowInEasing)
                    )
                }

                animScope.launch {
                    delay(300)
                    animatedProgress2.animateTo(
                        targetValue = if (animationPlayed) 1f else 0f,
                        animationSpec = tween(if (animationPlayed) 300 else 0, easing = LinearOutSlowInEasing)
                    )
                }
            }

            Surface(
                modifier = Modifier
                    .padding(vertical = 5.dp),
                color = Color.Transparent
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.weight(3f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(35.dp)
                        ) {
                            IconCategoria(
                                color = details.color,
                                icon = details.icon
                            )
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(start = 10.dp, bottom = 1.dp),
                                text = details.nome,
                                style = MaterialTheme.typography.displayMedium,
                                color = ColorFontesDark
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(details.color)
                            )
                            Text(
                                modifier = Modifier
                                    .padding(start = 15.dp, top = 1.dp),
                                text = "R$ %.2f".format(details.valor),
                                style = MaterialTheme.typography.displaySmall,
                                color = ColorFontesLight
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .height(35.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = details.color,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .padding(8.dp, 5.dp),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = "%.2f%%".format(percentual),
                                style = MaterialTheme.typography.headlineSmall,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}