package com.dieyteixeira.fluxsync.ui.home.tabs.home.components

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieyteixeira.fluxsync.R
import com.dieyteixeira.fluxsync.app.components.ButtonPersonalFilled
import com.dieyteixeira.fluxsync.app.components.ButtonPersonalIcon
import com.dieyteixeira.fluxsync.app.components.ButtonPersonalMaxWidth
import com.dieyteixeira.fluxsync.app.components.CustomDialog
import com.dieyteixeira.fluxsync.app.components.IconCategoria
import com.dieyteixeira.fluxsync.app.components.TextInput
import com.dieyteixeira.fluxsync.app.di.model.Categoria
import com.dieyteixeira.fluxsync.app.di.model.listColorsCategoria
import com.dieyteixeira.fluxsync.app.di.model.listIconsCategorias
import com.dieyteixeira.fluxsync.app.di.replace.colorToStringCategoria
import com.dieyteixeira.fluxsync.app.di.replace.iconToStringCategoria
import com.dieyteixeira.fluxsync.app.theme.ColorBackground
import com.dieyteixeira.fluxsync.app.theme.ColorCards
import com.dieyteixeira.fluxsync.app.theme.ColorFontesDark
import com.dieyteixeira.fluxsync.app.theme.ColorGrayDark
import com.dieyteixeira.fluxsync.app.theme.LightColor2
import com.dieyteixeira.fluxsync.app.theme.LightColor3
import com.dieyteixeira.fluxsync.ui.home.components.AlertDialog
import com.dieyteixeira.fluxsync.ui.home.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

@Composable
fun CategoriasDialog(
    homeViewModel: HomeViewModel,
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
                color = LightColor3
            )
            Spacer(modifier = Modifier.height(20.dp))
            ButtonPersonalMaxWidth(
                onClick = {
                    onClickClose()
                    onAddClick()
                },
                text = "Adicionar categoria",
                colorText = LightColor3,
                colorBorder = LightColor3,
                height = 40.dp
            )
            Spacer(modifier = Modifier.height(20.dp))
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(homeViewModel.categorias.value.size) { index ->
                    val categoria = homeViewModel.categorias.value[index]
                    val isMostrarButtons = mostrarCategoriasMap.value[categoria.id] ?: false

                    CategoriasList(
                        categorias = homeViewModel.categorias.value[index],
                        isMostrarButtons = isMostrarButtons,
                        onClickCategoria = {
                            mostrarCategoriasMap.value = mostrarCategoriasMap.value.toMutableMap().apply {
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

@Composable
fun CategoriasList(
    categorias: Categoria,
    isMostrarButtons: Boolean = false,
    onClickCategoria: (Categoria) -> Unit = {},
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
                    onClickCategoria(categorias)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconCategoria(
                color = categorias.color,
                icon = categorias.icon
            )
            Spacer(modifier = Modifier.width(15.dp))
            Text(
                text = categorias.descricao,
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
                    color = LightColor2,
                    size = 35.dp,
                    sizeIcon = 18.dp
                )
                ButtonPersonalIcon( // excluir
                    onClick = {
                        onClickDelete()
                    },
                    icon = R.drawable.icon_excluir,
                    color = LightColor2,
                    size = 35.dp
                )
            }
        }
    }
}