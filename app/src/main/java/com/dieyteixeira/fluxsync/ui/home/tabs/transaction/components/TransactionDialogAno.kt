package com.dieyteixeira.fluxsync.ui.home.tabs.transaction.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieyteixeira.fluxsync.app.components.CustomDialog
import com.dieyteixeira.fluxsync.app.components.anoAtual

@Composable
fun SelectAnoDialog(
    anoSelecionado: Int,
    onClickAno: (Int) -> Unit,
    onClickClose: () -> Unit
) {

    val anoAtual = anoAtual()
    val anos = (anoAtual - 10..anoAtual + 10).toList()
    val anosComEspacos = listOf("") + anos + listOf(" ")
    val lazyListStateAno = rememberLazyListState()
    val snapBehaviorAno = rememberSnapFlingBehavior(lazyListStateAno)
    val verticalFadeAno = Brush.verticalGradient(
        0f to Color.Transparent,
        0.35f to Color.Gray,
        0.5f to Color.Gray,
        0.5f to Color.Gray,
        0.65f to Color.Gray,
        1f to Color.Transparent
    )
    val anoCentral by remember {
        derivedStateOf {
            val middleIndex = if (lazyListStateAno.firstVisibleItemScrollOffset == 0) {
                lazyListStateAno.firstVisibleItemIndex + 1
            } else {
                lazyListStateAno.firstVisibleItemIndex + 2
            }
            anosComEspacos.getOrNull(middleIndex) ?: anoSelecionado
        }
    }

    LaunchedEffect(anoSelecionado) {
        val indexAnoSelecionado = anos.indexOf(anoSelecionado)
        if (indexAnoSelecionado != -1) {
            lazyListStateAno.scrollToItem(indexAnoSelecionado, -60)
        }
    }

    CustomDialog(
        onClickClose = onClickClose
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Selecionar Ano",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.surfaceContainer
            )
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(50.dp)
                        .background(MaterialTheme.colorScheme.surfaceContainerLow, RoundedCornerShape(12.dp))
                )
                LazyColumn(
                    state = lazyListStateAno,
                    flingBehavior = snapBehaviorAno,
                    modifier = Modifier
                        .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
                        .drawWithContent {
                            drawContent()
                            drawRect(brush = verticalFadeAno, blendMode = BlendMode.DstIn)
                        }
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(anosComEspacos) { ano ->
                        if (ano == "") {
                            Spacer(modifier = Modifier.height(23.dp))
                        }
                        Text(
                            text = ano.toString(),
                            style = if (ano == anoCentral) {
                                MaterialTheme.typography.headlineMedium.copy(
                                    fontSize = 30.sp,
                                    color = Color.White
                                )
                            } else {
                                MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = 20.sp,
                                    color = Color.Gray
                                )
                            },
                            modifier = Modifier
                                .height(50.dp)
                                .width(150.dp)
                                .wrapContentHeight()
                                .clickable {
                                    anoCentral.toString().toIntOrNull()?.let { novoAno ->
                                        onClickAno(novoAno)
                                        onClickClose()
                                    }
                                },
                            textAlign = TextAlign.Center
                        )
                        if (ano == " ") {
                            Spacer(modifier = Modifier.height(23.dp))
                        }
                    }
                }
            }
        }
    }
}