package com.dieyteixeira.fluxsync.ui.splash.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dieyteixeira.fluxsync.app.theme.ManageStatusBarIcons
import com.dieyteixeira.fluxsync.ui.splash.components.ChasingTwoDots
import kotlinx.coroutines.delay

@Composable
fun SplashScreen() {
    // Cor dos ícones da status bar
    ManageStatusBarIcons(isIconBlack = true)

    var loadingMessage by remember { mutableStateOf("Carregando...") }

    LaunchedEffect(Unit) {
        delay(5000)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surfaceContainer),
        contentAlignment = Alignment.Center
    ) {
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            ChasingTwoDots(color = MaterialTheme.colorScheme.surfaceContainerLowest)
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = loadingMessage,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
        }
    }
}