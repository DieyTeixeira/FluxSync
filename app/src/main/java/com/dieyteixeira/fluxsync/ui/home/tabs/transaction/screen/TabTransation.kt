package com.dieyteixeira.fluxsync.ui.home.tabs.transaction.screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieyteixeira.fluxsync.app.components.ButtonPersonalFilled
import com.dieyteixeira.fluxsync.app.components.SeletorAno
import com.dieyteixeira.fluxsync.app.components.anoAtual
import com.dieyteixeira.fluxsync.app.components.listarMeses
import com.dieyteixeira.fluxsync.app.theme.ColorBackground
import com.dieyteixeira.fluxsync.app.theme.ColorCards
import com.dieyteixeira.fluxsync.app.theme.ColorFontesDark
import com.dieyteixeira.fluxsync.app.theme.LightColor1
import com.dieyteixeira.fluxsync.ui.home.components.ButtonsIncDec
import com.dieyteixeira.fluxsync.ui.home.tabs.home.components.HomeCardNotifications
import com.dieyteixeira.fluxsync.ui.home.tabs.home.components.HomeTopBar
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar
import kotlin.math.abs

@Composable
fun TransactionTab() {
    var mostrarDialogo by remember { mutableStateOf(false) }
    var anoSelecionado by remember { mutableStateOf(Calendar.getInstance().get(Calendar.YEAR)) }
    val anoAtual = anoAtual()
    val anos = (anoAtual - 10..anoAtual + 10).toList()
    val anosComEspacos = listOf("", "") + anos + listOf("", "")

    val listState = rememberLazyListState(initialFirstVisibleItemIndex = anosComEspacos.indexOf(anoSelecionado))

    val anoCentralizado by remember {
        derivedStateOf {
            val middleIndex = listState.firstVisibleItemIndex + 2
            anosComEspacos.getOrNull(middleIndex) ?: anoSelecionado
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(LightColor1),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Fluxo de caixa",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 20.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                ButtonPersonalFilled(
                    onClick = { mostrarDialogo = true },
                    text = "$anoSelecionado",
                    colorText = Color.White,
                    color = LightColor1,
                    height = 25.dp,
                    width = 60.dp
                )
//                ButtonsIncDec(
//                    value = anoSelecionado,
//                    width = 60.dp,
//                    onClickMenos = {
//                        anoSelecionado =
//                            if (anoSelecionado > 1) anoSelecionado - 1 else 1
//                    },
//                    onClickMais = { anoSelecionado++ }
//                )
            }
        }
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(ColorCards),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(listarMeses(anoSelecionado)) { mes ->
                Box(
                    modifier = Modifier
                        .height(30.dp)
                        .width(100.dp)
                        .background(ColorBackground, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = mes,
                        color = ColorFontesDark,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 5.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {

            }
        }

        if (mostrarDialogo) {
            AlertDialog(
                onDismissRequest = { },
                confirmButton = { },
                title = { Text(text = "Selecionar Ano", fontSize = 18.sp) },
                text = {
                    Box(
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.6f)
                                .height(50.dp)
                                .background(Color.Gray.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                        )
                        LazyColumn(
                            state = listState,
                            modifier = Modifier.fillMaxWidth().padding(top = 5.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            itemsIndexed(anosComEspacos) { index, ano ->
                                val centerIndex = anosComEspacos.indexOf(anoCentralizado)
                                val distanceFromCenter = (index - centerIndex).toFloat()

                                val scale = 1.0f - (0.10f * abs(distanceFromCenter))
                                val alpha = 1f - (0.3f * abs(distanceFromCenter))
                                val rotationX = distanceFromCenter * -40f

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .graphicsLayer {
                                            this.scaleX = scale
                                            this.scaleY = scale
                                            this.alpha = alpha
                                            this.rotationX = rotationX
                                            this.cameraDistance = 8 * density
                                        }
                                        .clickable {
                                            anoSelecionado = anoCentralizado as Int
                                            mostrarDialogo = false
                                        }
                                        .padding(10.dp)
                                        .offset(y = 0.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "$ano",
                                        fontSize = if (ano == anoCentralizado) 26.sp else 24.sp,
                                        fontWeight = if (ano == anoCentralizado) FontWeight.Bold else FontWeight.Normal,
                                        color = if (ano == anoCentralizado) Color.Black else Color.Gray
                                    )
                                }
                            }
                        }
                    }
                }
            )
        }
    }
}