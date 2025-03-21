package com.dieyteixeira.fluxsync.ui.home.tabs.home.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieyteixeira.fluxsync.R
import com.dieyteixeira.fluxsync.app.components.dicasFinanceiras
import com.dieyteixeira.fluxsync.app.di.model.Transacoes
import com.dieyteixeira.fluxsync.app.theme.ColorCards
import com.dieyteixeira.fluxsync.app.theme.ColorFontesDark
import com.dieyteixeira.fluxsync.app.theme.ColorNegative
import com.dieyteixeira.fluxsync.app.theme.ColorPositive
import com.dieyteixeira.fluxsync.app.theme.GreenCont
import com.dieyteixeira.fluxsync.app.theme.LightColor1
import com.dieyteixeira.fluxsync.app.theme.LightColor3
import com.dieyteixeira.fluxsync.app.theme.LightColor4
import com.dieyteixeira.fluxsync.app.theme.RedCont
import com.dieyteixeira.fluxsync.app.theme.YellowCont
import com.dieyteixeira.fluxsync.ui.home.viewmodel.HomeViewModel
import kotlin.random.Random

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

    val dicaAleatoria = dicasFinanceiras[Random.nextInt(dicasFinanceiras.size)]

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
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_aviso),
                        contentDescription = "Alerta",
                        modifier = Modifier.size(20.dp),
                        colorFilter = ColorFilter.tint(YellowCont)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Atenção!",
                        style = MaterialTheme.typography.headlineMedium,
                        color = ColorNegative
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                transacoesNotificadas.forEach { (descricao, status) ->
                    Text(
                        text = "Conta \"$descricao\" $status.",
                        style = MaterialTheme.typography.displaySmall,
                        textAlign = TextAlign.Center,
                        color = ColorFontesDark,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 5.dp)
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_info),
                        contentDescription = "Alerta",
                        modifier = Modifier.size(20.dp),
                        colorFilter = ColorFilter.tint(GreenCont)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Dica financeira",
                        style = MaterialTheme.typography.headlineMedium,
                        color = ColorPositive
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = dicaAleatoria,
                    style = MaterialTheme.typography.displaySmall,
                    textAlign = TextAlign.Center,
                    color = ColorFontesDark,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun DicaFinanceira() {
    val dicaAleatoria = dicasFinanceiras[Random.nextInt(dicasFinanceiras.size)]

    Column(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_info),
                contentDescription = "Alerta",
                modifier = Modifier.size(20.dp),
                colorFilter = ColorFilter.tint(GreenCont)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Dica financeira",
                style = MaterialTheme.typography.titleMedium,
                fontSize = 18.sp,
                color = ColorPositive
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = dicaAleatoria,
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 14.sp,
            color = LightColor4,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(5.dp)
        )
    }
}