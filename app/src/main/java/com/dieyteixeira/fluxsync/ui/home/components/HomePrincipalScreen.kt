package com.dieyteixeira.fluxsync.ui.home.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dieyteixeira.fluxsync.R
import com.dieyteixeira.fluxsync.app.configs.UserPreferences
import com.dieyteixeira.fluxsync.app.theme.ColorBackground
import com.dieyteixeira.fluxsync.ui.home.tabs.graphic.screen.GraphicTabScreen
import com.dieyteixeira.fluxsync.ui.home.tabs.home.screen.HomeTabScreen
import com.dieyteixeira.fluxsync.ui.home.tabs.settings.SettingsTab
import com.dieyteixeira.fluxsync.ui.home.tabs.transaction.screen.TransactionTab
import com.dieyteixeira.fluxsync.ui.home.viewmodel.HomeViewModel
import com.dieyteixeira.fluxsync.ui.login.viewmodel.LoginViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomePrincipalScreen(
    selectedIndex: Int,
    loginViewModel: LoginViewModel,
    homeViewModel: HomeViewModel,
    userPreferences: UserPreferences,
    onAddClick: (String) -> Unit,
    onEditClick: (String) -> Unit,
    onSignOutClick: () -> Unit,
    onClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorBackground),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AnimatedContent(
            targetState = selectedIndex,
            transitionSpec = {
                fadeIn(animationSpec = tween(durationMillis = 800, easing = LinearOutSlowInEasing)) with
                        fadeOut(animationSpec = tween(durationMillis = 800, easing = LinearOutSlowInEasing))
            }
        ) { targetState ->
            NavigationBarItems.entries[targetState].screen(
                loginViewModel,
                homeViewModel,
                userPreferences,
                onAddClick,
                onEditClick,
                onSignOutClick,
                onClick
            )
        }
    }
}

enum class NavigationBarItems(
    val icon: Int,
    val screen: @Composable (
        (LoginViewModel, HomeViewModel, UserPreferences, (String) -> Unit, (String) -> Unit, () -> Unit, () -> Unit) -> Unit),
) {
    Home(
        icon = R.drawable.icon_casa,
        screen = { loginViewModel, homeViewModel, userPreferences, onAddClick, onEditClick, onSignOutClick, onClick ->
            HomeTabScreen(
                loginViewModel, homeViewModel, userPreferences,
                onAddClick, onEditClick, onSignOutClick, onClick
            )
        }
    ),
    @RequiresApi(Build.VERSION_CODES.O)
    Transaction(
        icon = R.drawable.icon_documento,
        screen = { _, homeViewModel, _, _, onEditClick, _, _ ->
            TransactionTab(
                homeViewModel, onEditClick
            )
        }
    ),
    Add(
        icon = R.drawable.icon_mais,
        screen = { _, _, _, _, _, _, _ -> }
    ),
    Chart(
        icon = R.drawable.icon_estatisticas,
        screen = { _, homeViewModel, _, _, _, _, _ ->
            GraphicTabScreen(
                homeViewModel
            )
        }
    ),
    Settings(
        icon = R.drawable.icon_ferramenta,
        screen = { _, homeViewModel, _, _, _, _, _ ->
            SettingsTab(
                homeViewModel
            )
        }
    )
}