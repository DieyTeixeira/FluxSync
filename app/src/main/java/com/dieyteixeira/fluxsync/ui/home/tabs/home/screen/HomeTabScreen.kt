package com.dieyteixeira.fluxsync.ui.home.tabs.home.screen

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dieyteixeira.fluxsync.app.components.ButtonPersonalFilled
import com.dieyteixeira.fluxsync.app.components.ButtonPersonalOutline
import com.dieyteixeira.fluxsync.app.configs.UserPreferences
import com.dieyteixeira.fluxsync.ui.home.tabs.home.components.CategoriasDialog
import com.dieyteixeira.fluxsync.ui.home.tabs.home.components.ContasDialog
import com.dieyteixeira.fluxsync.ui.home.tabs.home.components.EditCardsDialog
import com.dieyteixeira.fluxsync.ui.home.tabs.home.components.HomeCardAjusts
import com.dieyteixeira.fluxsync.ui.home.tabs.home.components.HomeCardCategorias
import com.dieyteixeira.fluxsync.ui.home.tabs.home.components.HomeCardHistorico
import com.dieyteixeira.fluxsync.ui.home.tabs.home.components.HomeCardNotifications
import com.dieyteixeira.fluxsync.ui.home.tabs.home.components.HomeCardPrevisao
import com.dieyteixeira.fluxsync.ui.home.tabs.home.components.HomeCardSaldo
import com.dieyteixeira.fluxsync.ui.home.tabs.home.components.HomeTopBar
import com.dieyteixeira.fluxsync.ui.home.tabs.home.components.SubcategoriasDialog
import com.dieyteixeira.fluxsync.ui.home.viewmodel.HomeViewModel
import com.dieyteixeira.fluxsync.ui.login.viewmodel.LoginViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeTabScreen(
    loginViewModel: LoginViewModel,
    homeViewModel: HomeViewModel,
    userPreferences: UserPreferences,
    onShowClick: (String) -> Unit,
    onAddClick: (String) -> Unit,
    onEditClick: (String) -> Unit,
    onSignOutClick: () -> Unit,
    onClick: () -> Unit
) {

    val coroutineScope = rememberCoroutineScope()
    val preferences by userPreferences.userPreferences.collectAsState(
        initial = Pair(
            listOf(
                "Saldo",
                "Previsão",
                "Categorias",
                "Histórico"
            ),
            mapOf(
                "Saldo" to true,
                "Previsão" to true,
                "Categorias" to true,
                "Histórico" to true
            )
        )
    )

    var isSaldoVisivel by remember { mutableStateOf(true) }
    var showEditDialog by remember { mutableStateOf(false) }
    val cards = preferences.first
    val enabledCards = preferences.second
    val visibleCards = cards.filter { enabledCards[it] == true }

    var mostrarContas by remember { mutableStateOf(false) }
    var mostrarCategorias by remember { mutableStateOf(false) }
    var showContas by remember { mutableStateOf(false) }
    var showCategorias by remember { mutableStateOf(false) }
    var showSubcategorias by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 5.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                coroutineScope.launch {
                    mostrarContas = false
                    mostrarCategorias = false
                }
            },
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 220.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 160.dp)
                        .background(MaterialTheme.colorScheme.surfaceContainerLowest)
                )
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Spacer(modifier = Modifier.height(35.dp))
                    HomeTopBar(
                        homeViewModel = homeViewModel
                    )
                    Spacer(modifier = Modifier.height(22.dp))
                    HomeCardNotifications(
                        homeViewModel = homeViewModel,
                        transacoes = homeViewModel.transacoes.value,
                        onClick = {
                            onClick()
                        }
                    )
                }
            }
        }
        itemsIndexed(visibleCards, key = { _, item -> item }) { _, item ->
            when (item) {
                "Saldo" -> HomeCardSaldo(
                    homeViewModel = homeViewModel,
                    isSaldoVisivel = isSaldoVisivel,
                    isMostrarButton = mostrarContas,
                    onVisibilityChange = { isSaldoVisivel = it },
                    onClickExibirConta = { mostrarContas = true },
                    onClickOcultarConta = { mostrarContas = false },
                    onClickContas = { showContas = true }
                )
                "Previsão" -> HomeCardPrevisao(
                    homeViewModel = homeViewModel,
                    isSaldoVisivel = isSaldoVisivel
                )
                "Categorias" -> HomeCardCategorias(
                    homeViewModel = homeViewModel,
                    isMostrarButton = mostrarCategorias,
                    onClickExibirCategoria = { mostrarCategorias = true },
                    onClickOcultarCategoria = { mostrarCategorias = false },
                    onClickCategorias = { showCategorias = true }
                )
                "Histórico" -> HomeCardHistorico(
                    homeViewModel = homeViewModel,
                    isSaldoVisivel = isSaldoVisivel
                )
            }
        }
        item {
            ButtonPersonalOutline(
                onClick = { showEditDialog = true },
                text = "Editar Cards",
                colorText = MaterialTheme.colorScheme.surfaceContainerHigh,
                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                height = 35.dp,
                width = 120.dp
            )
        }
        item {
            ButtonPersonalFilled(
                onClick = {
                    onSignOutClick()
                    loginViewModel.signOut()
                },
                text = "Sair",
                colorText = Color.White,
                color = MaterialTheme.colorScheme.surfaceContainerHigh,
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
            homeViewModel = homeViewModel,
            onAddClick = { onAddClick("conta") },
            onEditClick = { onEditClick("conta") },
            onClickClose = { showContas = false }
        )
    }
    if (showCategorias) {
        CategoriasDialog(
            homeViewModel = homeViewModel,
            onShowClick = {
                onShowClick("subcategoria")
                showCategorias = false
            },
            onAddClick = { onAddClick("categoria") },
            onEditClick = { onEditClick("categoria") },
            onClickClose = { showCategorias = false }
        )
    }
    if (showSubcategorias) {
        SubcategoriasDialog(
            homeViewModel = homeViewModel,
            onAddClick = { onAddClick("subcategoria") },
            onEditClick = { onEditClick("subcategoria") },
            onClickClose = { showSubcategorias = false }
        )
    }
}