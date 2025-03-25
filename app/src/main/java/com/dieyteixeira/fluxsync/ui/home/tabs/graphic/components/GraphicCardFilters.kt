package com.dieyteixeira.fluxsync.ui.home.tabs.graphic.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.dieyteixeira.fluxsync.R
import com.dieyteixeira.fluxsync.app.components.ButtonPersonalFilled
import com.dieyteixeira.fluxsync.app.components.ButtonPersonalOutline
import com.dieyteixeira.fluxsync.app.components.CustomDialog
import com.dieyteixeira.fluxsync.app.components.CustomDialogButton
import com.dieyteixeira.fluxsync.app.theme.ColorCards
import com.dieyteixeira.fluxsync.app.theme.ColorFontesDark
import com.dieyteixeira.fluxsync.app.theme.ColorFontesLight
import com.dieyteixeira.fluxsync.app.theme.ColorGrayLight
import com.dieyteixeira.fluxsync.app.theme.ColorGrayMedium
import com.dieyteixeira.fluxsync.app.theme.ColorLine
import com.dieyteixeira.fluxsync.ui.home.viewmodel.HomeViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GraphicCardFilters(
    contasDisponiveis: List<String>,
    contasSelecionadas: List<String>,
    onSelecionarContas: (List<String>) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

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
                    text = "Filtro por contas",
                    style = MaterialTheme.typography.headlineMedium,
                    color = ColorFontesDark,
                    modifier = Modifier.padding(0.dp, 3.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.icon_filtro),
                    contentDescription = "Alternar filtro",
                    modifier = Modifier
                        .size(22.dp)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                        ) {
                            showDialog = true
                        },
                    colorFilter = ColorFilter.tint(ColorFontesLight)
                )
            }
        }
        Box(modifier = Modifier.fillMaxWidth(0.95f).height(1.dp).background(ColorLine).align(Alignment.CenterHorizontally))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalArrangement = Arrangement.Center
            ) {
                contasSelecionadas.forEach { conta ->
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .background(
                                color = MaterialTheme.colorScheme.surfaceContainerLow,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(10.dp, 7.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = conta,
                            style = MaterialTheme.typography.displayMedium,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }

    if (showDialog) {
        DialogFiltroContas(
            contasDisponiveis = contasDisponiveis,
            contasSelecionadas = contasSelecionadas,
            onSelecionarContas = onSelecionarContas,
            onDismiss = { showDialog = false }
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DialogFiltroContas(
    contasDisponiveis: List<String>,
    contasSelecionadas: List<String>,
    onSelecionarContas: (List<String>) -> Unit,
    onDismiss: () -> Unit
) {
    var selecionadas by remember { mutableStateOf(contasSelecionadas) }

    CustomDialog(
        onClickClose = onDismiss
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Filtro de contas",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.surfaceContainer
            )
            Spacer(modifier = Modifier.height(15.dp))
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalArrangement = Arrangement.Center
            ) {
                contasDisponiveis.forEach { conta ->
                    val selecionado = selecionadas.contains(conta)
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .background(
                                if (selecionado) MaterialTheme.colorScheme.surfaceContainerLow else ColorGrayLight,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clickable {
                                selecionadas = if (conta == "Todas") {
                                    listOf("Todas")
                                } else {
                                    if (selecionadas.contains("Todas")) {
                                        listOf(conta)
                                    } else {
                                        if (selecionado) selecionadas - conta else selecionadas + conta
                                    }
                                }
                            }
                            .padding(10.dp, 7.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = conta,
                            style = MaterialTheme.typography.displayMedium,
                            color = if (selecionado) Color.White else Color.Black
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                ButtonPersonalOutline(
                    onClick = onDismiss,
                    text = "Cancelar",
                    colorText = MaterialTheme.colorScheme.surfaceContainer,
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    height = 35.dp,
                    width = 120.dp
                )
                Spacer(modifier = Modifier.width(8.dp))
                ButtonPersonalFilled(
                    onClick = {
                        onSelecionarContas(selecionadas)
                        onDismiss()
                    },
                    text = "Salvar",
                    colorText = Color.White,
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    height = 35.dp,
                    width = 120.dp
                )
            }
        }
    }
}