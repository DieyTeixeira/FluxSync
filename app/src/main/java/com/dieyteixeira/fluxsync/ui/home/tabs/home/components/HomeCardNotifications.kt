package com.dieyteixeira.fluxsync.ui.home.tabs.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieyteixeira.fluxsync.app.di.model.Transacoes
import com.dieyteixeira.fluxsync.app.theme.ColorCards
import com.dieyteixeira.fluxsync.ui.home.viewmodel.HomeViewModel

@Composable
fun HomeCardNotifications(
    homeViewModel: HomeViewModel,
    transacoes: List<Transacoes>,
    onClick: () -> Unit
) {

    val transacoesNotificadas = transacoes
        .filter { it.situacao == "pendente" && it.tipo == "despesa" }
        .mapNotNull { transacao ->
            val status = homeViewModel.verificarVencimento(transacao.data)
            status?.let { statusText -> transacao.descricao to statusText }
        }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 125.dp)
            .padding(20.dp, 0.dp)
            .clickable {
                onClick()
            }
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(10.dp),
                ambientColor = Color.Gray,
                spotColor = Color.Gray
            )
            .background(
                color = ColorCards,
                shape = RoundedCornerShape(10.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        if (transacoesNotificadas.isNotEmpty()) {
            Column(
                modifier = Modifier.padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "⚠️ Atenção!",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )
                Spacer(modifier = Modifier.height(10.dp))
                transacoesNotificadas.forEach { (descricao, status) ->
                    Text(
                        text = "Conta \"$descricao\" $status.",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                }
            }
        } else {
            Text(text = "Não existem notificações", fontSize = 18.sp)
        }
    }
}