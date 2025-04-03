package com.dieyteixeira.fluxsync.ui.home.tabs.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import com.dieyteixeira.fluxsync.app.components.CustomDialog
import com.dieyteixeira.fluxsync.app.components.IconCategoria
import com.dieyteixeira.fluxsync.app.di.model.Categoria
import com.dieyteixeira.fluxsync.app.di.model.Subcategoria
import com.dieyteixeira.fluxsync.app.theme.ColorBackground
import com.dieyteixeira.fluxsync.app.theme.ColorCards
import com.dieyteixeira.fluxsync.app.theme.ColorGrayDark
import com.dieyteixeira.fluxsync.ui.home.components.AlertDialog
import com.dieyteixeira.fluxsync.ui.home.viewmodel.HomeViewModel

@Composable
fun SubcategoriasDialog(
    homeViewModel: HomeViewModel,
    onAddClick: () -> Unit,
    onEditClick: () -> Unit,
    onClickClose: () -> Unit
) {

    val messageReturn by homeViewModel.message.collectAsState()
    val tipoMessage by homeViewModel.tipoMessage.collectAsState()
    val mostrarSubcategoriasMap = remember { mutableStateOf(mapOf<String, Boolean>()) }
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
                text = "Subcategorias",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.surfaceContainer
            )
            Spacer(modifier = Modifier.height(20.dp))
            ButtonPersonalMaxWidth(
                onClick = {
                    onClickClose()
                    onAddClick()
                },
                text = "Adicionar subcategoria",
                colorText = MaterialTheme.colorScheme.surfaceContainer,
                colorBorder = MaterialTheme.colorScheme.surfaceContainer,
                height = 40.dp
            )
            Spacer(modifier = Modifier.height(20.dp))
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(homeViewModel.subcategorias.value.size) { index ->
                    val subcategoria = homeViewModel.subcategorias.value[index]
                    val isMostrarButtons = mostrarSubcategoriasMap.value[subcategoria.id] ?: false

                    SubcategoriasList(
                        subcategorias = homeViewModel.subcategorias.value[index],
                        isMostrarButtons = isMostrarButtons,
                        onClickSubcategoria = {
                            mostrarSubcategoriasMap.value = mostrarSubcategoriasMap.value.toMutableMap().apply {
                                if (this[subcategoria.id] == true) {
                                    remove(subcategoria.id)
                                } else {
                                    clear()
                                    put(subcategoria.id, true)
                                }
                            }
                        },
                        onClickEditar = {
                            onClickClose()
                            onEditClick()
                            homeViewModel.selectSubcategoria(subcategoria)
                        },
                        onClickDelete = {
                            homeViewModel.excluirSubcategoria(subcategoria.id)
                        }
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
                    mostrarSubcategoriasMap.value = mostrarSubcategoriasMap.value
                        .toMutableMap().apply { clear() }
                }
            )
        }
    }
}

@Composable
fun SubcategoriasList(
    subcategorias: Subcategoria,
    isMostrarButtons: Boolean = false,
    onClickSubcategoria: (Subcategoria) -> Unit = {},
    onClickEditar: () -> Unit = {},
    onClickDelete: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(
                    color = ColorBackground.copy(alpha = 0.7f),
                    shape = RoundedCornerShape(15.dp)
                )
                .padding(horizontal = 15.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    onClickSubcategoria(subcategorias)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconCategoria(
                color = subcategorias.color,
                icon = subcategorias.icon
            )
            Spacer(modifier = Modifier.width(15.dp))
            Text(
                text = subcategorias.descricao,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.weight(1f)
            )
        }
        if (isMostrarButtons) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            0f to Color.Transparent,
                            0.3f to Color.Transparent,
                            1f to ColorCards
                        ),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .border(
                        width = 1.dp,
                        brush = Brush.verticalGradient(
                            0f to Color.Transparent,
                            0.3f to Color.Transparent,
                            1f to ColorGrayDark
                        ),
                        shape = RoundedCornerShape(20.dp)
                    ),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ButtonPersonalIcon( // editar
                    onClick = {
                        onClickEditar()
                    },
                    icon = R.drawable.icon_editar,
                    color = MaterialTheme.colorScheme.surfaceContainerLow,
                    size = 35.dp,
                    sizeIcon = 18.dp
                )
                ButtonPersonalIcon( // excluir
                    onClick = {
                        onClickDelete()
                    },
                    icon = R.drawable.icon_excluir,
                    color = MaterialTheme.colorScheme.surfaceContainerLow,
                    size = 35.dp
                )
            }
        }
    }
}