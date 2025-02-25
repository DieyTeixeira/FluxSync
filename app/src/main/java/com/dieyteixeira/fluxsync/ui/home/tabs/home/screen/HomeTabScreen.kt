package com.dieyteixeira.fluxsync.ui.home.tabs.home.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieyteixeira.fluxsync.app.components.ButtonPersonalFilled
import com.dieyteixeira.fluxsync.app.components.ButtonPersonalOutline
import com.dieyteixeira.fluxsync.app.components.CustomDialog
import com.dieyteixeira.fluxsync.app.components.CustomDialogButton
import com.dieyteixeira.fluxsync.app.components.ReorderableItems
import com.dieyteixeira.fluxsync.app.components.move
import com.dieyteixeira.fluxsync.app.configs.UserPreferences
import com.dieyteixeira.fluxsync.app.theme.LightColor1
import com.dieyteixeira.fluxsync.app.theme.LightColor3
import com.dieyteixeira.fluxsync.app.theme.LightColor4
import com.dieyteixeira.fluxsync.ui.home.tabs.home.components.HomeCardAjusts
import com.dieyteixeira.fluxsync.ui.home.tabs.home.components.HomeCardHistorico
import com.dieyteixeira.fluxsync.ui.home.tabs.home.components.HomeCardNotifications
import com.dieyteixeira.fluxsync.ui.home.tabs.home.components.HomeCardSaldo
import com.dieyteixeira.fluxsync.ui.home.tabs.home.components.HomeTopBar
import com.dieyteixeira.fluxsync.ui.login.viewmodel.LoginViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeTabScreen(
    viewModel: LoginViewModel,
    userPreferences: UserPreferences,
    onSignOutClick: () -> Unit
) {

    val coroutineScope = rememberCoroutineScope()
    val preferences by userPreferences.userPreferences.collectAsState(
        initial = Pair(
            listOf(
                "Saldo",
                "Histórico",
                "Ajustes"
            ),
            mapOf(
                "Saldo" to true,
                "Histórico" to true,
                "Ajustes" to true
            )
        )
    )

    var isSaldoVisivel by remember { mutableStateOf(true) }
    var showEditDialog by remember { mutableStateOf(false) }
    val cards = preferences.first
    val enabledCards = preferences.second
    val visibleCards = cards.filter { enabledCards[it] == true }

    var showContas by remember { mutableStateOf(false) }
    var showCategorias by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 5.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .background(LightColor1)
                )
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Spacer(modifier = Modifier.height(35.dp))
                    HomeTopBar()
                    Spacer(modifier = Modifier.height(22.dp))
                    HomeCardNotifications(
                        onClick = {
                            coroutineScope.launch {
                                userPreferences.clearUserPreferences()
                                userPreferences.saveUserPreferences(
                                    listOf("Saldo", "Histórico", "Ajustes"),
                                    mapOf("Saldo" to true, "Histórico" to true, "Ajustes" to true)
                                )
                                delay(100)
                            }
                        }
                    )
                }
            }
        }
        itemsIndexed(visibleCards, key = { _, item -> item }) { _, item ->
            when (item) {
                "Saldo" -> HomeCardSaldo(isSaldoVisivel) { isSaldoVisivel = it }
                "Histórico" -> HomeCardHistorico(isSaldoVisivel)
                "Ajustes" -> HomeCardAjusts(
                    onEditItem = {
                        if (it == "Contas") {
                            showContas = true
                        } else {
                            showCategorias = true
                        }
                    }
                )
            }
        }
        item {
            ButtonPersonalOutline(
                onClick = { showEditDialog = true },
                text = "Editar Cards",
                colorText = LightColor4,
                color = LightColor4,
                height = 35.dp,
                width = 120.dp
            )
        }
        item {
            ButtonPersonalFilled(
                onClick = {
                    onSignOutClick()
                    viewModel.signOut()
                },
                text = "Sair",
                colorText = Color.White,
                color = LightColor4,
                height = 35.dp,
                width = 120.dp
            )
        }
        item {
            Spacer(modifier = Modifier.height(64.dp))
        }
    }
    if (showEditDialog) {
        EditCardsDialog(
            cards = cards,
            enabledCards = enabledCards,
            onSave = { newOrder, newEnabledCards ->
                coroutineScope.launch {
                    userPreferences.saveUserPreferences(newOrder, newEnabledCards)
                    delay(100)
                }
                showEditDialog = false
            },
            onDismiss = { showEditDialog = false }
        )
    }
    if (showContas) {
        ContasDialog(
            onClickClose = { showContas = false }
        )
    }
    if (showCategorias) {
        CategoriasDialog(
            onClickClose = { showCategorias = false }
        )
    }
}

@SuppressLint("MutableCollectionMutableState")
@Composable
fun EditCardsDialog(
    cards: List<String>,
    enabledCards: Map<String, Boolean>,
    onSave: (List<String>, Map<String, Boolean>) -> Unit,
    onDismiss: () -> Unit
) {
    val tempCards = remember { mutableStateListOf(*cards.toTypedArray()) }
    val tempEnabled = remember { mutableStateMapOf<String, Boolean>().apply { putAll(enabledCards) } }

    CustomDialogButton(
        textConfirm = "Salvar",
        colorTextConfirm = Color.White,
        colorConfirm = LightColor3,
        onClickConfirm = { onSave(tempCards.toList(), tempEnabled.toMap()) },
        textCancel = "Cancelar",
        colorTextCancel = LightColor3,
        colorCancel = LightColor3,
        onClickCancel = onDismiss
    ) {
        ReorderableItems(
            items = tempCards,
            tempEnabled = tempEnabled,
            colorCheck = LightColor3,
            onMove = { fromIndex, toIndex -> tempCards.move(fromIndex, toIndex)}
        )
    }
}

@Composable
fun ContasDialog(
    onClickClose: () -> Unit
) {
    CustomDialog(
        onClickClose = onClickClose
    ) {
        Text(
            text = "Contas",
            fontSize = 20.sp,
            color = LightColor3,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun CategoriasDialog(
    onClickClose: () -> Unit
) {
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
            Spacer(modifier = Modifier.height(25.dp))
            LazyColumn {
                items(100) {
                    Text(text = "Item $it")
                }
            }
        }
    }
}