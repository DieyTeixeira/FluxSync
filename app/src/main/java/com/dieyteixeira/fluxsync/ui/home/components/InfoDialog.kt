package com.dieyteixeira.fluxsync.ui.home.components

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dieyteixeira.fluxsync.R
import com.dieyteixeira.fluxsync.app.components.CustomDialog
import com.dieyteixeira.fluxsync.app.components.IconCategoria
import com.dieyteixeira.fluxsync.app.components.IconConta
import com.dieyteixeira.fluxsync.app.di.model.Categoria
import com.dieyteixeira.fluxsync.app.di.model.Conta
import com.dieyteixeira.fluxsync.app.di.model.Subcategoria
import com.dieyteixeira.fluxsync.app.theme.ColorFontesLight
import com.dieyteixeira.fluxsync.app.theme.ColorLine
import com.dieyteixeira.fluxsync.app.theme.ColorNegative
import com.dieyteixeira.fluxsync.app.theme.ColorPositive
import com.dieyteixeira.fluxsync.app.theme.GrayCont
import com.dieyteixeira.fluxsync.ui.home.state.formatarValor
import com.dieyteixeira.fluxsync.ui.home.viewmodel.HomeViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("DefaultLocale")
@Composable
fun InfoDialog(
    selectTransaction: String,
    homeViewModel: HomeViewModel,
    onClickClose: () -> Unit = {}
) {

    val selectedTransaction = homeViewModel.selectedTransaction.collectAsState().value

    LaunchedEffect(selectTransaction) {
        homeViewModel.getTransactionById(selectTransaction)
    }

    CustomDialog(
        onClickClose = { onClickClose() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Detalhes da Transação",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.surfaceContainer
            )
            Spacer(modifier = Modifier.height(25.dp))

            selectedTransaction?.let { transaction ->
                val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val formattedDate = formatter.format(transaction.data)

                val conta = homeViewModel.contas.value.firstOrNull { it.id == transaction.contaId }
                val categoria = homeViewModel.categorias.value.firstOrNull { it.id == transaction.categoriaId }
                val subcategoria = homeViewModel.subcategorias.value.firstOrNull { it.id == transaction.subcategoriaId }

                if (conta == null || categoria == null || subcategoria == null) {
                    Text(
                        text = "Erro ao carregar os detalhes da transação.",
                        style = MaterialTheme.typography.displayMedium,
                        textAlign = TextAlign.Center,
                        color = ColorFontesLight
                    )
                    return@CustomDialog
                }

                CardText(
                    icon = R.drawable.icon_texto,
                    textItem = transaction.descricao
                )
                CardText(
                    icon = R.drawable.icon_dinheiro,
                    textItem = formatarValor(transaction.valor)
                )
                if (transaction.lancamento == "Parcelado") {
                    CardText(
                        icon = R.drawable.icon_parcela,
                        textItem = transaction.parcelas + " parcelas"
                    )
                }

                CardIconText(conta, categoria, subcategoria, "Conta")
                CardIconText(conta, categoria, subcategoria, "Categoria")
                CardIconText(conta, categoria, subcategoria, "Subcategoria")

                CardText(
                    icon = R.drawable.icon_calendario,
                    textItem = formattedDate
                )
                if (transaction.observacao != "") {
                    CardText(
                        icon = R.drawable.icon_comentario,
                        textItem = transaction.observacao
                    )
                }
            } ?: Text(
                text = "Transação não encontrada.",
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center,
                color = ColorFontesLight
            )
        }
    }
}

@Composable
fun CardText(
    icon: Int,
    textItem: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp, 10.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Row(
            modifier = Modifier
                .height(30.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .background(
                        color = Color.Transparent
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.surfaceContainerLow)
                )
            }
            Text(
                text = textItem,
                style = MaterialTheme.typography.bodyLarge,
                color = ColorFontesLight,
                modifier = Modifier.padding(start = 15.dp)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(ColorLine)
        )
    }
}

@Composable
fun CardIconText(
    conta: Conta,
    categoria: Categoria,
    subcategoria: Subcategoria?,
    textTitulo: String,
) {

    val (descricao, icon, color) = when (textTitulo) {
        "Conta" -> Triple(
            conta.descricao,
            conta.icon,
            conta.color
        )
        "Categoria" -> Triple(
            categoria.descricao,
            categoria.icon,
            categoria.color
        )
        else -> Triple(
            subcategoria?.descricao ?: "Sem Subcategoria",
            subcategoria?.icon ?: R.drawable.icon_cubo,
            subcategoria?.color ?: GrayCont
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp, 10.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (textTitulo == "Conta") {
                IconConta(
                    icon = icon,
                    color = color,
                    sizeBox = 28,
                    sizeIcon = 16
                )
            } else {
                IconCategoria(
                    color = color,
                    icon = icon,
                    sizeBox = 30,
                    sizeIcon = 17,
                    rounded = 12
                )
            }
            Row {
                Text(
                    text = descricao,
                    style = MaterialTheme.typography.bodyLarge,
                    color = ColorFontesLight,
                    modifier = Modifier.padding(start = 15.dp)
                )
                if (textTitulo == "Conta") {
                    Spacer(modifier = Modifier.width(15.dp))
                    Text(
                        text = "(${formatarValor(conta.saldo)})",
                        style = MaterialTheme.typography.bodySmall,
                        color = if (conta.saldo > 0) ColorPositive else ColorNegative,
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(ColorLine)
        )
    }
}