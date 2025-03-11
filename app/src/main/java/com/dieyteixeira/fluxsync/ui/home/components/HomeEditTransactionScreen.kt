package com.dieyteixeira.fluxsync.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieyteixeira.fluxsync.R
import com.dieyteixeira.fluxsync.app.components.ButtonPersonalFilled
import com.dieyteixeira.fluxsync.app.components.CustomDialog
import com.dieyteixeira.fluxsync.app.components.CustomFieldEdit
import com.dieyteixeira.fluxsync.app.components.CustomKeyboard
import com.dieyteixeira.fluxsync.app.components.CustomKeyboardEdit
import com.dieyteixeira.fluxsync.app.components.formatCurrencyInput
import com.dieyteixeira.fluxsync.app.components.removeLastDigit
import com.dieyteixeira.fluxsync.app.di.model.Categoria
import com.dieyteixeira.fluxsync.app.di.model.Conta
import com.dieyteixeira.fluxsync.app.di.model.Transacoes
import com.dieyteixeira.fluxsync.app.theme.ColorBackground
import com.dieyteixeira.fluxsync.app.theme.ColorNegative
import com.dieyteixeira.fluxsync.app.theme.ColorPositive
import com.dieyteixeira.fluxsync.app.theme.LightColor2
import com.dieyteixeira.fluxsync.app.theme.LightColor3
import com.dieyteixeira.fluxsync.ui.home.tabs.home.components.CategoriasList
import com.dieyteixeira.fluxsync.ui.home.tabs.home.components.ContasList
import com.dieyteixeira.fluxsync.ui.home.tabs.transaction.components.ConfirmDialog
import com.dieyteixeira.fluxsync.ui.home.tabs.transaction.components.TransactionAddFieldsInsert
import com.dieyteixeira.fluxsync.ui.home.tabs.transaction.components.TransactionAddFieldsTextLeanding
import com.dieyteixeira.fluxsync.ui.home.tabs.transaction.components.TransactionAddFieldsTextLongLeanding
import com.dieyteixeira.fluxsync.ui.home.tabs.transaction.components.formatarValorEdit
import com.dieyteixeira.fluxsync.ui.home.tabs.transaction.components.textSalvar
import com.dieyteixeira.fluxsync.ui.home.viewmodel.HomeViewModel
import kotlinx.coroutines.delay

