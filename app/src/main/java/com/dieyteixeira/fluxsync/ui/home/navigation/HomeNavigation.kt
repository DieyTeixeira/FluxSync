package com.dieyteixeira.fluxsync.ui.home.navigation

import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dieyteixeira.fluxsync.Screen
import com.dieyteixeira.fluxsync.app.animations.enterTransition
import com.dieyteixeira.fluxsync.app.animations.exitTransition
import com.dieyteixeira.fluxsync.app.animations.popEnterTransition
import com.dieyteixeira.fluxsync.app.animations.popExitTransition
import com.dieyteixeira.fluxsync.app.repository.AuthRepository
import com.dieyteixeira.fluxsync.ui.home.screen.HomeScreen
import com.dieyteixeira.fluxsync.ui.login.viewmodel.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

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