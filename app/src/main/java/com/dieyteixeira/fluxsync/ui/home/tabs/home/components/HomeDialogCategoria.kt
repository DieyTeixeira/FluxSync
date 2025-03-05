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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieyteixeira.fluxsync.R
import com.dieyteixeira.fluxsync.app.components.ButtonPersonalFilled
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
import com.dieyteixeira.fluxsync.app.theme.ColorFontesDark
import com.dieyteixeira.fluxsync.app.theme.ColorGrayDark
import com.dieyteixeira.fluxsync.app.theme.LightColor3
import com.dieyteixeira.fluxsync.ui.home.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

@Composable
fun CategoriasDialog(
    homeViewModel: HomeViewModel,
    onClickClose: () -> Unit
) {
    var showAddCategorias by remember { mutableStateOf(false) }

    CustomDialog(
        onClickClose = onClickClose
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Categorias",
                fontSize = 20.sp,
                color = LightColor3,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(20.dp))
            ButtonPersonalMaxWidth(
                onClick = { showAddCategorias = true },
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
                    CategoriasList(categorias = homeViewModel.categorias.value[index])
                }
            }
        }
        if (showAddCategorias) {
            AddCategoriasDialog(
                homeViewModel = homeViewModel,
                onClickClose = { showAddCategorias = false }
            )
        }
    }
}

@Composable
fun CategoriasList(
    categorias: Categoria,
    onClickCategoria: (Categoria) -> Unit = {}
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
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun AddCategoriasDialog(
    homeViewModel: HomeViewModel,
    onClickClose: () -> Unit
) {
    var descricao by remember { mutableStateOf("") }
    var color by remember { mutableStateOf(Color.LightGray) }
    var icon by remember { mutableStateOf(R.drawable.icon_mais) }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    CustomDialog(
        onClickClose = onClickClose
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Adicionar categoria",
                fontSize = 20.sp,
                color = LightColor3,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconCategoria(
                    color = color,
                    icon = icon
                )
                TextInput(
                    textValue = descricao,
                    onValueChange = { descricao = it },
                    placeholder = "Descrição"
                )
            }
            Spacer(modifier = Modifier.height(25.dp))
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(0.94f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(listColorsCategoria.size) { index ->
                    val selectedColor = listColorsCategoria[index] == color
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(
                                color = listColorsCategoria[index],
                                shape = RoundedCornerShape(100)
                            )
                            .clickable {
                                color = listColorsCategoria[index]
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.icon_verificar),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            colorFilter = ColorFilter.tint(if (selectedColor) Color.Black else Color.Transparent)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth(0.95f),
                columns = GridCells.Fixed(7),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(listIconsCategorias.size) { index ->
                    val selectedIcon = listIconsCategorias[index] == icon
                    Box(
                        modifier = Modifier
                            .size(33.dp)
                            .border(
                                width = 1.5.dp,
                                color = if (selectedIcon) Color.Black else Color.Transparent,
                                shape = RoundedCornerShape(100)
                            )
                            .clickable {
                                icon = listIconsCategorias[index]
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = listIconsCategorias[index]),
                            contentDescription = null,
                            modifier = Modifier.size(18.dp),
                            colorFilter = ColorFilter.tint(if (selectedIcon) ColorFontesDark else ColorGrayDark)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            ) {
                SnackbarHost(hostState = snackbarHostState)
            }
            ButtonPersonalFilled(
                onClick = {
                    if (icon != R.drawable.icon_mais && color != Color.LightGray && descricao.isNotEmpty()) {
                        homeViewModel.salvarCategoria(
                            icon = iconToStringCategoria(icon),
                            color = colorToStringCategoria(color),
                            descricao = descricao
                        )
                        homeViewModel.getCategorias()
                        onClickClose()
                    } else {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(
                                message = if (descricao.isEmpty()) "Preencha a descrição!"
                                else if (icon == R.drawable.icon_mais) "Selecione um ícone!"
                                else if (color == Color.LightGray) "Selecione uma cor!"
                                else "",
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                },
                text = "Salvar",
                colorText = Color.White,
                color = LightColor3,
                height = 40.dp,
                width = 100.dp
            )
        }
    }
}