package com.dieyteixeira.fluxsync.ui.home.tabs.transaction.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieyteixeira.fluxsync.R
import com.dieyteixeira.fluxsync.app.components.ButtonPersonalIcon
import com.dieyteixeira.fluxsync.app.di.model.Conta
import com.dieyteixeira.fluxsync.app.di.model.Transacoes
import com.dieyteixeira.fluxsync.app.theme.ColorCards
import com.dieyteixeira.fluxsync.app.theme.ColorFontesLight
import com.dieyteixeira.fluxsync.app.theme.ColorGrayDark
import com.dieyteixeira.fluxsync.app.theme.ColorGrayLight
import com.dieyteixeira.fluxsync.app.theme.ColorNegative
import com.dieyteixeira.fluxsync.app.theme.ColorPositive
import com.dieyteixeira.fluxsync.app.theme.LightColor2
import com.dieyteixeira.fluxsync.ui.home.components.AlertDialog
import com.dieyteixeira.fluxsync.ui.home.components.ConfirmDialog
import com.dieyteixeira.fluxsync.ui.home.components.InfoDialog
import com.dieyteixeira.fluxsync.ui.home.state.formatarValor
import com.dieyteixeira.fluxsync.ui.home.viewmodel.HomeViewModel

@Composable
fun TransactionItem(
    homeViewModel: HomeViewModel,
    transacao: Transacoes,
    conta: Conta,
    backgroundColor: Color,
    isMostrarButtons: Boolean,
    onClickTransaction: () -> Unit,
    onClickEditar: () -> Unit,
    onClickInfo: () -> Unit
) {

    var excluirDialog by remember { mutableStateOf(false) }
    var situacaoDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(10.dp, 0.dp, 10.dp, 10.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onClickTransaction()
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    if (isMostrarButtons) ColorGrayLight else Color.Transparent,
                    shape = RoundedCornerShape(15.dp)
                )
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = transacao.descricao,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
                if (transacao.lancamento == "Parcelado") {
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = transacao.parcelas,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = ColorFontesLight
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = conta.descricao,
                    fontSize = 16.sp,
                    color = ColorFontesLight
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = transacao.situacao,
                    fontSize = 16.sp,
                    color = ColorFontesLight
                )
            }
            Text(
                text = formatarValor(transacao.valor),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = if (transacao.tipo == "receita") ColorPositive else ColorNegative
            )
        }
        if (isMostrarButtons) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            0f to Color.Transparent,
                            0.3f to Color.Transparent,
                            1f to ColorCards
                        ),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .border(
                        width = 1.dp,
                        brush = Brush.verticalGradient(
                            0f to Color.Transparent,
                            0.3f to Color.Transparent,
                            1f to ColorGrayDark
                        ),
                        shape = RoundedCornerShape(20.dp)
                    ),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ButtonPersonalIcon( // info
                    onClick = { onClickInfo() },
                    icon = R.drawable.icon_info,
                    color = LightColor2,
                    size = 35.dp
                )
                ButtonPersonalIcon( // excluir
                    onClick = { if (transacao.situacao == "pendente") {
                        excluirDialog = true
                    } },
                    icon = R.drawable.icon_excluir,
                    color = if (transacao.situacao == "pendente") LightColor2 else ColorGrayDark,
                    size = 35.dp
                )
                ButtonPersonalIcon( // editar
                    onClick = { if (transacao.situacao == "pendente") {
                        onClickEditar()
                    } },
                    icon = R.drawable.icon_editar,
                    color = if (transacao.situacao == "pendente") LightColor2 else ColorGrayDark,
                    size = 35.dp,
                    sizeIcon = 18.dp
                )
                ButtonPersonalIcon( // situacao
                    onClick = {
                        situacaoDialog = true
                    },
                    icon = if (transacao.situacao == "efetivado") R.drawable.icon_balao_check else R.drawable.icon_balao_cruz,
                    color = if (transacao.situacao == "efetivado") ColorPositive else ColorNegative,
                    size = 35.dp
                )
            }
        }
    }

    if (excluirDialog) {
        ConfirmDialog(
            text = textExcluir,
            onClickClose = { excluirDialog = false },
            onClickYes = {
                homeViewModel.excluirTransacao(transacao.grupoId)
                excluirDialog = false
            }
        )
    }

    if (situacaoDialog) {
        ConfirmDialog(
            text = textSituacao,
            onClickClose = { situacaoDialog = false },
            onClickYes = {
                homeViewModel.editarSituacao(
                    transacao.id,
                    transacao.situacao,
                    transacao.tipo,
                    transacao.valor,
                    transacao.contaId,
                    conta.saldo
                )
                situacaoDialog = false
            }
        )
    }
}