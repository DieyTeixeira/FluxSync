package com.dieyteixeira.fluxsync.ui.home.tabs.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Circle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieyteixeira.fluxsync.app.components.ButtonPersonalFilled
import com.dieyteixeira.fluxsync.app.components.ButtonPersonalMaxWidth
import com.dieyteixeira.fluxsync.app.components.CustomDialog
import com.dieyteixeira.fluxsync.app.components.IconPersonal
import com.dieyteixeira.fluxsync.app.components.TextInput
import com.dieyteixeira.fluxsync.app.di.model.Categoria
import com.dieyteixeira.fluxsync.app.di.model.listColors
import com.dieyteixeira.fluxsync.app.di.model.listIcons
import com.dieyteixeira.fluxsync.app.di.replace.colorToString
import com.dieyteixeira.fluxsync.app.di.replace.iconToString
import com.dieyteixeira.fluxsync.app.di.replace.stringToColor
import com.dieyteixeira.fluxsync.app.theme.ColorBackground
import com.dieyteixeira.fluxsync.app.theme.ColorFontesDark
import com.dieyteixeira.fluxsync.app.theme.ColorFontesLight
import com.dieyteixeira.fluxsync.app.theme.LightColor3
import com.dieyteixeira.fluxsync.ui.home.state.categorias
import com.dieyteixeira.fluxsync.ui.home.viewmodel.HomeViewModel

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
    categorias: Categoria
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(
                color = ColorBackground.copy(alpha = 0.7f),
                shape = RoundedCornerShape(15.dp)
            )
            .padding(horizontal = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconPersonal(
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
    var color by remember { mutableStateOf(Color.Transparent) }
    var icon by remember { mutableStateOf(Icons.Default.Circle) }

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
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (icon != Icons.Default.Circle) {
                    IconPersonal(
                        color = color,
                        icon = icon
                    )
                }
                TextInput(
                    textValue = descricao,
                    onValueChange = { descricao = it },
                    placeholder = "Descrição"
                )
            }
            Spacer(modifier = Modifier.height(25.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(9.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(listColors.size) { index ->
                    val selectedColor = listColors[index] == color
                    Box(
                        modifier = Modifier
                            .size(25.dp)
                            .background(
                                color = listColors[index],
                                shape = RoundedCornerShape(100)
                            )
                            .clickable {
                                color = listColors[index]
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = if (selectedColor) Color.White else Color.Transparent,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(8),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(listIcons.size) { index ->
                    val selectedIcon = listIcons[index] == icon
                    Box(
                        modifier = Modifier
                            .size(25.dp)
                            .clickable {
                                icon = listIcons[index]
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = listIcons[index],
                            contentDescription = null,
                            tint = if (selectedIcon) ColorFontesDark else ColorFontesLight,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            ButtonPersonalFilled(
                onClick = {
                    homeViewModel.salvarCategoria(
                        icon = iconToString(icon),
                        color = colorToString(color),
                        descricao = descricao
                    )
                    homeViewModel.getCategorias()
                    onClickClose()
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