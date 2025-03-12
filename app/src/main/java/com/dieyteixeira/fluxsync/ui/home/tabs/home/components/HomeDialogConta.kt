package com.dieyteixeira.fluxsync.ui.home.tabs.home.components

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
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
import com.dieyteixeira.fluxsync.app.components.IconConta
import com.dieyteixeira.fluxsync.app.components.TextInput
import com.dieyteixeira.fluxsync.app.di.model.Conta
import com.dieyteixeira.fluxsync.app.di.model.listBancos
import com.dieyteixeira.fluxsync.app.di.model.listColorsConta
import com.dieyteixeira.fluxsync.app.di.replace.colorToStringConta
import com.dieyteixeira.fluxsync.app.di.replace.iconToStringConta
import com.dieyteixeira.fluxsync.app.theme.BlackCont
import com.dieyteixeira.fluxsync.app.theme.ColorBackground
import com.dieyteixeira.fluxsync.app.theme.ColorCards
import com.dieyteixeira.fluxsync.app.theme.ColorFontesDark
import com.dieyteixeira.fluxsync.app.theme.ColorFontesLight
import com.dieyteixeira.fluxsync.app.theme.ColorGrayDark
import com.dieyteixeira.fluxsync.app.theme.ColorNegative
import com.dieyteixeira.fluxsync.app.theme.ColorPositive
import com.dieyteixeira.fluxsync.app.theme.GrayCont
import com.dieyteixeira.fluxsync.app.theme.LightColor2
import com.dieyteixeira.fluxsync.app.theme.LightColor3
import com.dieyteixeira.fluxsync.ui.home.state.formatarValor
import com.dieyteixeira.fluxsync.ui.home.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

@SuppressLint("MutableCollectionMutableState", "StateFlowValueCalledInComposition")
@Composable
fun ContasDialog(
    homeViewModel: HomeViewModel,
    onAddClick: () -> Unit,
    onEditClick: () -> Unit,
    onClickClose: () -> Unit
) {
    var showAddContas by remember { mutableStateOf(false) }
    var showEditContas by remember { mutableStateOf(false) }
    val mostrarContasMap = remember { mutableStateOf(mapOf<String, Boolean>()) }

    CustomDialog(
        onClickClose = onClickClose
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Contas",
                fontSize = 20.sp,
                color = LightColor3,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(20.dp))
            ButtonPersonalMaxWidth(
                onClick = {
                    onClickClose()
                    onAddClick()
//                    showAddContas = true
                },
                text = "Adicionar conta",
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
                items(homeViewModel.contas.value.size) { index ->
                    val conta = homeViewModel.contas.value[index] // Agora pegamos pelo índice
                    val isMostrarButtons = mostrarContasMap.value[conta.id] ?: false

                    ContasList(
                        contas = conta,
                        isMostrarButtons = isMostrarButtons,
                        onClickConta = {
                            mostrarContasMap.value = mostrarContasMap.value.toMutableMap().apply {
                                if (this[conta.id] == true) {
                                    remove(conta.id)
                                } else {
                                    clear()
                                    put(conta.id, true)
                                }
                            }
                        },
                        onClickEditar = {
                            onClickClose()
                            onEditClick()
//                            showEditContas = true
                            homeViewModel.selectConta(conta)
                        }
                    )
                }
            }
        }
        if (showAddContas) {
            AddContasDialog(
                homeViewModel = homeViewModel,
                onClickClose = { showAddContas = false }
            )
        }
    }
}

@Composable
fun ContasList(
    contas: Conta,
    isMostrarButtons: Boolean = false,
    onClickConta: (Conta) -> Unit,
    onClickEditar: () -> Unit = {}
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(
                    color = ColorBackground.copy(alpha = 0.7f),
                    shape = RoundedCornerShape(15.dp)
                )
                .padding(horizontal = 15.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    onClickConta(contas)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconConta(
                icon = contas.icon,
                color = contas.color
            )
            Spacer(modifier = Modifier.width(15.dp))
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = contas.descricao,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
                Row {
                    Text(
                        text = "Saldo:  ",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = ColorFontesLight
                    )
                    Text(
                        text = formatarValor(contas.saldo),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (contas.saldo > 0) ColorPositive else ColorNegative
                    )
                }
            }
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

                    },
                    icon = R.drawable.icon_excluir,
                    color = LightColor2,
                    size = 35.dp
                )
            }
        }
    }
}

@Composable
fun AddContasDialog(
    homeViewModel: HomeViewModel,
    onClickClose: () -> Unit
) {

    var descricao by remember { mutableStateOf("") }
    var color by remember { mutableStateOf(Color.LightGray) }
    var icon by remember { mutableStateOf(R.drawable.icon_mais) }
    var saldo by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val listState = rememberLazyListState()

    CustomDialog(
        onClickClose = onClickClose
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Adicionar conta",
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
                Box(
                    modifier = Modifier
                        .size(30.dp)
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
                            .size(20.dp)
                    )
                }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    TextInput(
                        textValue = descricao,
                        onValueChange = { descricao = it },
                        placeholder = "Conta"
                    )
                    TextInput(
                        textValue = saldo.toString(),
                        onValueChange = { saldo = it },
                        placeholder = "R$ 0,00"
                    )
                }
            }
            Spacer(modifier = Modifier.height(25.dp))
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(0.94f),
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
                                else Color.Transparent)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .heightIn(max = 200.dp)
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
                                    descricao = if (descricao == "") listBancos[index].name else ""
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
                                fontSize = 18.sp,
                                color = ColorFontesDark
                            )
                        }
                    }
                }
                // Barra de rolagem personalizada
                val firstVisibleIndex by remember { derivedStateOf { listState.firstVisibleItemIndex } }
                val itemCount = listBancos.size
                val progress = if (itemCount > 0) firstVisibleIndex.toFloat() / (itemCount - 1) else 0f

                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .height(180.dp)
                        .width(8.dp)
                        .offset(x = (-10).dp)
                        .background(Color.LightGray.copy(alpha = 0.3f), shape = RoundedCornerShape(100))
                ) {
                    Box(
                        modifier = Modifier
                            .width(8.dp)
                            .height(25.dp)
                            .offset(y = ((progress * 170).dp).coerceIn(0.dp, 170.dp))
                            .background(ColorFontesLight, shape = RoundedCornerShape(100))
                    )
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
                        homeViewModel.salvarConta(
                            icon = iconToStringConta(icon),
                            color = colorToStringConta(color),
                            descricao = descricao,
                            saldo = saldo.replace(",", ".").toDoubleOrNull()
                                ?: 0.0
                        )
                        homeViewModel.getContas()
                        onClickClose()
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
                color = LightColor3,
                height = 40.dp,
                width = 100.dp
            )
        }
    }
}