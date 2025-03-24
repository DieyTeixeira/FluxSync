package com.dieyteixeira.fluxsync.ui.home.tabs.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieyteixeira.fluxsync.R
import com.dieyteixeira.fluxsync.app.components.ButtonPersonalMaxWidth
import com.dieyteixeira.fluxsync.app.components.IconConta
import com.dieyteixeira.fluxsync.app.di.model.Conta
import com.dieyteixeira.fluxsync.app.theme.ColorCards
import com.dieyteixeira.fluxsync.app.theme.ColorFontesDark
import com.dieyteixeira.fluxsync.app.theme.ColorFontesLight
import com.dieyteixeira.fluxsync.app.theme.ColorFontesMedium
import com.dieyteixeira.fluxsync.app.theme.ColorLine
import com.dieyteixeira.fluxsync.app.theme.ColorNegative
import com.dieyteixeira.fluxsync.app.theme.ColorPositive
import com.dieyteixeira.fluxsync.ui.home.state.formatarValor
import com.dieyteixeira.fluxsync.ui.home.viewmodel.HomeViewModel

@Composable
fun HomeCardSaldo(
    homeViewModel: HomeViewModel,
    isSaldoVisivel: Boolean,
    isMostrarButton: Boolean,
    onVisibilityChange: (Boolean) -> Unit,
    onClickExibirConta: () -> Unit,
    onClickOcultarConta: () -> Unit,
    onClickContas: () -> Unit
) {

    val contas = homeViewModel.contas.value
    val saldoTotal = contas.sumOf { it.saldo }

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
                        style = MaterialTheme.typography.bodySmall,
                        color = ColorFontesMedium
                    )
                    Text(
                        text = if (isSaldoVisivel) formatarValor(saldoTotal) else "R$ *****",
                        style = MaterialTheme.typography.headlineLarge,
                        fontSize = 25.sp,
                        color = ColorFontesDark,
                        modifier = Modifier.padding(0.dp, 3.dp)
                    )
                }
                Image(
                    painter = painterResource(id = if (isSaldoVisivel) R.drawable.icon_visivel else R.drawable.icon_invisivel),
                    contentDescription = "Alternar visibilidade do saldo",
                    modifier = Modifier
                        .size(22.dp)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                        ) { onVisibilityChange(!isSaldoVisivel) },
                    colorFilter = ColorFilter.tint(ColorFontesLight)
                )
            }
        }
        Box(modifier = Modifier.fillMaxWidth(0.95f).height(1.dp).background(ColorLine).align(Alignment.CenterHorizontally))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 235.dp)
                .padding(10.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            onClickOcultarConta()
                        },
                        onLongPress = {
                            onClickExibirConta()
                        }
                    )
                }
        ) {
            items(homeViewModel.contas.value.size) { index ->
                SaldoItem(
                    contas = homeViewModel.contas.value[index],
                    visibility = isSaldoVisivel
                )
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        if (isMostrarButton) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                contentAlignment = Alignment.Center
            ) {
                ButtonPersonalMaxWidth(
                    onClick = { onClickContas() },
                    text = "Gerenciar contas",
                    colorText = MaterialTheme.colorScheme.surfaceContainer,
                    colorBorder = MaterialTheme.colorScheme.surfaceContainer,
                    height = 40.dp,
                    width = 0.6f,
                    icon = false
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
        IconConta(
            color = contas.color,
            icon = contas.icon
        )
        Spacer(modifier = Modifier.width(10.dp))
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = contas.descricao,
                style = MaterialTheme.typography.displayMedium
            )
            Text(
                text = if (visibility) formatarValor(contas.saldo) else "R$ *****",
                style = MaterialTheme.typography.headlineSmall,
                color = if (contas.saldo > 0) ColorPositive else ColorNegative
            )
        }
    }
}