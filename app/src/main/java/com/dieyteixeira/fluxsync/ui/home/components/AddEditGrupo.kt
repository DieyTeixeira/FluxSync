package com.dieyteixeira.fluxsync.ui.home.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dieyteixeira.fluxsync.R
import com.dieyteixeira.fluxsync.app.components.ButtonPersonalFilled
import com.dieyteixeira.fluxsync.app.components.CustomDialog
import com.dieyteixeira.fluxsync.app.components.TextInput
import com.dieyteixeira.fluxsync.app.di.model.Prioridade
import com.dieyteixeira.fluxsync.app.di.model.listColorsCategoria
import com.dieyteixeira.fluxsync.app.di.replace.colorToStringCategoria
import com.dieyteixeira.fluxsync.app.theme.ColorBackground
import com.dieyteixeira.fluxsync.ui.home.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

@Composable
fun AddGrupoForm(
    homeViewModel: HomeViewModel,
    onClose: () -> Unit
) {

    var descricao by remember { mutableStateOf("") }
    var color by remember { mutableStateOf(Color.LightGray) }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var showDialogPicker by remember { mutableStateOf(false) }
    var colorPickerSelected by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onClose()
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_seta_baixo),
                contentDescription = "Icon Action",
                modifier = Modifier.size(25.dp),
                colorFilter = ColorFilter.tint(Color.Gray)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(MaterialTheme.colorScheme.surfaceContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Adicionar Categoria",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)
                            .border(
                                width = 1.5.dp,
                                color = Color.LightGray,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),
                            horizontalArrangement = Arrangement.spacedBy(20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Box com a cor
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                            ) {
                                TextInput(
                                    textValue = descricao,
                                    onValueChange = { descricao = it },
                                    placeholder = "Descrição"
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(8), // Define 8 colunas
                        modifier = Modifier.fillMaxWidth(0.9f),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
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
                                        colorPickerSelected = false
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
                        item {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(shape = RoundedCornerShape(100))
                                    .clickable {
                                        showDialogPicker = true
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.image_picker),
                                    contentDescription = null,
                                    modifier = Modifier.size(32.dp)
                                )
                                Image(
                                    painter = painterResource(id = R.drawable.icon_verificar),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp),
                                    colorFilter = ColorFilter.tint(if (colorPickerSelected) Color.White else Color.Transparent)
                                )
                            }
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
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .background(
                            ColorBackground,
                            RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    ButtonPersonalFilled(
                        onClick = {
                            if (color != Color.LightGray && descricao.isNotEmpty()) {
                                homeViewModel.salvarPrioridade(
                                    color = colorToStringCategoria(color),
                                    descricao = descricao
                                )
                                homeViewModel.getPrioridades()
                                onClose()
                            } else {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = if (descricao.isEmpty()) "Preencha a descrição!"
                                        else if (color == Color.LightGray) "Selecione uma cor!"
                                        else "",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }
                        },
                        text = "Salvar",
                        colorText = Color.White,
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        height = 40.dp,
                        width = 100.dp
                    )
                }
            }
        }

        if (showDialogPicker) {
            CustomDialog(
                onClickClose = { showDialogPicker = false }
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Selecione uma Categoria",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.surfaceContainer
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    ColorPicker(
                        onSelectedColor = {
                            color = it
                            showDialogPicker = false
                            colorPickerSelected = true
                        }
                    )
                }
            }
        }
    }
}

@SuppressLint("MemberExtensionConflict")
@Composable
fun EditGrupoForm(
    homeViewModel: HomeViewModel,
    prioridade: Prioridade?,
    onClose: () -> Unit
) {

    var descricaoEditada by remember { mutableStateOf(prioridade?.descricao ?: "") }
    var colorEditada by remember { mutableStateOf(prioridade?.color ?: Color.LightGray) }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var showDialogPicker by remember { mutableStateOf(false) }
    var colorPickerSelected by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onClose()
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_seta_baixo),
                contentDescription = "Icon Action",
                modifier = Modifier.size(25.dp),
                colorFilter = ColorFilter.tint(Color.Gray)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(MaterialTheme.colorScheme.surfaceContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Editar Categoria",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)
                            .border(
                                width = 1.5.dp,
                                color = Color.LightGray,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),
                            horizontalArrangement = Arrangement.spacedBy(20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Box com a cor
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                            ) {
                                TextInput(
                                    textValue = descricaoEditada,
                                    onValueChange = { descricaoEditada = it },
                                    placeholder = "Descrição"
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(8), // Define 8 colunas
                        modifier = Modifier.fillMaxWidth(0.9f),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(listColorsCategoria.size) { index ->
                            val selectedColor = listColorsCategoria[index] == colorEditada
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(
                                        color = listColorsCategoria[index],
                                        shape = RoundedCornerShape(100)
                                    )
                                    .clickable {
                                        colorEditada = listColorsCategoria[index]
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
                        item {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(shape = RoundedCornerShape(100))
                                    .clickable {
                                        showDialogPicker = true
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.image_picker),
                                    contentDescription = null,
                                    modifier = Modifier.size(32.dp)
                                )
                                Image(
                                    painter = painterResource(id = R.drawable.icon_verificar),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp),
                                    colorFilter = ColorFilter.tint(if (colorPickerSelected) Color.White else Color.Transparent)
                                )
                            }
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
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .background(
                            ColorBackground,
                            RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    ButtonPersonalFilled(
                        onClick = {
                            if (colorEditada != Color.LightGray && descricaoEditada.isNotEmpty()) {
                                prioridade?.let {
                                    homeViewModel.editarPrioridade(
                                        it.copy(
                                            color = colorEditada,
                                            descricao = descricaoEditada
                                        )
                                    )
                                }
                                homeViewModel.getPrioridades()
                                onClose()
                            } else {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = if (descricaoEditada.isEmpty()) "Preencha a descrição!"
                                        else if (colorEditada == Color.LightGray) "Selecione uma cor!"
                                        else "",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }
                        },
                        text = "Alterar",
                        colorText = Color.White,
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        height = 40.dp,
                        width = 100.dp
                    )
                }
            }
        }

        if (showDialogPicker) {
            CustomDialog(
                onClickClose = { showDialogPicker = false }
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Selecione uma Categoria",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.surfaceContainer
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    ColorPicker(
                        onSelectedColor = {
                            colorEditada = it
                            showDialogPicker = false
                            colorPickerSelected = true
                        }
                    )
                }
            }
        }
    }
}