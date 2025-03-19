package com.dieyteixeira.fluxsync.app.notification

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.dieyteixeira.fluxsync.R
import com.dieyteixeira.fluxsync.app.di.model.Transacoes
import com.dieyteixeira.fluxsync.ui.home.viewmodel.HomeViewModel
import java.util.Calendar

class NotificationEnviar : BroadcastReceiver() {
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(
        context: Context,
        intent: Intent?
    ) {
        val contas = intent?.getStringExtra("contas") ?: return

        val builder = NotificationCompat.Builder(context!!, "fluxsync_channel")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Contas Pendentes 💰")
            .setContentText("Você tem contas vencidas ou próximas do vencimento.")
            .setStyle(NotificationCompat.BigTextStyle().bigText(contas))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        val notificationManager = ContextCompat.getSystemService(
            context, NotificationManager::class.java
        ) as NotificationManager
        notificationManager.notify(1001, builder.build())

        Log.d("NOTIFICACAO", "Notificação enviada: $contas")
    }
}

fun scheduleDailyNotifications(
    context: Context,
    homeViewModel: HomeViewModel,
    transacoes: List<Transacoes>
) {
    val contasPendentes = transacoes
        .filter { it.situacao == "pendente" && it.tipo == "despesa" }
        .mapNotNull { transacao ->
            val status = homeViewModel.verificarVencimento(transacao.data)
            status?.let { "${transacao.descricao} $status" } // Descrição da transação + status de vencimento
        }

    if (contasPendentes.isEmpty()) {
        Log.d("NOTIFICACAO", "Nenhuma conta vencida ou próxima do vencimento. Notificação cancelada.")
        return
    }

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val times = listOf(
        Pair(9, 0),   // Notificação às 09:00
        Pair(18, 0)  // Notificação às 18:00
    )

    times.forEachIndexed { index, (hour, minute) ->
        val intent = Intent(context, NotificationEnviar::class.java).apply {
            putExtra("contas", contasPendentes.joinToString("\n")) // Passa as contas para a notificação
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            1001 + index,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            if (timeInMillis < System.currentTimeMillis()) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }

        Log.d("AGENDAMENTO", "Agendando notificação para: ${calendar.time}")


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) {
            Log.e("NOTIFICACAO", "Permissão para agendar alarmes exatos não concedida.")
            return
        }

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )

        Log.d("AGENDAMENTO", "Notificação agendada para $hour:$minute com ID $index")
        Log.d("AGENDAMENTO", "Contas pendentes: $contasPendentes")
    }
}

fun disableBatteryOptimizations(
    context: Context
) {
    val pm = context.getSystemService(Context.POWER_SERVICE) as android.os.PowerManager
    val packageName = context.packageName

    if (!pm.isIgnoringBatteryOptimizations(packageName)) {
        val intent = Intent(android.provider.Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS)
        context.startActivity(intent)
    } else {
        Log.d("NOTIFICACAO", "Otimização de bateria já desativada para este app.")
    }
}