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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.dieyteixeira.fluxsync.R
import com.dieyteixeira.fluxsync.app.components.ButtonPersonalMaxWidth
import com.dieyteixeira.fluxsync.app.components.IconCategoria
import com.dieyteixeira.fluxsync.app.di.model.Categoria
import com.dieyteixeira.fluxsync.app.theme.ColorBackground
import com.dieyteixeira.fluxsync.app.theme.ColorCards
import com.dieyteixeira.fluxsync.app.theme.ColorFontesDark
import com.dieyteixeira.fluxsync.app.theme.ColorFontesLight
import com.dieyteixeira.fluxsync.app.theme.ColorLine
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

    val categorias = homeViewModel.categorias.value
    val transacoes = homeViewModel.transacoes.value

    var isOrdenacaoCrescente by remember { mutableStateOf(true) }

    val totalGeral = transacoes.filter { it.tipo == "despesa" && it.valor > 0 }.sumOf { it.valor }

    val transacoesPorCategoria = categorias.map { categoria ->
        val transacoesDaCategoria = transacoes.filter {
            it.categoriaId == categoria.id && it.tipo == "despesa" && it.valor > 0
        }
        val totalCategoria = transacoesDaCategoria.sumOf { it.valor }

        categoria to totalCategoria
    }.filter { it.second > 0 }

    val categoriasOrdenadas = if (isOrdenacaoCrescente) {
        transacoesPorCategoria.sortedBy { it.second }
    } else {
        transacoesPorCategoria.sortedByDescending { it.second }
    }

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
                    style = MaterialTheme.typography.headlineMedium,
                    color = ColorFontesDark,
                    modifier = Modifier.padding(0.dp, 3.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.icon_classification),
                    contentDescription = if (isOrdenacaoCrescente) "Ordenar: Maior → Menor" else "Ordenar: Menor → Maior",
                    modifier = Modifier
                        .size(22.dp)
                        .padding(end = 2.dp)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                        ) {
                            isOrdenacaoCrescente = !isOrdenacaoCrescente
                        },
                    colorFilter = ColorFilter.tint(ColorFontesLight)
                )
            }
        }
        Box(modifier = Modifier.fillMaxWidth(0.95f).height(1.dp).background(ColorLine).align(Alignment.CenterHorizontally))
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
            items(categoriasOrdenadas.size) { index ->
                val categoria = categoriasOrdenadas[index].first
                val total = categoriasOrdenadas[index].second
                val percentual = if (totalGeral > 0) (total / totalGeral) * 100 else 0.0

                CategoriasItem(
                    categoria = categoria,
                    total = total,
                    percentual = percentual
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
                    colorText = MaterialTheme.colorScheme.surfaceContainer,
                    colorBorder = MaterialTheme.colorScheme.surfaceContainer,
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
    categoria: Categoria,
    total: Double,
    percentual: Double
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconCategoria(
                icon = categoria.icon,
                color = categoria.color
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = categoria.descricao,
                style = MaterialTheme.typography.displayMedium
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "%.2f".format(percentual),
                style = MaterialTheme.typography.bodySmall,
                color = ColorFontesLight
            )
            Text(
                text = "%",
                style = MaterialTheme.typography.labelSmall,
                color = ColorFontesLight
            )
            Spacer(modifier = Modifier.width(5.dp))
            Box(
                modifier = Modifier
                    .height(15.dp)
                    .width(80.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Box(
                    modifier = Modifier
                        .height(5.dp)
                        .width(80.dp)
                        .background(ColorBackground, RoundedCornerShape(100))
                )
                Box(
                    modifier = Modifier
                        .height(15.dp)
                        .width((percentual / 100) * 80.dp)
                        .background(MaterialTheme.colorScheme.surfaceContainer, RoundedCornerShape(3.dp))
                )
            }
        }
    }
}