package com.dieyteixeira.fluxsync.ui.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dieyteixeira.fluxsync.Screen
import com.dieyteixeira.fluxsync.app.components.enterTransition
import com.dieyteixeira.fluxsync.app.components.exitTransition
import com.dieyteixeira.fluxsync.app.components.popEnterTransition
import com.dieyteixeira.fluxsync.app.components.popExitTransition
import com.dieyteixeira.fluxsync.ui.home.screen.HomeScreen
import com.dieyteixeira.fluxsync.ui.login.viewmodel.LoginViewModel

fun NavGraphBuilder.homeScreen(
    loginViewModel: LoginViewModel,
    onSignOut: () -> Unit
) {
    composable(
        route = Screen.Home.route,
        enterTransition = { enterTransition() },
        exitTransition = { exitTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() }
    ) {

        HomeScreen(
            viewModel = loginViewModel,
            onSignOutClick = onSignOut
        )
    }
}