package com.dieyteixeira.fluxsync.ui.home.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.InsertChart
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.InsertChart
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.dieyteixeira.fluxsync.app.configs.UserPreferences
import com.dieyteixeira.fluxsync.app.theme.ColorBackground
import com.dieyteixeira.fluxsync.ui.home.tabs.chart.ChartTab
import com.dieyteixeira.fluxsync.ui.home.tabs.home.screen.HomeTabScreen
import com.dieyteixeira.fluxsync.ui.home.tabs.settings.SettingsTab
import com.dieyteixeira.fluxsync.ui.home.tabs.transaction.TransactionTab
import com.dieyteixeira.fluxsync.ui.home.viewmodel.HomeViewModel
import com.dieyteixeira.fluxsync.ui.login.viewmodel.LoginViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomePrincipalScreen(
    selectedIndex: Int,
    loginViewModel: LoginViewModel,
    homeViewModel: HomeViewModel,
    userPreferences: UserPreferences,
    onSignOutClick: () -> Unit
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
                fadeIn(animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)) with
                        fadeOut(animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing))
            }
        ) { targetState ->
            NavigationBarItems.entries[targetState].screen(loginViewModel, homeViewModel, userPreferences, onSignOutClick)
        }
    }
}

enum class NavigationBarItems(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val screen: @Composable (LoginViewModel, HomeViewModel, UserPreferences, () -> Unit) -> Unit,
) {
    Home(
        selectedIcon = Icons.Outlined.Home,
        unselectedIcon = Icons.Filled.Home,
        screen = { loginViewModel, homeViewModel, userPreferences, onSignOutClick ->
            HomeTabScreen(loginViewModel, homeViewModel, userPreferences, onSignOutClick)
        }
    ),
    Transaction(
        selectedIcon = Icons.Outlined.Description,
        unselectedIcon = Icons.Filled.Description,
        screen = { _, _, _, _ -> TransactionTab() }
    ),
    Add(
        selectedIcon = Icons.Outlined.Add,
        unselectedIcon = Icons.Filled.Add,
        screen = { _, _, _, _ -> }
    ),
    Chart(
        selectedIcon = Icons.Outlined.InsertChart,
        unselectedIcon = Icons.Filled.InsertChart,
        screen = { _, _, _, _ -> ChartTab() }
    ),
    Settings(
        selectedIcon = Icons.Outlined.Settings,
        unselectedIcon = Icons.Filled.Settings,
        screen = { _, _, _, _ -> SettingsTab() }
    )
}