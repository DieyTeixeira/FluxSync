package com.dieyteixeira.fluxsync.ui.splash.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dieyteixeira.fluxsync.Screen
import com.dieyteixeira.fluxsync.app.animations.enterTransition
import com.dieyteixeira.fluxsync.app.animations.exitTransition
import com.dieyteixeira.fluxsync.app.animations.popEnterTransition
import com.dieyteixeira.fluxsync.app.animations.popExitTransition
import com.dieyteixeira.fluxsync.ui.splash.screen.SplashScreen

fun NavGraphBuilder.splashScreen() {
    composable(
        route = Screen.Splash.route,
        enterTransition = { enterTransition() },
        exitTransition = { exitTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() }
    ) {
        SplashScreen()
    }
}