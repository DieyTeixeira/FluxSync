package com.dieyteixeira.fluxsync.app.notification

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.dieyteixeira.fluxsync.app.di.repository.FirestoreRepository
import com.google.firebase.Timestamp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PixNotificationReceiver : BroadcastReceiver() {
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent) {
        val extras = intent.extras
        if (extras != null) {
            val mensagem = extras.getString("android.text", "") ?: ""
            val titulo = extras.getString("android.title", "") ?: ""

            Log.d("PixNotificationReceiver", "Notificação recebida: $titulo - $mensagem")

            if (titulo.contains("PIX", ignoreCase = true)) {
                processarPix(context, titulo, mensagem)
            }
        }
    }

    private fun processarPix(context: Context, titulo: String, mensagem: String) {
        val repository = FirestoreRepository()

        // Extrai informações da mensagem (exemplo: valor e tipo)
        val tipo = if (titulo.contains("recebido", ignoreCase = true)) "recebido" else "enviado"
        val valorRegex = Regex("R\\$\\s*([\\d,.]+)")
        val valorMatch = valorRegex.find(mensagem)
        val valor = valorMatch?.groupValues?.get(1)?.replace(",", ".")?.toDoubleOrNull() ?: 0.0

        // Salvar no Firestore
        CoroutineScope(Dispatchers.IO).launch {
            repository.salvarTransacao(
                descricao = titulo,
                valor = valor,
                tipo = tipo,
                situacao = "Pendente",
                categoriaId = "",
                contaId = "",
                data = Timestamp.now(),
                lancamento = "Único",
                parcelas = "1",
                observacao = ""
            )
        }
    }
}
