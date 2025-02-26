package com.dieyteixeira.fluxsync.ui.home.components

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieyteixeira.fluxsync.R
import com.dieyteixeira.fluxsync.app.components.CustomField
import com.dieyteixeira.fluxsync.app.components.CustomKeyboard
import com.dieyteixeira.fluxsync.app.components.DatePickerCustom
import com.dieyteixeira.fluxsync.app.components.formatCurrencyInput
import com.dieyteixeira.fluxsync.app.components.removeLastDigit
import com.dieyteixeira.fluxsync.app.theme.ColorBackground
import com.dieyteixeira.fluxsync.app.theme.ColorFontesLight
import com.dieyteixeira.fluxsync.app.theme.ColorNegative
import com.dieyteixeira.fluxsync.app.theme.ColorPositive
import com.dieyteixeira.fluxsync.app.theme.LightColor2
import kotlinx.coroutines.delay
import java.time.LocalDate

@SuppressLint("DefaultLocale")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeAddTransactionScreen(
    onClose: () -> Unit
) {

    val scope = rememberCoroutineScope()
    val interactionSource = remember { MutableInteractionSource() }
    val screenWidth = with(LocalDensity.current) { LocalConfiguration.current.screenWidthDp.dp }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    var value by remember { mutableStateOf("0,00") }
    var isKeyboardVisible by remember { mutableStateOf(false) }
    var isClickClose by remember { mutableStateOf(false) }

    val typeTransaction = remember { mutableStateOf("receita") }
    val selectedIndex by remember { derivedStateOf { if (typeTransaction.value == "receita") 0 else 1 } }
    val deslocOffset = screenWidth / 2 / 2
    val indicatorOffset by animateDpAsState(
        targetValue = if (selectedIndex == 0) -deslocOffset else deslocOffset,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing), label = ""
    )

    var descriptionText by remember { mutableStateOf("") }
    var categoryText by remember { mutableStateOf("") }
    var contaText by remember { mutableStateOf("") }
    var dateSelected by remember { mutableStateOf(LocalDate.now()) }
    var dateRegistro by remember { mutableStateOf("") }
    var showCustomDatePicker by remember { mutableStateOf(false) }
    val formattedDate = if (dateSelected == LocalDate.now()) {
        "Hoje"
    } else {
        String.format("%02d/%02d/%d", dateSelected.dayOfMonth, dateSelected.monthValue, dateSelected.year)
    }
    val typeLancamento = remember { mutableStateOf("Único") }
    var observacaoText by remember { mutableStateOf("") }

    LaunchedEffect(isClickClose) {
        if (isClickClose) {
            delay(200)
            isKeyboardVisible = false
            isClickClose = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) { focusManager.clearFocus() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable{
                    onClose()
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_seta_baixo),
                contentDescription = "Icon Action",
                modifier = Modifier.size(25.dp),
                colorFilter = ColorFilter.tint(Color.Gray)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .background(
                            if (typeTransaction.value == "despesa") ColorNegative else ColorPositive,
                        )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(40.dp)
                                .clickable(
                                    indication = null,
                                    interactionSource = interactionSource
                                ) {
                                    typeTransaction.value = "receita"
                                },
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            Text(
                                text = "Receita",
                                fontSize = 16.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 5.dp)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(40.dp)
                                .clickable(
                                    indication = null,
                                    interactionSource = interactionSource
                                ) {
                                    typeTransaction.value = "despesa"
                                },
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            Text(
                                text = "Despesa",
                                fontSize = 16.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 5.dp)
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .offset(x = indicatorOffset)
                                .width(30.dp)
                                .height(2.dp)
                                .background(Color.White, RoundedCornerShape(100))
                                .align(Alignment.Center)
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    CustomField(
                        value = value,
                        onClickVisibility = {
                            isKeyboardVisible = true
                            keyboardController?.hide()
                        }
                    )
                }
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        HomeAddFieldsTextLeanding(
                            divider = false,
                            text = "Descrição",
                            textValue = descriptionText,
                            onValueChange = { descriptionText = it },
                            icon = R.drawable.icon_editar,
                            placeholder = "Adicionar a descrição",
                            maxLength = 20,
                            focusRequester = focusRequester,
                            onClickKeyboard = { isKeyboardVisible = false },
                            keyboardController = keyboardController
                        )
                    }
                    item {
                        HomeAddFieldsTextImage(
                            interactionSource = interactionSource,
                            divider = true,
                            text = "Categoria",
                            textValue = categoryText,
                            onValueChange = { categoryText = it },
                            placeholder = "Adicionar a categoria",
                            icon = R.drawable.banco_original,
                            focusRequester = focusRequester,
                            onClickKeyboard = { isKeyboardVisible = false },
                            keyboardController = keyboardController
                        )
                    }
                    item {
                        HomeAddFieldsTextImage(
                            interactionSource = interactionSource,
                            divider = true,
                            text = if (typeTransaction.value == "receita") "Entrada em" else "Saída de",
                            textValue = contaText,
                            onValueChange = { contaText = it },
                            placeholder = "Adicionar a conta",
                            icon = R.drawable.banco_c6,
                            focusRequester = focusRequester,
                            onClickKeyboard = { isKeyboardVisible = false },
                            keyboardController = keyboardController
                        )
                    }
                    item {
                        HomeAddFieldsTextIcon(
                            interactionSource = interactionSource,
                            divider = true,
                            text = "Data do lançamento",
                            textValue = formattedDate,
                            icon = R.drawable.icon_calendario,
                            onClick = { showCustomDatePicker = true }
                        )
                    }
                    item {
                        HomeAddFieldsTextButtons(
                            divider = true,
                            text = "Tipo de lançamento",
                            textValue1 = "Único",
                            colorText1 = if (typeLancamento.value == "Único") Color.White else ColorFontesLight,
                            color1 = if (typeLancamento.value == "Único") LightColor2 else Color.Transparent,
                            colorBorder1 = if (typeLancamento.value == "Único") Color.Transparent else ColorFontesLight,
                            onClick1 = { typeLancamento.value = "Único" },
                            textValue2 = "Fixo",
                            colorText2 = if (typeLancamento.value == "Fixo") Color.White else ColorFontesLight,
                            color2 = if (typeLancamento.value == "Fixo") LightColor2 else Color.Transparent,
                            colorBorder2 = if (typeLancamento.value == "Fixo") Color.Transparent else ColorFontesLight,
                            onClick2 = { typeLancamento.value = "Fixo" },
                            textValue3 = "Parcelado",
                            colorText3 = if (typeLancamento.value == "Parcelado") Color.White else ColorFontesLight,
                            color3 = if (typeLancamento.value == "Parcelado") LightColor2 else Color.Transparent,
                            colorBorder3 = if (typeLancamento.value == "Parcelado") Color.Transparent else ColorFontesLight,
                            onClick3 = { typeLancamento.value = "Parcelado" }
                        )
                    }
                    item {
                        HomeAddFieldsTextLeanding(
                            divider = true,
                            text = "Observação",
                            textValue = observacaoText,
                            onValueChange = { observacaoText = it },
                            icon = R.drawable.icon_comentario,
                            placeholder = "Adicionar alguma observação",
                            singleLine = false,
                            maxLength = 150,
                            focusRequester = focusRequester,
                            onClickKeyboard = { isKeyboardVisible = false },
                            keyboardController = keyboardController
                        )
                    }
                }
            }

            if (isKeyboardVisible) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = ColorBackground,
                            shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp)
                        )
                        .align(Alignment.BottomCenter),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CustomKeyboard(
                        onClick = { digit -> value = formatCurrencyInput(value, digit) },
                        onClickClose = { isClickClose = true },
                        onClickBackspace = { value = removeLastDigit(value) }
                    )
                }
            }

            if (showCustomDatePicker) {
                DatePickerCustom(
                    initialDate = dateSelected,
                    onDismissRequest = { showCustomDatePicker = false },
                    onCancelClick = { showCustomDatePicker = false },
                    onOKClick = { date ->
                        dateRegistro = date.toString()
                        dateSelected = date
                        showCustomDatePicker = false
                    }
                )
            }
        }
    }
}