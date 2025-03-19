package com.dieyteixeira.fluxsync.app.notification

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.dieyteixeira.fluxsync.R

class NotificationEnviar : BroadcastReceiver() {
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onReceive(context: Context, intent: Intent?) {
        val channelId = "daily_reminder"

        // Criar canal de notificação (se necessário)
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Lembrete Diário",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        // Criar e exibir a notificação
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.logo_sifrao) // Substituir pelo seu ícone
            .setContentTitle("Lembrete de Pagamento")
            .setContentText("Não se esqueça de verificar suas contas no FluxSync!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context).notify(1001, notification)
    }
}