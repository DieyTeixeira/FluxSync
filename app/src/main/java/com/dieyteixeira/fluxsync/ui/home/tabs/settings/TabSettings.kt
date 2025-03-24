package com.dieyteixeira.fluxsync.ui.home.tabs.settings

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.dieyteixeira.fluxsync.R
import com.dieyteixeira.fluxsync.app.theme.BlueColor1
import com.dieyteixeira.fluxsync.app.theme.BlueColor2
import com.dieyteixeira.fluxsync.app.theme.BlueColor3
import com.dieyteixeira.fluxsync.app.theme.BlueColor4
import com.dieyteixeira.fluxsync.app.theme.BlueColor5
import com.dieyteixeira.fluxsync.app.theme.ColorFontesDark
import com.dieyteixeira.fluxsync.app.theme.ColorFontesLight
import com.dieyteixeira.fluxsync.app.theme.ColorGrayMedium
import com.dieyteixeira.fluxsync.app.theme.GrayColor1
import com.dieyteixeira.fluxsync.app.theme.GrayColor2
import com.dieyteixeira.fluxsync.app.theme.GrayColor3
import com.dieyteixeira.fluxsync.app.theme.GrayColor4
import com.dieyteixeira.fluxsync.app.theme.GrayColor5
import com.dieyteixeira.fluxsync.app.theme.GreenColor1
import com.dieyteixeira.fluxsync.app.theme.GreenColor2
import com.dieyteixeira.fluxsync.app.theme.GreenColor3
import com.dieyteixeira.fluxsync.app.theme.GreenColor4
import com.dieyteixeira.fluxsync.app.theme.GreenColor5
import com.dieyteixeira.fluxsync.app.theme.RedColor1
import com.dieyteixeira.fluxsync.app.theme.RedColor2
import com.dieyteixeira.fluxsync.app.theme.RedColor3
import com.dieyteixeira.fluxsync.app.theme.RedColor4
import com.dieyteixeira.fluxsync.app.theme.RedColor5
import com.dieyteixeira.fluxsync.app.theme.YellowColor1
import com.dieyteixeira.fluxsync.app.theme.YellowColor2
import com.dieyteixeira.fluxsync.app.theme.YellowColor3
import com.dieyteixeira.fluxsync.app.theme.YellowColor4
import com.dieyteixeira.fluxsync.app.theme.YellowColor5
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

    val selectedColor by homeViewModel.selectedColor.collectAsState()
    val colorOptions = listOf("GREEN", "BLUE", "RED", "YELLOW", "GRAY")

    val colorVariations = mapOf(
        "GREEN" to listOf(GreenColor2, GreenColor3, GreenColor4, GreenColor5),
        "BLUE" to listOf(BlueColor2, BlueColor3, BlueColor4, BlueColor5),
        "RED" to listOf(RedColor2, RedColor3, RedColor4, RedColor5),
        "YELLOW" to listOf(YellowColor2, YellowColor3, YellowColor4, YellowColor5),
        "GRAY" to listOf(GrayColor2, GrayColor3, GrayColor4, GrayColor5)
    )

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
                    style = MaterialTheme.typography.headlineSmall,
                    color = ColorFontesDark
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
                    fontSize = 19.sp
                )
            }
            Slider(
                value = sliderPosition,
                onValueChange = { homeViewModel.setSliderPosition(it) },
                valueRange = 0f..3f,
                steps = 2,
                modifier = Modifier
                    .semantics { contentDescription = "Controle de tamanho da fonte" },
                colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colorScheme.surfaceContainer,
                    activeTrackColor = MaterialTheme.colorScheme.surfaceContainerLow,
                    inactiveTrackColor = ColorGrayMedium
                )
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Cor do Tema",
                style = MaterialTheme.typography.headlineSmall,
                color = ColorFontesDark
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                colorOptions.forEach { color ->
                    val colorValue = when (color) {
                        "GREEN" -> GreenColor1
                        "BLUE" -> BlueColor1
                        "RED" -> RedColor1
                        "YELLOW" -> YellowColor1
                        "GRAY" -> GrayColor1
                        else -> Color.Transparent
                    }

                    Column {
                        Box(
                            modifier = Modifier
                                .height(40.dp)
                                .width(60.dp)
                                .background(
                                    colorValue,
                                    RoundedCornerShape(
                                        10.dp,
                                        10.dp,
                                        0.dp,
                                        0.dp
                                    )
                                )
                                .padding(8.dp)
                                .clickable {
                                    homeViewModel.setSelectedColor(color)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            if (selectedColor == color) {
                                Image(
                                    painter = painterResource(id = R.drawable.icon_verificar),
                                    contentDescription = null,
                                    colorFilter = ColorFilter.tint(Color.White),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        Row(
                            modifier = Modifier
                                .height(15.dp)
                                .width(60.dp)
                                .clip(RoundedCornerShape(
                                    0.dp,
                                    0.dp,
                                    10.dp,
                                    10.dp)
                                ),
                        ) {
                            colorVariations[color]?.forEach { variaton ->
                                Box(
                                    modifier = Modifier
                                        .height(15.dp)
                                        .weight(1f)
                                        .background(variaton)
                                )
                            }
                        }
                    }
                }
            }
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
                thumbColor = MaterialTheme.colorScheme.surfaceContainer,
                activeTrackColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                inactiveTrackColor = ColorGrayMedium
            )
        )
    }
}