package com.dieyteixeira.fluxsync.ui.home.tabs.home.screen

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dieyteixeira.fluxsync.R
import com.dieyteixeira.fluxsync.app.components.ButtonPersonalFilled
import com.dieyteixeira.fluxsync.app.components.ButtonPersonalOutline
import com.dieyteixeira.fluxsync.app.configs.UserPreferences
import com.dieyteixeira.fluxsync.app.theme.ColorFontesLight
import com.dieyteixeira.fluxsync.app.theme.LightColor1
import com.dieyteixeira.fluxsync.app.theme.LightColor4
import com.dieyteixeira.fluxsync.ui.home.tabs.home.components.CategoriasDialog
import com.dieyteixeira.fluxsync.ui.home.tabs.home.components.ContasDialog
import com.dieyteixeira.fluxsync.ui.home.tabs.home.components.EditCardsDialog
import com.dieyteixeira.fluxsync.ui.home.tabs.home.components.HomeCardAjusts
import com.dieyteixeira.fluxsync.ui.home.tabs.home.components.HomeCardCategorias
import com.dieyteixeira.fluxsync.ui.home.tabs.home.components.HomeCardHistorico
import com.dieyteixeira.fluxsync.ui.home.tabs.home.components.HomeCardNotifications
import com.dieyteixeira.fluxsync.ui.home.tabs.home.components.HomeCardSaldo
import com.dieyteixeira.fluxsync.ui.home.tabs.home.components.HomeTopBar
import com.dieyteixeira.fluxsync.ui.home.viewmodel.HomeViewModel
import com.dieyteixeira.fluxsync.ui.login.viewmodel.LoginViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeTabScreen(
    loginViewModel: LoginViewModel,
    homeViewModel: HomeViewModel,
    userPreferences: UserPreferences,
    onSignOutClick: () -> Unit
) {

    val interactionSource = remember { MutableInteractionSource() }
    val coroutineScope = rememberCoroutineScope()
    val preferences by userPreferences.userPreferences.collectAsState(
        initial = Pair(
            listOf(
                "Saldo",
                "Categorias",
                "Histórico",
                "Ajustes"
            ),
            mapOf(
                "Saldo" to true,
                "Categorias" to true,
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

    var mostrarContas by remember { mutableStateOf(false) }
    var mostrarCategorias by remember { mutableStateOf(false) }
    var showContas by remember { mutableStateOf(false) }
    var showCategorias by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 5.dp)
            .clickable(
                indication = null,
                interactionSource = interactionSource
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
                    HomeTopBar(
                        homeViewModel = homeViewModel
                    )
                    Spacer(modifier = Modifier.height(22.dp))
                    HomeCardNotifications(
                        onClick = {
                            coroutineScope.launch {
                                userPreferences.clearUserPreferences()
                                userPreferences.saveUserPreferences(
                                    listOf("Saldo", "Categorias", "Histórico", "Ajustes"),
                                    mapOf("Saldo" to true, "Categorias" to true, "Histórico" to true, "Ajustes" to true)
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
                "Saldo" -> HomeCardSaldo(
                    homeViewModel = homeViewModel,
                    isSaldoVisivel = isSaldoVisivel,
                    isMostrarButton = mostrarContas,
                    onVisibilityChange = { isSaldoVisivel = it },
                    onClickExibirConta = { mostrarContas = true },
                    onClickOcultarConta = { mostrarContas = false },
                    onClickContas = { showContas = true }
                )
                "Categorias" -> HomeCardCategorias(
                    homeViewModel = homeViewModel,
                    isMostrarButton = mostrarCategorias,
                    onClickExibirCategoria = { mostrarCategorias = true },
                    onClickOcultarCategoria = { mostrarCategorias = false },
                    onClickCategorias = { showCategorias = true }
                )
                "Histórico" -> HomeCardHistorico(
                    isSaldoVisivel = isSaldoVisivel
                )
                "Ajustes" -> HomeCardAjusts(
                    onEditItem = {
                        if (it == "Contas") {
                            coroutineScope.launch { homeViewModel.getContas() }
                            showContas = true
                        } else {
                            coroutineScope.launch { homeViewModel.getCategorias() }
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
                    loginViewModel.signOut()
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
            homeViewModel = homeViewModel,
            onClickClose = { showContas = false }
        )
    }
    if (showCategorias) {
        CategoriasDialog(
            homeViewModel = homeViewModel,
            onClickClose = { showCategorias = false }
        )
    }
}