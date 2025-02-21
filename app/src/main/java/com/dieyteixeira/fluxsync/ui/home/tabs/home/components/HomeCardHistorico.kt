package com.dieyteixeira.fluxsync.ui.home.tabs.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieyteixeira.fluxsync.app.theme.ColorBackground
import com.dieyteixeira.fluxsync.app.theme.ColorCards
import com.dieyteixeira.fluxsync.app.theme.ColorFontesDark
import com.dieyteixeira.fluxsync.app.theme.ColorLine
import com.dieyteixeira.fluxsync.app.theme.ColorNegative
import com.dieyteixeira.fluxsync.app.theme.ColorPositive
import com.dieyteixeira.fluxsync.app.theme.DarkColorError
import com.dieyteixeira.fluxsync.app.theme.DarkColorSuccess
import com.dieyteixeira.fluxsync.ui.home.tabs.home.state.Transacao
import com.dieyteixeira.fluxsync.ui.home.tabs.home.state.transacoes

@Composable
fun HomeCardHistorico(
    isSaldoVisivel: Boolean
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 0.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(10.dp),
                ambientColor = Color.Gray,
                spotColor = Color.Gray
            )
            .background(
                color = ColorCards,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp, 3.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Histórico",
                    color = ColorFontesDark,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(0.dp, 3.dp)
                )
            }
        }
        Box(modifier = Modifier.fillMaxWidth(0.9f).height(1.dp).background(ColorLine).align(Alignment.CenterHorizontally))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 295.dp)
                .padding(10.dp)
        ) {
            items(transacoes.size) { index ->
                val backgroundColor = if (index % 2 == 0) ColorBackground.copy(alpha = 0.5f) else Color.Transparent
                TransacaoItem(
                    transacao = transacoes[index],
                    visibility = isSaldoVisivel,
                    backgroundColor = backgroundColor
                )
            }
        }
    }
}

@Composable
fun TransacaoItem(
    transacao: Transacao,
    visibility: Boolean,
    backgroundColor: Color
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = backgroundColor, shape = RoundedCornerShape(8.dp))
            .padding(10.dp, 7.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = transacao.descricao,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = if (visibility) transacao.valor else " R$ *****",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = if (transacao.tipo == "receita") ColorPositive else ColorNegative
        )
    }
}

@Preview
@Composable
private fun PreviewHomeCardSaldo() {
    HomeCardHistorico(
        isSaldoVisivel = true
    )
}