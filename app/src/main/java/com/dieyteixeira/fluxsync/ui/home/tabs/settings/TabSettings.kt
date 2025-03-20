package com.dieyteixeira.fluxsync.ui.home.tabs.settings

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SliderState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.dieyteixeira.fluxsync.R
import com.dieyteixeira.fluxsync.app.theme.ColorFontesDark
import com.dieyteixeira.fluxsync.app.theme.ColorFontesLight
import com.dieyteixeira.fluxsync.app.theme.ColorGrayMedium
import com.dieyteixeira.fluxsync.app.theme.LightColor1
import com.dieyteixeira.fluxsync.app.theme.LightColor3
import com.dieyteixeira.fluxsync.ui.home.viewmodel.HomeViewModel
import kotlin.math.roundToInt
import kotlin.random.Random

@SuppressLint("MissingPermission")
@Composable
fun SettingsTab(
    homeViewModel: HomeViewModel
) {

    val context = LocalContext.current
    val sliderPosition by homeViewModel.sliderPosition.collectAsState()
    val adjustedFontSize by homeViewModel.adjustedFontSize.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tamanho da Fonte",
                    style = MaterialTheme.typography.titleMedium,
                    color = ColorFontesDark,
                    fontSize = (14 + adjustedFontSize).sp
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "Aa",
                    style = MaterialTheme.typography.titleSmall,
                    color = ColorFontesDark,
                    fontSize = 14.sp
                )
                Text(
                    text = "Aa",
                    style = MaterialTheme.typography.titleSmall,
                    color = ColorFontesDark,
                    fontSize = 22.sp
                )
            }
            Slider(
                value = sliderPosition,
                onValueChange = { homeViewModel.setSliderPosition(it) },
                valueRange = 0f..7f,
                steps = 6,
                modifier = Modifier
                    .semantics { contentDescription = "Controle de tamanho da fonte" },
                colors = SliderDefaults.colors(
                    thumbColor = LightColor3,
                    activeTrackColor = LightColor1,
                    inactiveTrackColor = ColorFontesLight
                )
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                showTestNotification(context)
            }
        ) {
            Text(
                text = "Show basic notification"
            )
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

@Preview
@Composable
fun StepsSliderSample() {
    var sliderPosition by remember { mutableStateOf(10f) }
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(text = sliderPosition.roundToInt().toString())
        Slider(
            modifier = Modifier.semantics { contentDescription = "Localized Description" },
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            valueRange = 0f..100f,
            onValueChangeFinished = {
            },
            steps = 6
        )
    }
}

@Preview
@Composable
private fun SliderTest() {
    var sliderPosition by remember { mutableStateOf(7f) }
    val ajustedFontSize by remember { mutableStateOf(21) }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Tamanho da Fonte",
                style = MaterialTheme.typography.titleMedium,
                color = ColorFontesDark,
                fontSize = (16 + ajustedFontSize).sp
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = "Aa",
                style = MaterialTheme.typography.titleSmall,
                color = ColorFontesDark,
                fontSize = 14.sp
            )
            Text(
                text = "Aa",
                style = MaterialTheme.typography.titleSmall,
                color = ColorFontesDark,
                fontSize = 22.sp
            )
        }
        Slider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            valueRange = 0f..8f,
            steps = 7,
            modifier = Modifier.semantics { contentDescription = "Controle de tamanho da fonte" },
            colors = SliderDefaults.colors(
                thumbColor = LightColor3,
                activeTrackColor = LightColor1,
                inactiveTrackColor = ColorGrayMedium
            )
        )
    }
}