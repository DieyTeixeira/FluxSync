package com.dieyteixeira.fluxsync.ui.home.components

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dieyteixeira.fluxsync.R
import com.dieyteixeira.fluxsync.app.components.ButtonPersonalFilled
import com.dieyteixeira.fluxsync.app.components.CustomDialog
import com.dieyteixeira.fluxsync.app.components.CustomFieldEdit
import com.dieyteixeira.fluxsync.app.components.CustomKeyboard
import com.dieyteixeira.fluxsync.app.components.TextInput
import com.dieyteixeira.fluxsync.app.components.formatCurrencyInput
import com.dieyteixeira.fluxsync.app.components.removeLastDigit
import com.dieyteixeira.fluxsync.app.di.model.Conta
import com.dieyteixeira.fluxsync.app.di.model.listBancos
import com.dieyteixeira.fluxsync.app.di.model.listColorsConta
import com.dieyteixeira.fluxsync.app.di.replace.colorToStringConta
import com.dieyteixeira.fluxsync.app.di.replace.formatarValorEdit
import com.dieyteixeira.fluxsync.app.di.replace.iconToStringConta
import com.dieyteixeira.fluxsync.app.theme.BlackCont
import com.dieyteixeira.fluxsync.app.theme.ColorBackground
import com.dieyteixeira.fluxsync.app.theme.ColorFontesDark
import com.dieyteixeira.fluxsync.app.theme.ColorFontesLight
import com.dieyteixeira.fluxsync.app.theme.GrayCont
import com.dieyteixeira.fluxsync.ui.home.viewmodel.HomeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AddContaForm(
    homeViewModel: HomeViewModel,
    onClose: () -> Unit
) {

    var descricao by remember { mutableStateOf("") }
    var color by remember { mutableStateOf(Color.LightGray) }
    var icon by remember { mutableStateOf(R.drawable.icon_mais) }
    var saldo by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val listState = rememberLazyListState()

    val interactionSource = remember { MutableInteractionSource() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    var isKeyboardVisible by remember { mutableStateOf(false) }
    var isClickClose by remember { mutableStateOf(false) }

    var showDialogPicker by remember { mutableStateOf(false) }
    var colorPickerSelected by remember { mutableStateOf(false) }

    LaunchedEffect(isClickClose) {
        if (isClickClose) {
            delay(200)
            isKeyboardVisible = false
            isClickClose = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) { focusManager.clearFocus() }
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
                        text = "Adicionar Conta",
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
                            .border(
                                width = 1.5.dp,
                                color = Color.LightGray,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.width(5.dp))
                        Box(
                            modifier = Modifier
                                .size(35.dp)
                                .background(
                                    color = color,
                                    shape = RoundedCornerShape(100)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = icon),
                                contentDescription = "Ícone de banco",
                                modifier = Modifier
                                    .size(25.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Box(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            TextInput(
                                textValue = descricao,
                                onValueChange = { descricao = it },
                                placeholder = "Conta",
                                focusRequester = focusRequester,
                                onClickKeyboard = { isKeyboardVisible = false },
                                keyboardController = keyboardController
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        CustomFieldEdit(
                            value = saldo,
                            color = if (isKeyboardVisible) Color.LightGray else Color.Transparent,
                            onClickVisibility = {
                                focusManager.clearFocus()
                                keyboardController?.hide()
                                isKeyboardVisible = true
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(25.dp))
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth(0.85f),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        items(listColorsConta.size) { index ->
                            val selectedColor = listColorsConta[index] == color
                            val blackColor = listColorsConta[index] == BlackCont
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(
                                        color = listColorsConta[index],
                                        shape = RoundedCornerShape(100)
                                    )
                                    .clickable {
                                        color = listColorsConta[index]
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.icon_verificar),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp),
                                    colorFilter = ColorFilter.tint(
                                        if (blackColor && selectedColor) Color.White
                                        else if (selectedColor) Color.Black
                                        else Color.Transparent
                                    )
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
                    Spacer(modifier = Modifier.height(20.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.95f)
                            .heightIn(max = 350.dp)
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    color = ColorBackground.copy(alpha = 0.7f),
                                    shape = RoundedCornerShape(15.dp)
                                )
                                .padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(5.dp),
                            state = listState
                        ) {
                            items(listBancos.size) { index ->
                                val selectedIconConta = listBancos[index].icon == icon
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(end = 15.dp)
                                        .border(
                                            width = 1.5.dp,
                                            color = if (selectedIconConta) ColorFontesLight else Color.Transparent,
                                            shape = RoundedCornerShape(20.dp)
                                        )
                                        .padding(5.dp)
                                        .clickable {
                                            icon = listBancos[index].icon
                                            descricao =
                                                if (descricao == "") listBancos[index].name else ""
                                        },
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(30.dp)
                                            .background(
                                                color = GrayCont.copy(alpha = 0.5f),
                                                shape = RoundedCornerShape(100)
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Image(
                                            painter = painterResource(id = listBancos[index].icon),
                                            contentDescription = null,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = listBancos[index].name,
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = ColorFontesDark
                                    )
                                }
                            }
                        }
                        // Barra de rolagem personalizada
                        val firstVisibleIndex by remember { derivedStateOf { listState.firstVisibleItemIndex } }
                        val itemCount = listBancos.size
                        val progress =
                            if (itemCount > 0) firstVisibleIndex.toFloat() / (itemCount - 1) else 0f

                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .height(330.dp)
                                .width(8.dp)
                                .offset(x = (-10).dp)
                                .background(
                                    Color.LightGray.copy(alpha = 0.3f),
                                    shape = RoundedCornerShape(100)
                                )
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(8.dp)
                                    .height(40.dp)
                                    .offset(y = ((progress * 342).dp).coerceIn(0.dp, 342.dp))
                                    .background(ColorFontesLight, shape = RoundedCornerShape(100))
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
                            if (icon != R.drawable.icon_mais && color != Color.LightGray && descricao.isNotEmpty()) {
                                homeViewModel.salvarConta(
                                    icon = iconToStringConta(icon),
                                    color = colorToStringConta(color),
                                    descricao = descricao,
                                    saldo = saldo.replace(",", ".").toDoubleOrNull()
                                        ?: 0.0
                                )
                                homeViewModel.getContas()
                                onClose()
                            } else {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = if (descricao.isEmpty()) "Preencha a descrição!"
                                        else if (icon == R.drawable.icon_mais) "Selecione um ícone!"
                                        else if (color == Color.LightGray) "Selecione uma cor!"
                                        else if (saldo.isEmpty()) "Preencha o saldo!"
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

            if (isKeyboardVisible) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = ColorBackground,
                            shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp)
                        )
                        .align(Alignment.BottomCenter),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CustomKeyboard(
                        onClick = { digit -> saldo = formatCurrencyInput(saldo, digit) },
                        onClickClose = { isClickClose = true },
                        onClickBackspace = { saldo = removeLastDigit(saldo) }
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

@Composable
fun EditContaForm(
    homeViewModel: HomeViewModel,
    conta: Conta?,
    onClose: () -> Unit
) {

    var descricaoEditada by remember { mutableStateOf(conta?.descricao ?: "") }
    var colorEditada by remember { mutableStateOf(conta?.color ?: Color.LightGray) }
    var saldoEditada by remember { mutableStateOf(formatarValorEdit(conta?.saldo ?: 0.0)) }
    var iconEditada by remember { mutableStateOf(conta?.icon ?: R.drawable.icon_mais) }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val listState = rememberLazyListState()

    val interactionSource = remember { MutableInteractionSource() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    var isKeyboardVisible by remember { mutableStateOf(false) }
    var isClickClose by remember { mutableStateOf(false) }

    var showDialogPicker by remember { mutableStateOf(false) }
    var colorPickerSelected by remember { mutableStateOf(false) }

    LaunchedEffect(isClickClose) {
        if (isClickClose) {
            delay(200)
            isKeyboardVisible = false
            isClickClose = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) { focusManager.clearFocus() }
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
                        text = "Editar Conta",
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
                            .border(
                                width = 1.5.dp,
                                color = Color.LightGray,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.width(5.dp))
                        Box(
                            modifier = Modifier
                                .size(35.dp)
                                .background(
                                    color = colorEditada,
                                    shape = RoundedCornerShape(100)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = iconEditada),
                                contentDescription = "Ícone de banco",
                                modifier = Modifier
                                    .size(25.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Box(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            TextInput(
                                textValue = descricaoEditada,
                                onValueChange = { descricaoEditada = it },
                                placeholder = "Conta",
                                focusRequester = focusRequester,
                                onClickKeyboard = { isKeyboardVisible = false },
                                keyboardController = keyboardController
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        CustomFieldEdit(
                            value = saldoEditada,
                            color = if (isKeyboardVisible) Color.LightGray else Color.Transparent,
                            onClickVisibility = {
                                focusManager.clearFocus()
                                keyboardController?.hide()
                                isKeyboardVisible = true
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(25.dp))
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth(0.85f),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        items(listColorsConta.size) { index ->
                            val selectedColor = listColorsConta[index] == colorEditada
                            val blackColor = listColorsConta[index] == BlackCont
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(
                                        color = listColorsConta[index],
                                        shape = RoundedCornerShape(100)
                                    )
                                    .clickable {
                                        colorEditada = listColorsConta[index]
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.icon_verificar),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp),
                                    colorFilter = ColorFilter.tint(
                                        if (blackColor && selectedColor) Color.White
                                        else if (selectedColor) Color.Black
                                        else Color.Transparent
                                    )
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
                    Spacer(modifier = Modifier.height(20.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.95f)
                            .heightIn(max = 350.dp)
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    color = ColorBackground.copy(alpha = 0.7f),
                                    shape = RoundedCornerShape(15.dp)
                                )
                                .padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(5.dp),
                            state = listState
                        ) {
                            items(listBancos.size) { index ->
                                val selectedIconConta = listBancos[index].icon == iconEditada
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(end = 15.dp)
                                        .border(
                                            width = 1.5.dp,
                                            color = if (selectedIconConta) ColorFontesLight else Color.Transparent,
                                            shape = RoundedCornerShape(20.dp)
                                        )
                                        .padding(5.dp)
                                        .clickable {
                                            iconEditada = listBancos[index].icon
                                            descricaoEditada =
                                                if (descricaoEditada == "") listBancos[index].name else descricaoEditada
                                        },
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(30.dp)
                                            .background(
                                                color = GrayCont.copy(alpha = 0.5f),
                                                shape = RoundedCornerShape(100)
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Image(
                                            painter = painterResource(id = listBancos[index].icon),
                                            contentDescription = null,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = listBancos[index].name,
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = ColorFontesDark
                                    )
                                }
                            }
                        }
                        // Barra de rolagem personalizada
                        val firstVisibleIndex by remember { derivedStateOf { listState.firstVisibleItemIndex } }
                        val itemCount = listBancos.size
                        val progress =
                            if (itemCount > 0) firstVisibleIndex.toFloat() / (itemCount - 1) else 0f

                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .height(330.dp)
                                .width(8.dp)
                                .offset(x = (-10).dp)
                                .background(
                                    Color.LightGray.copy(alpha = 0.3f),
                                    shape = RoundedCornerShape(100)
                                )
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(8.dp)
                                    .height(40.dp)
                                    .offset(y = ((progress * 342).dp).coerceIn(0.dp, 342.dp))
                                    .background(ColorFontesLight, shape = RoundedCornerShape(100))
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
                            if (iconEditada != R.drawable.icon_mais && colorEditada != Color.LightGray && descricaoEditada.isNotEmpty()) {
                                conta?.let {
                                    homeViewModel.editarConta(
                                        it.copy(
                                            icon = iconEditada,
                                            color = colorEditada,
                                            descricao = descricaoEditada,
                                            saldo = saldoEditada.replace(",", ".").toDoubleOrNull()
                                                ?: 0.0
                                        )
                                    )
                                }
                                homeViewModel.getContas()
                                onClose()
                            } else {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = if (descricaoEditada.isEmpty()) "Preencha a descrição!"
                                        else if (iconEditada == R.drawable.icon_mais) "Selecione um ícone!"
                                        else if (colorEditada == Color.LightGray) "Selecione uma cor!"
                                        else if (saldoEditada.isEmpty()) "Preencha o saldo!"
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

            if (isKeyboardVisible) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = ColorBackground,
                            shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp)
                        )
                        .align(Alignment.BottomCenter),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CustomKeyboard(
                        onClick = { digit -> saldoEditada = formatCurrencyInput(saldoEditada, digit) },
                        onClickClose = { isClickClose = true },
                        onClickBackspace = { saldoEditada = removeLastDigit(saldoEditada) }
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