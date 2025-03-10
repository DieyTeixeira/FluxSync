package com.dieyteixeira.fluxsync.ui.home.tabs.transaction.screen

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieyteixeira.fluxsync.R
import com.dieyteixeira.fluxsync.app.components.ButtonPersonalFilled
import com.dieyteixeira.fluxsync.app.components.ButtonPersonalIcon
import com.dieyteixeira.fluxsync.app.components.ButtonPersonalOutline
import com.dieyteixeira.fluxsync.app.components.CustomDialog
import com.dieyteixeira.fluxsync.app.components.anoAtual
import com.dieyteixeira.fluxsync.app.components.listarMeses
import com.dieyteixeira.fluxsync.app.components.nomeMesAtual
import com.dieyteixeira.fluxsync.app.di.model.Conta
import com.dieyteixeira.fluxsync.app.di.model.Transacoes
import com.dieyteixeira.fluxsync.app.theme.ColorCards
import com.dieyteixeira.fluxsync.app.theme.ColorFontesLight
import com.dieyteixeira.fluxsync.app.theme.ColorGrayDark
import com.dieyteixeira.fluxsync.app.theme.ColorGrayLight
import com.dieyteixeira.fluxsync.app.theme.ColorNegative
import com.dieyteixeira.fluxsync.app.theme.ColorPositive
import com.dieyteixeira.fluxsync.app.theme.LightColor1
import com.dieyteixeira.fluxsync.app.theme.LightColor2
import com.dieyteixeira.fluxsync.app.theme.LightColor3
import com.dieyteixeira.fluxsync.ui.home.components.HomeAddTransactionScreen
import com.dieyteixeira.fluxsync.ui.home.state.formatarValor
import com.dieyteixeira.fluxsync.ui.home.tabs.transaction.components.textExcluir
import com.dieyteixeira.fluxsync.ui.home.tabs.transaction.components.textSituacao
import com.dieyteixeira.fluxsync.ui.home.viewmodel.HomeViewModel
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("MutableCollectionMutableState")
@Composable
fun TransactionTab(
    homeViewModel: HomeViewModel,
) {
    var showTransactionEdit by remember { mutableStateOf(false) }
    var transacoesFiltradas by remember { mutableStateOf(emptyList<Transacoes>()) }
    val mostrarTransacoesMap = remember { mutableStateOf(mutableMapOf<String, Boolean>()) }
    var mostrarDialogo by remember { mutableStateOf(false) }
    var anoSelecionado by remember { mutableStateOf(Calendar.getInstance().get(Calendar.YEAR)) }
    val anoAtual = anoAtual()
    val anos = (anoAtual - 10..anoAtual + 10).toList()
    val anosComEspacos = listOf("") + anos + listOf(" ")
    val mesesComEspacos = listOf("") + listarMeses() + listOf(" ")

    val lazyListStateAno = rememberLazyListState()
    val snapBehaviorAno = rememberSnapFlingBehavior(lazyListStateAno)
    val verticalFadeAno = Brush.verticalGradient(
        0f to Color.Transparent,
        0.5f to Color.Gray,
        0.5f to Color.Gray,
        1f to Color.Transparent
    )
    val anoCentral by remember {
        derivedStateOf {
            val middleIndex = if (lazyListStateAno.firstVisibleItemScrollOffset == 0) {
                lazyListStateAno.firstVisibleItemIndex + 1
            } else {
                lazyListStateAno.firstVisibleItemIndex + 2
            }
            anosComEspacos.getOrNull(middleIndex) ?: anoSelecionado
        }
    }

    val lazyListStateMes = rememberLazyListState()
    val snapBehaviorMes = rememberSnapFlingBehavior(lazyListStateMes)
    val horizontalFadeMes = Brush.horizontalGradient(
        0f to Color.Transparent,
        0.35f to Color.Gray,
        0.5f to Color.Gray,
        0.5f to Color.Gray,
        0.65f to Color.Gray,
        1f to Color.Transparent
    )
    val mesCentral by remember {
        derivedStateOf {
            val middleIndex = if (lazyListStateMes.firstVisibleItemScrollOffset == 0) {
                lazyListStateMes.firstVisibleItemIndex + 1
            } else {
                lazyListStateMes.firstVisibleItemIndex + 2
            }
            mesesComEspacos.getOrNull(middleIndex) ?: nomeMesAtual(Calendar.getInstance().get(Calendar.MONTH))
        }
    }

    LaunchedEffect(homeViewModel.transacoes.value, mesCentral, anoSelecionado) {
        transacoesFiltradas = homeViewModel.transacoes.value.filter { transacao ->
            val dataTransacao = transacao.data
            val anoCorreto = dataTransacao.year + 1900
            nomeMesAtual(dataTransacao.month) == mesCentral &&
                    anoCorreto == anoSelecionado
        }.toList()
    }

    Box() {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(LightColor1),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Fluxo de caixa",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 20.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    ButtonPersonalOutline(
                        onClick = { mostrarDialogo = true },
                        text = "$anoSelecionado",
                        colorText = Color.White,
                        color = Color.White,
                        height = 25.dp,
                        width = 60.dp,
                        rounded = 10.dp
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(LightColor3)
            ) {
                LazyRow(
                    state = lazyListStateMes,
                    flingBehavior = snapBehaviorMes,
                    modifier = Modifier
                        .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
                        .drawWithContent {
                            drawContent()
                            drawRect(brush = horizontalFadeMes, blendMode = BlendMode.DstIn)
                        }
                        .fillMaxWidth()
                        .height(50.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(mesesComEspacos) { mes ->
                        if (mes == "") { Spacer(modifier = Modifier.width(35.dp)) }
                        Text(
                            text = mes,
                            style = TextStyle(
                                fontSize = if (mes == mesCentral) 20.sp else 14.sp,
                                fontWeight = if (mes == mesCentral) FontWeight.Bold else FontWeight.Normal,
                                color = Color.White
                            ),
                            modifier = Modifier
                                .height(30.dp)
                                .width(100.dp)
                                .wrapContentHeight(),
                            textAlign = TextAlign.Center
                        )
                        if (mes == " ") { Spacer(modifier = Modifier.width(35.dp)) }
                    }
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val transacoesAgrupadas = transacoesFiltradas.groupBy { transacao ->
                    val data = transacao.data
                    val dia = data.date
                    val mes = nomeMesAtual(data.month)
                    "$dia de $mes"
                }
                var isGrayBackground = false
                transacoesAgrupadas.forEach { (dataFormatada, transacoes) ->
                    isGrayBackground = !isGrayBackground
                    val backgroundColor = if (isGrayBackground) ColorCards else ColorCards.copy(alpha = 0.6f)

                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(backgroundColor)
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = dataFormatada,
                                fontSize = 16.sp,
                                color = ColorFontesLight
                            )
                        }
                    }
                    items(transacoes) { transacao ->
                        val isMostrarButtons = mostrarTransacoesMap.value[transacao.id] ?: false
                        TransacaoItem(
                            homeViewModel = homeViewModel,
                            transacao = transacao,
                            conta = homeViewModel.contas.value.first { it.id == transacao.contaId },
                            backgroundColor = backgroundColor,
                            isMostrarButtons = isMostrarButtons,
                            onClickExibirTransaction = {
                                mostrarTransacoesMap.value = mutableMapOf(transacao.id to true)
                            },
                            onClickOcultarTransaction = {
                                mostrarTransacoesMap.value = mostrarTransacoesMap.value.toMutableMap()
                                    .also { it[transacao.id] = false }
                            },
                            onClickEditar = { showTransactionEdit = true }
                        )
                    }
                }
            }

            if (mostrarDialogo) {
                AlertDialog(
                    onDismissRequest = { },
                    confirmButton = { },
                    title = { Text(text = "Selecionar Ano", fontSize = 18.sp) },
                    text = {
                        Box(
                            modifier = Modifier
                                .height(200.dp)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.5f)
                                    .height(50.dp)
                                    .background(LightColor2, RoundedCornerShape(12.dp))
                            )
                            LazyColumn(
                                state = lazyListStateAno,
                                flingBehavior = snapBehaviorAno,
                                modifier = Modifier
                                    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
                                    .drawWithContent {
                                        drawContent()
                                        drawRect(brush = verticalFadeAno, blendMode = BlendMode.DstIn)
                                    }
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(2.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                items(anosComEspacos) { ano ->
                                    if (ano == "") {Spacer(modifier = Modifier.height(23.dp))}
                                    Text(
                                        text = ano.toString(),
                                        style = TextStyle(
                                            fontSize = if (ano == anoCentral) 30.sp else 20.sp,
                                            fontWeight = if (ano == anoCentral) FontWeight.Bold else FontWeight.Normal,
                                            color = if (ano == anoCentral) Color.White else Color.Gray
                                        ),
                                        modifier = Modifier
                                            .height(50.dp)
                                            .width(150.dp)
                                            .wrapContentHeight()
                                            .clickable {
                                                anoCentral.toString().toIntOrNull()?.let { novoAno ->
                                                    anoSelecionado = novoAno
                                                    mostrarDialogo = false
                                                }
                                            },
                                        textAlign = TextAlign.Center
                                    )
                                    if (ano == " ") {Spacer(modifier = Modifier.height(23.dp))}
                                }
                            }
                        }
                    }
                )
            }
        }
        AnimatedVisibility(
            visible = showTransactionEdit,
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(durationMillis = 300)
            ) + fadeIn(),
            exit = slideOutVertically(
                targetOffsetY = { it },
                animationSpec = tween(durationMillis = 300)
            ) + fadeOut()
        ) {
            Column {
                Spacer(modifier = Modifier.height(50.dp))
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp)
                        )
                ) {
                    HomeAddTransactionScreen(
                        homeViewModel = homeViewModel,
                        onClose = { showTransactionEdit = false }
                    )
                }
            }
        }
    }
}

