package com.dieyteixeira.fluxsync.ui.home.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dieyteixeira.fluxsync.Screen
import com.dieyteixeira.fluxsync.app.components.enterTransition
import com.dieyteixeira.fluxsync.app.components.exitTransition
import com.dieyteixeira.fluxsync.app.components.popEnterTransition
import com.dieyteixeira.fluxsync.app.components.popExitTransition
import com.dieyteixeira.fluxsync.ui.home.screen.HomeScreen
import com.dieyteixeira.fluxsync.ui.home.viewmodel.HomeViewModel
import com.dieyteixeira.fluxsync.ui.login.viewmodel.LoginViewModel

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.homeScreen(
    loginViewModel: LoginViewModel,
    homeViewModel: HomeViewModel,
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
            loginViewModel = loginViewModel,
            homeViewModel = homeViewModel,
            onSignOutClick = onSignOut
        )
    }
}