package com.dieyteixeira.fluxsync.ui.home.tabs.settings

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.dieyteixeira.fluxsync.R
import kotlin.random.Random

@SuppressLint("MissingPermission")
@Composable
fun SettingsTab() {

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Settings"
        )

        Button(
            onClick = {
                showTestNotification(context)
            }
        ) {
            Text(text = "Show basic notification")
        }
    }
}

@RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
fun showTestNotification(context: Context) {
    val channelId = "test_channel"

    // Criar o canal de notificação se necessário (para Android 8+)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channelName = "Test Channel"
        val descriptionText = "Canal de notificações para testes"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelId, channelName, importance).apply {
            description = descriptionText
        }

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    // Criar a notificação
    val notification = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.icon_cubo) // Substitua por um ícone válido
        .setContentTitle("Notificação de Teste")
        .setContentText("Esta é uma notificação de teste acionada por um botão.")
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)
        .build()

    // Enviar a notificação
    NotificationManagerCompat.from(context).notify(Random.nextInt(), notification)
}