@Composable
fun HomeEditTransactionScreen(
    homeViewModel: HomeViewModel,
    transacao: Transacoes?,
    contas: List<Conta>,
    categorias: List<Categoria>,
    onClose: () -> Unit
) {

    val interactionSource = remember { MutableInteractionSource() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    var isKeyboardVisible by remember { mutableStateOf(false) }
    var isClickClose by remember { mutableStateOf(false) }

    var valorEditado by remember { mutableStateOf(formatarValorEdit(transacao?.valor ?: 0.0)) }
    var descricaoEditada by remember { mutableStateOf(transacao?.descricao ?: "") }
    var observacaoEditada by remember { mutableStateOf(transacao?.observacao ?: "") }
    var contaEditada by remember {
        mutableStateOf(
            contas.firstOrNull { it.id == transacao!!.contaId } ?: contas.first())
    }
    var categoriaEditada by remember {
        mutableStateOf(
            categorias.firstOrNull { it.id == transacao!!.categoriaId } ?: categorias.first())
    }

    val typeTransaction = remember { mutableStateOf("receita") }
    var showCategoryDialog by remember { mutableStateOf(false) }
    var showAccountDialog by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf<Categoria?>(null) }
    var selectedAccount by remember { mutableStateOf<Conta?>(null) }

    var salvarAjustes by remember { mutableStateOf(false) }
    var alterarTodas by remember { mutableStateOf(false) }

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
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            if (typeTransaction.value == "despesa") ColorNegative else ColorPositive,
                        )
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Editar Transação",
                        fontSize = 26.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = if (typeTransaction.value == "receita") "Receita" else "Despesa",
                        fontSize = 22.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item { // DESCRIÇÃO
                        TransactionAddFieldsTextLeanding(
                            divider = false,
                            text = "Descrição",
                            textValue = descricaoEditada,
                            onValueChange = { descricaoEditada = it },
                            icon = R.drawable.icon_editar,
                            placeholder = "Adicionar a descrição",
                            maxLength = 20,
                            focusRequester = focusRequester,
                            onClickKeyboard = { isKeyboardVisible = false },
                            keyboardController = keyboardController
                        )
                    }
                    item { // VALOR
                        CustomFieldEdit(
                            divider = true,
                            text = "Valor",
                            value = valorEditado,
                            icon = R.drawable.icon_dinheiro,
                            color = if (isKeyboardVisible) Color.LightGray else Color.Transparent,
                            onClickVisibility = {
                                focusManager.clearFocus()
                                keyboardController?.hide()
                                isKeyboardVisible = true
                            }
                        )
                    }
                    item { // CONTA
                        TransactionAddFieldsInsert(
                            interactionSource = interactionSource,
                            divider = true,
                            text = if (typeTransaction.value == "receita") "Entrada em" else "Saída de",
                            textValue = contaEditada.descricao,
                            textSaldo = contaEditada.saldo,
                            color = contaEditada.color,
                            icon = contaEditada.icon,
                            onClick = {
                                focusManager.clearFocus()
                                isKeyboardVisible = false
                                showAccountDialog = true
                            }
                        )
                    }
                    item { // CATEGORIA
                        TransactionAddFieldsInsert(
                            interactionSource = interactionSource,
                            divider = true,
                            text = "Categoria",
                            textValue = categoriaEditada.descricao,
                            color = categoriaEditada.color,
                            icon = categoriaEditada.icon,
                            onClick = {
                                focusManager.clearFocus()
                                isKeyboardVisible = false
                                showCategoryDialog = true
                            }
                        )
                    }
                    item {
                        TransactionAddFieldsTextLongLeanding(
                            divider = true,
                            text = "Observação",
                            textValue = observacaoEditada,
                            onValueChange = { observacaoEditada = it },
                            icon = R.drawable.icon_comentario,
                            placeholder = "Adicionar alguma observação",
                            singleLine = false,
                            heightMin = 100.dp,
                            colorBorder = Color.LightGray,
                            maxLength = 150,
                            focusRequester = focusRequester,
                            onClickKeyboard = { isKeyboardVisible = false },
                            keyboardController = keyboardController
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .background(
                            ColorBackground,
                            RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    ButtonPersonalFilled(
                        onClick = {
                            focusManager.clearFocus()
                            if (transacao!!.grupoId.isNotEmpty()) {
                                salvarAjustes = true
                            } else {
                                alterarTodas = false
                                homeViewModel.editarTransacao(transacao.copy(
                                    descricao = descricaoEditada,
                                    valor = valorEditado.replace(",", ".").toDoubleOrNull() ?: 0.0,
                                    categoriaId = categoriaEditada.id,
                                    contaId = contaEditada.id,
                                    observacao = observacaoEditada
                                ), alterarTodas)
                                homeViewModel.getAtualizar()
                                onClose()
                            }
                        },
                        text = "Salvar Edição",
                        colorText = Color.White,
                        color = LightColor2,
                        height = 45.dp,
                        width = 190.dp
                    )
                }
            }

            // Diálogo para seleção de conta
            if (showAccountDialog) {
                CustomDialog(
                    onClickClose = { showAccountDialog = false }
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Selecione uma Conta",
                            fontSize = 20.sp,
                            color = LightColor3,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(homeViewModel.contas.value.size) { index ->
                                ContasList(
                                    contas = homeViewModel.contas.value[index],
                                    onClickConta = {
                                        showAccountDialog = false
                                        selectedAccount = it
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // Diálogo para seleção de categoria
            if (showCategoryDialog) {
                CustomDialog(
                    onClickClose = { showCategoryDialog = false }
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Selecione uma Categoria",
                            fontSize = 20.sp,
                            color = LightColor3,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(homeViewModel.categorias.value.size) { index ->
                                CategoriasList(
                                    categorias = homeViewModel.categorias.value[index],
                                    onClickCategoria = {
                                        showCategoryDialog = false
                                        selectedCategory = it
                                    }
                                )
                            }
                        }
                    }
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
                        onClick = { digit -> valorEditado = formatCurrencyInput(valorEditado, digit) },
                        onClickClose = { isClickClose = true },
                        onClickBackspace = { valorEditado = removeLastDigit(valorEditado) }
                    )
                }
            }

            if (salvarAjustes) {
                ConfirmDialog(
                    text = textSalvar,
                    onClickClose = {
                        alterarTodas = false
                        homeViewModel.editarTransacao(
                            transacao!!.copy(
                                descricao = descricaoEditada,
                                valor = valorEditado.replace(",", ".").toDoubleOrNull() ?: 0.0,
                                categoriaId = categoriaEditada.id,
                                contaId = contaEditada.id,
                                observacao = observacaoEditada
                            ), alterarTodas
                        )
                        homeViewModel.getAtualizar()
                        salvarAjustes = false
                        onClose()
                    },
                    onClickYes = {
                        alterarTodas = true
                        homeViewModel.editarTransacao(
                            transacao!!.copy(
                                descricao = descricaoEditada,
                                valor = valorEditado.replace(",", ".").toDoubleOrNull() ?: 0.0,
                                categoriaId = categoriaEditada.id,
                                contaId = contaEditada.id,
                                observacao = observacaoEditada
                            ), alterarTodas
                        )
                        homeViewModel.getAtualizar()
                        salvarAjustes = false
                        onClose()
                    }
                )
            }
        }
    }
}