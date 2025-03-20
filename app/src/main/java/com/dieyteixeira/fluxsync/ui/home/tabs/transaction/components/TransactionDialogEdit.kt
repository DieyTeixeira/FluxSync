package com.dieyteixeira.fluxsync.ui.home.tabs.transaction.components

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieyteixeira.fluxsync.R
import com.dieyteixeira.fluxsync.app.components.CustomDialog
import com.dieyteixeira.fluxsync.app.components.CustomDialogButtonEdit
import com.dieyteixeira.fluxsync.app.components.CustomFieldIconEdit
import com.dieyteixeira.fluxsync.app.components.CustomKeyboardEdit
import com.dieyteixeira.fluxsync.app.components.formatCurrencyInput
import com.dieyteixeira.fluxsync.app.components.removeLastDigit
import com.dieyteixeira.fluxsync.app.components.textEditTransactionSalvar
import com.dieyteixeira.fluxsync.app.di.model.Categoria
import com.dieyteixeira.fluxsync.app.di.model.Conta
import com.dieyteixeira.fluxsync.app.di.model.Transacoes
import com.dieyteixeira.fluxsync.app.theme.ColorBackground
import com.dieyteixeira.fluxsync.app.theme.LightColor3
import com.dieyteixeira.fluxsync.ui.home.components.ConfirmDialog
import com.dieyteixeira.fluxsync.ui.home.tabs.home.components.CategoriasList
import com.dieyteixeira.fluxsync.ui.home.tabs.home.components.ContasList
import com.dieyteixeira.fluxsync.ui.home.viewmodel.HomeViewModel
import kotlinx.coroutines.delay
import java.text.NumberFormat
import java.util.Locale

@Composable
fun TransactionEditDialog(
    homeViewModel: HomeViewModel,
    transacao: Transacoes,
    contas: List<Conta>,
    categorias: List<Categoria>,
    onClickClose: () -> Unit
) {

    val interactionSource = remember { MutableInteractionSource() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    var isKeyboardVisible by remember { mutableStateOf(false) }
    var isClickClose by remember { mutableStateOf(false) }

    var valorEditado by remember { mutableStateOf(formatarValorEdit(transacao.valor)) }
    var descricaoEditada by remember { mutableStateOf(transacao.descricao) }
    var observacaoEditada by remember { mutableStateOf(transacao.observacao) }
    var contaEditada by remember { mutableStateOf(
        contas.firstOrNull { it.id == transacao.contaId } ?: contas.first()) }
    var categoriaEditada by remember { mutableStateOf(
        categorias.firstOrNull { it.id == transacao.categoriaId } ?: categorias.first())}

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

    CustomDialogButtonEdit(
        onClickClose = onClickClose,
        onClickButton = {
            focusManager.clearFocus()
            if (transacao.grupoId.isNotEmpty()) {
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
            }
        },
        textButton = "Salvar Alterações"
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    indication = null,
                    interactionSource = interactionSource
                ) {
                    focusManager.clearFocus()
                }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        indication = null,
                        interactionSource = interactionSource
                    ) { focusManager.clearFocus() },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Editar Transação",
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 20.sp,
                    color = LightColor3,
                    modifier = Modifier.clickable(
                        indication = null,
                        interactionSource = interactionSource
                    ) {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    }
                )
                Spacer(modifier = Modifier.height(15.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 360.dp)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
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
                            CustomFieldIconEdit(
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
                }
                Box(modifier = Modifier.fillMaxWidth().height(45.dp))
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
                    CustomKeyboardEdit(
                        onClick = { digit -> valorEditado = formatCurrencyInput(valorEditado, digit) },
                        onClickClose = { isClickClose = true },
                        onClickBackspace = { valorEditado = removeLastDigit(valorEditado) }
                    )
                }
            }
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
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 20.sp,
                    color = LightColor3
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
                            isMostrarButtons = false,
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
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 20.sp,
                    color = LightColor3
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

    if (salvarAjustes) {
        ConfirmDialog(
            text = textEditTransactionSalvar,
            onClickClose = {
                alterarTodas = false
                homeViewModel.editarTransacao(transacao.copy(
                    descricao = descricaoEditada,
                    valor = valorEditado.replace(",", ".").toDoubleOrNull() ?: 0.0,
                    categoriaId = categoriaEditada.id,
                    contaId = contaEditada.id,
                    observacao = observacaoEditada
                ), alterarTodas)
                salvarAjustes = false
            },
            onClickYes = {
                alterarTodas = true
                homeViewModel.editarTransacao(transacao.copy(
                    descricao = descricaoEditada,
                    valor = valorEditado.replace(",", ".").toDoubleOrNull() ?: 0.0,
                    categoriaId = categoriaEditada.id,
                    contaId = contaEditada.id,
                    observacao = observacaoEditada
                ), alterarTodas)
                salvarAjustes = false
            }
        )
    }
}

fun formatarValorEdit(valor: Double): String {
    val formato = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    return formato.format(valor).replace("R$", "").trim()
}