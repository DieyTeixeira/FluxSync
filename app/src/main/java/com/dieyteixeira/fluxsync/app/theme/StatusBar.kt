package com.dieyteixeira.fluxsync.app.theme

import android.view.Window
import androidx.core.view.WindowInsetsControllerCompat
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalView

fun setStatusBarIconsColor(window: Window, isLight: Boolean) {
    val controller = WindowInsetsControllerCompat(window, window.decorView)
    controller.isAppearanceLightStatusBars = isLight
}

@Composable
fun ManageStatusBarIcons(isIconBlack: Boolean) {
    val window = (LocalView.current.context as ComponentActivity).window

    LaunchedEffect(isIconBlack) {
        setStatusBarIconsColor(window, isIconBlack)
    }
}