package com.dieyteixeira.fluxsync.ui.home.components

import android.os.Build
import android.util.Log
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
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Edit
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
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
import com.dieyteixeira.fluxsync.app.theme.ColorFontesDark
import com.dieyteixeira.fluxsync.app.theme.ColorLine
import com.dieyteixeira.fluxsync.app.theme.ColorNegative
import com.dieyteixeira.fluxsync.app.theme.ColorPositive
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate

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
            Icon(
                imageVector = Icons.Filled.ArrowBackIosNew,
                contentDescription = "Icon Action",
                tint = Color.Gray,
                modifier = Modifier
                    .rotate(-90f)
                    .size(30.dp)
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
                        HomeAddFieldsText(
                            divider = false,
                            text = "Descrição",
                            textValue = descriptionText,
                            onValueChange = { descriptionText = it },
                            leadingIcon = Icons.Outlined.Edit,
                            placeholder = "Adicionar a descrição",
                            focusRequester = focusRequester,
                            onClickKeyboard = { isKeyboardVisible = false },
                            keyboardController = keyboardController
                        )
                    }
                    item {
                        HomeAddFieldsTextIcon(
                            divider = true,
                            text = "Categoria",
                            textValue = categoryText,
                            onValueChange = { categoryText = it },
                            placeholder = "Adicionar a categoria",
                            icon = R.drawable.banco_orginal,
                            focusRequester = focusRequester,
                            onClickKeyboard = { isKeyboardVisible = false },
                            keyboardController = keyboardController
                        )
                    }
                    item {
                        HomeAddFieldsTextIcon(
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
                        Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(ColorLine))
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(25.dp, 20.dp, 25.dp, 5.dp)
                        ) {
                            Text(
                                text = "Data",
                                fontSize = 18.sp,
                                color = ColorFontesDark,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Spacer(modifier = Modifier.width(7.dp))
                                Box(
                                    modifier = Modifier
                                        .size(30.dp)
                                        .background(
                                            color = Color.Black,
                                            shape = RoundedCornerShape(100)
                                        )
                                        .clickable(
                                            indication = null,
                                            interactionSource = interactionSource
                                        ) {
                                            showCustomDatePicker = true
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.CalendarMonth,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(20.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(7.dp))
                                Text(
                                    text = "${dateSelected.dayOfMonth}/${dateSelected.monthValue}/${dateSelected.year}",
                                    fontSize = 18.sp,
                                    color = ColorFontesDark
                                )
                            }
                        }
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