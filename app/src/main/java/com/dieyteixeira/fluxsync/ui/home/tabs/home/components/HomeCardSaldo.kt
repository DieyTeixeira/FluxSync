package com.dieyteixeira.fluxsync.ui.home.tabs.home.components

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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieyteixeira.fluxsync.app.theme.ColorCards
import com.dieyteixeira.fluxsync.app.theme.ColorFontesDark
import com.dieyteixeira.fluxsync.app.theme.ColorFontesLight
import com.dieyteixeira.fluxsync.app.theme.ColorLine
import com.dieyteixeira.fluxsync.app.theme.ColorNegative
import com.dieyteixeira.fluxsync.app.theme.ColorPositive
import com.dieyteixeira.fluxsync.ui.home.state.Conta
import com.dieyteixeira.fluxsync.ui.home.state.formatarValor
import com.dieyteixeira.fluxsync.ui.home.state.contas

@Composable
fun HomeCardSaldo(
    isSaldoVisivel: Boolean,
    onVisibilityChange: (Boolean) -> Unit
) {

    val saldoTotal = contas.sumOf { it.valor }

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
                .height(80.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp, 3.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Saldo",
                        color = ColorFontesLight,
                        fontSize = 16.sp
                    )
                    Text(
                        text = if (isSaldoVisivel) formatarValor(saldoTotal) else "R$ *****",
                        color = ColorFontesDark,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(0.dp, 3.dp)
                    )
                }
                Icon(
                    imageVector = if (isSaldoVisivel) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                    contentDescription = "Alternar visibilidade do saldo",
                    tint = ColorFontesLight,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onVisibilityChange(!isSaldoVisivel) }
                )
            }
        }
        Box(modifier = Modifier.fillMaxWidth(0.95f).height(1.dp).background(ColorLine).align(Alignment.CenterHorizontally))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 235.dp)
                .padding(10.dp)
        ) {
            items(contas.size) { index ->
                SaldoItem(
                    contas = contas[index],
                    visibility = isSaldoVisivel
                )
            }
        }
    }
}

@Composable
fun SaldoItem(
    contas: Conta,
    visibility: Boolean
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .background(
                    color = contas.color,
                    shape = RoundedCornerShape(100)
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = contas.icon),
                contentDescription = "Ícone de banco",
                modifier = Modifier
                    .size(20.dp)
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = contas.descricao,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = if (visibility) formatarValor(contas.valor) else "R$ *****",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = if (contas.valor > 0) ColorPositive else ColorNegative
            )
        }
    }
}

@Preview
@Composable
private fun PreviewHomeCardSaldo() {
    HomeCardSaldo(
        isSaldoVisivel = true,
        onVisibilityChange = {}
    )
}