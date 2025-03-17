package com.dieyteixeira.fluxsync.ui.home.tabs.home.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieyteixeira.fluxsync.R
import com.dieyteixeira.fluxsync.app.components.ButtonPersonalMaxWidth
import com.dieyteixeira.fluxsync.app.components.IconCategoria
import com.dieyteixeira.fluxsync.app.di.model.Categoria
import com.dieyteixeira.fluxsync.app.di.model.Grafico
import com.dieyteixeira.fluxsync.app.theme.ColorCards
import com.dieyteixeira.fluxsync.app.theme.ColorFontesDark
import com.dieyteixeira.fluxsync.app.theme.ColorGrayLight
import com.dieyteixeira.fluxsync.app.theme.ColorLine
import com.dieyteixeira.fluxsync.app.theme.LightColor3
import com.dieyteixeira.fluxsync.ui.home.viewmodel.HomeViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HomeCardCategorias(
    homeViewModel: HomeViewModel,
    isMostrarButton: Boolean,
    onClickExibirCategoria: () -> Unit,
    onClickOcultarCategoria: () -> Unit,
    onClickCategorias: () -> Unit
) {

    val somaPorCategoria by homeViewModel.somaPorCategoria.collectAsState()
    var isOrdenacaoCrescente by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 0.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(10.dp),
                ambientColor = Color.Gray,
                spotColor = Color.Gray
            )
            .background(
                color = ColorCards,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp, 3.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Categorias",
                    color = ColorFontesDark,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(0.dp, 3.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.icon_classification),
                    contentDescription = if (isOrdenacaoCrescente) "Ordenar: Maior → Menor" else "Ordenar: Menor → Maior",
                    modifier = Modifier
                        .size(20.dp)
                        .padding(end = 5.dp)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                        ) {
                            isOrdenacaoCrescente = !isOrdenacaoCrescente
                        }
                )
            }
        }

        Box(modifier = Modifier.fillMaxWidth(0.95f).height(1.dp).background(ColorLine).align(Alignment.CenterHorizontally))

        val categoriasOrdenadas = homeViewModel.categorias.value
            .filter { categoria -> categoria.tipo == "despesa" && (somaPorCategoria[categoria.id]?.valor ?: 0.0) > 0.0 }
            .sortedBy { somaPorCategoria[it.id]?.valor ?: 0.0 }
            .let { if (!isOrdenacaoCrescente) it.reversed() else it }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 240.dp)
                .padding(10.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { onClickOcultarCategoria() },
                        onLongPress = { onClickExibirCategoria() }
                    )
                }
        ) {
            items(categoriasOrdenadas) { categoria ->
                CategoriasItem(
                    categorias = categoria,
                    data = somaPorCategoria
                )
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        if (isMostrarButton) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                contentAlignment = Alignment.Center
            ) {
                ButtonPersonalMaxWidth(
                    onClick = { onClickCategorias() },
                    text = "Gerenciar categorias",
                    colorText = LightColor3,
                    colorBorder = LightColor3,
                    height = 40.dp,
                    width = 0.6f,
                    icon = false
                )
            }
        }
    }
}

@Composable
fun CategoriasItem(
    categorias: Categoria,
    data: Map<String, Grafico>?,
) {

    if (data.isNullOrEmpty()) return

    val totalSum = data.values.sumOf { it.valor }.takeIf { it > 0 } ?: 1.0

    val grafico = data[categorias.id]
    val categoriaValor = grafico?.valor ?: 0.0
    val percentual = (categoriaValor / totalSum) * 100

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconCategoria(
                icon = categorias.icon,
                color = categorias.color
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = categorias.descricao,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }
        Row(
            modifier = Modifier
                .width(100.dp)
                .height(15.dp)
                .background(ColorGrayLight),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start // Alinha a barra e texto corretamente
        ) {
            val fillPercentage = (percentual / 100).coerceIn(0.0, 1.0).toFloat()

            Box(
                modifier = Modifier
                    .fillMaxWidth(fillPercentage)
                    .height(15.dp)
                    .background(LightColor3),
                contentAlignment = Alignment.CenterEnd // Alinha o texto dentro da barra
            ) {
                if (fillPercentage > 0.5f) { // Exibir texto branco apenas se a barra for grande o suficiente
                    Text(
                        text = "%.1f".format(percentual),
                        fontSize = 13.sp,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.width(5.dp))
            // Texto principal fora da barra, com cor dinâmica
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "%.1f".format(percentual),
                    fontSize = 13.sp,
                    color = if (fillPercentage > 0.5f) Color.White else LightColor3
                )
                Text(
                    text = "%",
                    fontSize = 10.sp,
                    color = if (fillPercentage > 0.5f) Color.White else LightColor3
                )
            }
        }
    }
}