@Composable
fun TransacaoItem(
    homeViewModel: HomeViewModel,
    transacao: Transacoes,
    conta: Conta,
    backgroundColor: Color,
    isMostrarButtons: Boolean,
    onClickExibirTransaction: () -> Unit,
    onClickOcultarTransaction: () -> Unit,
    onClickEditar: () -> Unit,
) {

    var excluirDialog by remember { mutableStateOf(false) }
    var situacaoDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(10.dp, 0.dp, 10.dp, 10.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        onClickOcultarTransaction()
                    },
                    onLongPress = {
                        onClickExibirTransaction()
                    }
                )
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
                homeViewModel.editarTransacaoSituacao(
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

@Composable
fun ConfirmDialog(
    text: String,
    onClickClose: () -> Unit,
    onClickYes: () -> Unit
) {

    CustomDialog(
        onClickClose = onClickClose
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Confirmar ação",
                fontSize = 20.sp,
                color = LightColor3,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(25.dp))
            Text(
                text = text,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = ColorFontesLight
            )
            Spacer(modifier = Modifier.height(25.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ButtonPersonalFilled(
                    onClick = onClickClose,
                    text = "Não",
                    colorText = Color.White,
                    color = LightColor2,
                    height = 40.dp,
                    width = 100.dp
                )
                ButtonPersonalFilled(
                    onClick = onClickYes,
                    text = "Sim",
                    colorText = Color.White,
                    color = LightColor2,
                    height = 40.dp,
                    width = 100.dp
                )
            }
        }
    }
}