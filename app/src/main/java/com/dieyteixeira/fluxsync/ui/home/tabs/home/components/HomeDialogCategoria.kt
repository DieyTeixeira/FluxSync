package com.dieyteixeira.fluxsync.ui.home.tabs.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dieyteixeira.fluxsync.R
import com.dieyteixeira.fluxsync.app.components.ButtonPersonalIcon
import com.dieyteixeira.fluxsync.app.components.ButtonPersonalMaxWidth
import com.dieyteixeira.fluxsync.app.components.ButtonSinal
import com.dieyteixeira.fluxsync.app.components.CategoriasList
import com.dieyteixeira.fluxsync.app.components.CustomDialog
import com.dieyteixeira.fluxsync.app.components.IconCategoria
import com.dieyteixeira.fluxsync.app.di.model.Categoria
import com.dieyteixeira.fluxsync.app.di.model.Subcategoria
import com.dieyteixeira.fluxsync.app.theme.ColorBackground
import com.dieyteixeira.fluxsync.app.theme.ColorCards
import com.dieyteixeira.fluxsync.app.theme.ColorFontesLight
import com.dieyteixeira.fluxsync.app.theme.ColorGrayDark
import com.dieyteixeira.fluxsync.ui.home.components.AlertDialog
import com.dieyteixeira.fluxsync.ui.home.viewmodel.HomeViewModel

@Composable
fun CategoriasDialog(
    homeViewModel: HomeViewModel,
    onShowClick: () -> Unit,
    onAddClick: () -> Unit,
    onEditClick: () -> Unit,
    onClickClose: () -> Unit
) {

    val messageReturn by homeViewModel.message.collectAsState()
    val tipoMessage by homeViewModel.tipoMessage.collectAsState()
    val mostrarCategoriasMap = remember { mutableStateOf(mapOf<String, Boolean>()) }
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(tipoMessage) {
        if (tipoMessage == "vinculo") {
            showDialog = true
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
                text = "Categorias",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.surfaceContainer
            )
            Spacer(modifier = Modifier.height(20.dp))
            ButtonPersonalMaxWidth(
                onClick = {
                    onClickClose()
                    onAddClick()
                },
                text = "Adicionar categoria",
                colorText = MaterialTheme.colorScheme.surfaceContainer,
                colorBorder = MaterialTheme.colorScheme.surfaceContainer,
                height = 40.dp
            )
            Spacer(modifier = Modifier.height(15.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 350.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                val transacoesAgrupadas = homeViewModel.categorias.value.groupBy { it.tipo }

                transacoesAgrupadas.forEach { (tipoCategoria, categorias) ->
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp)
                                .height(40.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.surfaceContainer,
                                    shape = RoundedCornerShape(15.dp)
                                )
                                .padding(horizontal = 20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (tipoCategoria == "despesa") "Despesas" else "Receitas",
                                style = MaterialTheme.typography.headlineSmall,
                                color = Color.White
                            )
                        }
                    }
                    items(categorias) { categoria ->
                        val isMostrarButtons = mostrarCategoriasMap.value[categoria.id] ?: false

                        CategoriasList(
                            categorias = categoria,
                            isMostrarButtons = isMostrarButtons,
                            onClickCategoria = {
                                mostrarCategoriasMap.value =
                                    mostrarCategoriasMap.value.toMutableMap().apply {
                                        if (this[categoria.id] == true) {
                                            remove(categoria.id)
                                        } else {
                                            clear()
                                            put(categoria.id, true)
                                        }
                                    }
                            },
                            onClickEditar = {
                                onClickClose()
                                onEditClick()
                                homeViewModel.selectCategoria(categoria)
                            },
                            onClickDelete = {
                                homeViewModel.excluirCategoria(categoria.id)
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(25.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(35.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    ButtonPersonalMaxWidth(
                        onClick = {
                            onClickClose()
                            onShowClick()
                        },
                        text = "Subcategorias",
                        colorText = MaterialTheme.colorScheme.surfaceContainer,
                        colorBorder = MaterialTheme.colorScheme.surfaceContainer,
                        height = 35.dp,
                        icon = false
                    )
                }
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    ButtonPersonalMaxWidth(
                        onClick = {

                        },
                        text = "Grupos",
                        colorText = MaterialTheme.colorScheme.surfaceContainer,
                        colorBorder = MaterialTheme.colorScheme.surfaceContainer,
                        height = 35.dp,
                        icon = false
                    )
                }
            }
        }
    }
    if (showDialog) {
        messageReturn?.let {
            AlertDialog(
                text = it,
                onClickClose = {
                    showDialog = false
                    homeViewModel.clearMessage()
                    mostrarCategoriasMap.value = mostrarCategoriasMap.value
                        .toMutableMap().apply { clear() }
                }
            )
        }
    }
}