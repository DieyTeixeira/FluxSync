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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.dieyteixeira.fluxsync.app.components.CustomKeyboard
import com.dieyteixeira.fluxsync.app.configs.UserPreferences
import com.dieyteixeira.fluxsync.app.theme.ColorBackground
import com.dieyteixeira.fluxsync.app.theme.LightColor1
import com.dieyteixeira.fluxsync.app.theme.LightColor3
import com.dieyteixeira.fluxsync.app.theme.LightColor5
import com.dieyteixeira.fluxsync.ui.home.tabs.chart.ChartTab
import com.dieyteixeira.fluxsync.ui.home.tabs.home.screen.HomeTabScreen
import com.dieyteixeira.fluxsync.ui.home.tabs.settings.SettingsTab
import com.dieyteixeira.fluxsync.ui.home.tabs.transaction.TransactionTab
import com.dieyteixeira.fluxsync.ui.login.viewmodel.LoginViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomePrincipalScreen(
    selectedIndex: Int,
    viewModel: LoginViewModel,
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
            NavigationBarItems.entries[targetState].screen(viewModel, userPreferences, onSignOutClick)
        }
    }
}

enum class NavigationBarItems(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val screen: @Composable (LoginViewModel, UserPreferences, () -> Unit) -> Unit,
) {
    Home(
        selectedIcon = Icons.Outlined.Home,
        unselectedIcon = Icons.Filled.Home,
        screen = { viewModel, userPreferences, onSignOutClick ->
            HomeTabScreen(viewModel, userPreferences, onSignOutClick)
        }
    ),
    Transaction(
        selectedIcon = Icons.Outlined.Description,
        unselectedIcon = Icons.Filled.Description,
        screen = { _, _, _ -> TransactionTab() }
    ),
    Add(
        selectedIcon = Icons.Outlined.Add,
        unselectedIcon = Icons.Filled.Add,
        screen = { _, _, _ -> }
    ),
    Chart(
        selectedIcon = Icons.Outlined.InsertChart,
        unselectedIcon = Icons.Filled.InsertChart,
        screen = { _, _, _ -> ChartTab() }
    ),
    Settings(
        selectedIcon = Icons.Outlined.Settings,
        unselectedIcon = Icons.Filled.Settings,
        screen = { _, _, _ -> SettingsTab() }
    )
}