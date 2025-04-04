package com.dieyteixeira.fluxsync.ui.home.tabs.home.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import com.dieyteixeira.fluxsync.app.components.ContasList
import com.dieyteixeira.fluxsync.app.components.CustomDialog
import com.dieyteixeira.fluxsync.app.components.IconConta
import com.dieyteixeira.fluxsync.app.di.model.Conta
import com.dieyteixeira.fluxsync.app.theme.ColorBackground
import com.dieyteixeira.fluxsync.app.theme.ColorCards
import com.dieyteixeira.fluxsync.app.theme.ColorFontesMedium
import com.dieyteixeira.fluxsync.app.theme.ColorGrayDark
import com.dieyteixeira.fluxsync.app.theme.ColorNegative
import com.dieyteixeira.fluxsync.app.theme.ColorPositive
import com.dieyteixeira.fluxsync.ui.home.components.AlertDialog
import com.dieyteixeira.fluxsync.ui.home.state.formatarValor
import com.dieyteixeira.fluxsync.ui.home.viewmodel.HomeViewModel

@SuppressLint("MutableCollectionMutableState", "StateFlowValueCalledInComposition")
@Composable
fun ContasDialog(
    homeViewModel: HomeViewModel,
    onAddClick: () -> Unit,
    onEditClick: () -> Unit,
    onClickClose: () -> Unit
) {

    val messageReturn by homeViewModel.message.collectAsState()
    val tipoMessage by homeViewModel.tipoMessage.collectAsState()
    val mostrarContasMap = remember { mutableStateOf(mapOf<String, Boolean>()) }
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
                text = "Contas",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.surfaceContainer
            )
            Spacer(modifier = Modifier.height(20.dp))
            ButtonPersonalMaxWidth(
                onClick = {
                    onClickClose()
                    onAddClick()
                },
                text = "Adicionar conta",
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
                items(homeViewModel.contas.value.size) { index ->
                    val conta = homeViewModel.contas.value[index]
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
                            homeViewModel.selectConta(conta)
                        },
                        onClickDelete = {
                            homeViewModel.excluirConta(conta.id)
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
                    mostrarContasMap.value = mostrarContasMap.value
                        .toMutableMap().apply { clear() }
                }
            )
        }
    }
}