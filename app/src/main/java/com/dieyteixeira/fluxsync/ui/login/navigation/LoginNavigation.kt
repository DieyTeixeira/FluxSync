package com.dieyteixeira.fluxsync.ui.login.navigation

import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dieyteixeira.fluxsync.Screen
import com.dieyteixeira.fluxsync.app.animations.enterTransition
import com.dieyteixeira.fluxsync.app.animations.exitTransition
import com.dieyteixeira.fluxsync.app.animations.popEnterTransition
import com.dieyteixeira.fluxsync.app.animations.popExitTransition
import com.dieyteixeira.fluxsync.ui.login.screen.LoginScreen
import com.dieyteixeira.fluxsync.ui.login.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

fun NavGraphBuilder.loginScreen(
    loginViewModel: LoginViewModel,
    onLoginSuccess: () -> Unit
) {
    composable(
        route = Screen.Login.route,
        enterTransition = { enterTransition() },
        exitTransition = { exitTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() }
    ) {

        val scope = rememberCoroutineScope()

        LoginScreen(
            loginViewModel = loginViewModel,
            onSignInClick = {
                scope.launch {
                    loginViewModel.signIn()
                }
            },
            onSignUpClick = {
                scope.launch {
                    loginViewModel.signUp()
                }
            },
            onLoginSuccess = onLoginSuccess
        )
    }
}