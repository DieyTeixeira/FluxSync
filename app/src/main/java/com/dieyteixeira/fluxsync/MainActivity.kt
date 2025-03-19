package com.dieyteixeira.fluxsync

import android.os.Build
import android.os.Bundle
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
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import java.util.*

fun scheduleDailyNotification(context: Context) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, com.dieyteixeira.fluxsync.app.notification.NotificationEnviar::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        1001,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    // Configurar o horário (20:30)
    val calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 22)
        set(Calendar.MINUTE, 5)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)

        // Se a hora já passou hoje, agenda para amanhã
        if (timeInMillis < System.currentTimeMillis()) {
            add(Calendar.DAY_OF_YEAR, 1)
        }
    }

    Log.d(TAG, "Agendando notificação para: ${calendar.time}")

    // Agendar notificação diária
    alarmManager.setInexactRepeating(
        AlarmManager.RTC_WAKEUP,
        calendar.timeInMillis,
        AlarmManager.INTERVAL_DAY,
        pendingIntent
    )

    Log.d(TAG, "Notificação diária agendada com sucesso")
}

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
            FluxSyncTheme {

                scheduleDailyNotification(this)

                val navController = rememberNavController()
                val loginViewModel: LoginViewModel by viewModel()
                val homeViewModel: HomeViewModel by viewModel()

                LaunchedEffect(Unit) {
                    delay(2000)
                    val currentUser = FirebaseAuth.getInstance().currentUser
                    if (currentUser != null) {
                        navController.navigateToScreen("home", "splash")
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