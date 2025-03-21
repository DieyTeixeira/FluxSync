package com.dieyteixeira.fluxsync

import android.Manifest
import android.app.AlarmManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.dieyteixeira.fluxsync.app.components.AlertFirebaseMensagem
import com.dieyteixeira.fluxsync.app.notification.disableBatteryOptimizations
import com.dieyteixeira.fluxsync.app.notification.scheduleDailyNotifications
import com.dieyteixeira.fluxsync.app.theme.FluxSyncTheme
import com.dieyteixeira.fluxsync.ui.home.navigation.homeScreen
import com.dieyteixeira.fluxsync.ui.home.viewmodel.HomeViewModel
import com.dieyteixeira.fluxsync.ui.login.navigation.loginScreen
import com.dieyteixeira.fluxsync.ui.login.viewmodel.LoginViewModel
import com.dieyteixeira.fluxsync.ui.splash.navigation.splashScreen
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import org.koin.androidx.viewmodel.ext.android.viewModel

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Home : Screen("home")
}

fun NavHostController.navigateToScreen(route: String, popUpToRoute: String? = null) {
    this.navigate(route) {
        popUpToRoute?.let {
            popUpTo(it) { inclusive = true }
        }
    }
}

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)

        enableEdgeToEdge()
        setContent {

            val loginViewModel: LoginViewModel by viewModel()
            val homeViewModel: HomeViewModel by viewModel()
            val fontSize by homeViewModel.adjustedFontSize.collectAsState()

            FluxSyncTheme(fontSize = fontSize) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1002)
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    if (!getSystemService(AlarmManager::class.java).canScheduleExactAlarms()) {
                        Log.e("NOTIFICACAO", "Usuário precisa permitir alarmes exatos nas configurações.")
                    }
                }

                val navController = rememberNavController()
                val transacoes = homeViewModel.transacoes.value

                disableBatteryOptimizations(this)
                scheduleDailyNotifications(this, homeViewModel, transacoes)

                LaunchedEffect(Unit) {
                    delay(2000)
                    val currentUser = FirebaseAuth.getInstance().currentUser
                    if (currentUser != null) {
                        navController.navigateToScreen("home", "splash")
                        homeViewModel.getAtualizar()
                    } else {
                        navController.navigateToScreen("login", "splash")
                    }
                }

                Box(modifier = Modifier.fillMaxSize()) {
                    val messageReturn by homeViewModel.message.collectAsState()
                    val tipoMessage by homeViewModel.tipoMessage.collectAsState()

                    NavHost(
                        navController = navController,
                        startDestination = "splash"
                    ) {
                        splashScreen()
                        loginScreen(
                            loginViewModel = loginViewModel,
                            onLoginSuccess = { navController.navigateToScreen("home", "splash") }
                        )
                        homeScreen(
                            loginViewModel = loginViewModel,
                            homeViewModel = homeViewModel,
                            onSignOut = { navController.navigateToScreen("login", "home") }
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .align(Alignment.BottomCenter),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        if (messageReturn != null && tipoMessage != "vinculo") {
                            AlertFirebaseMensagem(
                                message = messageReturn.toString(),
                                tipo = tipoMessage.toString(),
                                onClickCancel = { homeViewModel.clearMessage() }
                            )
                        }
                    }
                }
            }
        }
    }
}