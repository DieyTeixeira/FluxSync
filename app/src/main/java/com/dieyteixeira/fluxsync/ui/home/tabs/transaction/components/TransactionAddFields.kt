package com.dieyteixeira.fluxsync.ui.home.tabs.transaction.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieyteixeira.fluxsync.app.components.IconCategoria
import com.dieyteixeira.fluxsync.app.components.IconConta
import com.dieyteixeira.fluxsync.app.theme.ColorFontesDark
import com.dieyteixeira.fluxsync.app.theme.ColorFontesLight
import com.dieyteixeira.fluxsync.app.theme.ColorLine
import com.dieyteixeira.fluxsync.app.theme.ColorNegative
import com.dieyteixeira.fluxsync.app.theme.ColorPositive
import com.dieyteixeira.fluxsync.app.theme.LightColor2
import com.dieyteixeira.fluxsync.ui.home.state.formatarValor

@Composable
fun TransactionAddFieldsInsert(
    interactionSource: MutableInteractionSource,
    divider: Boolean,
    text: String,
    textValue: String,
    textSaldo: Double = 0.0,
    color: Color,
    icon: Int,
    onClick: () -> Unit
) {
    if (divider) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(ColorLine))
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp)
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            color = ColorFontesDark,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    indication = null,
                    interactionSource = interactionSource
                ) {
                    onClick()
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(4.dp))
            if (text == "Categoria") {
                IconCategoria(
                    icon = icon,
                    color = color
                )
            } else {
                IconConta(
                    icon = icon,
                    color = color
                )
            }
            Spacer(modifier = Modifier.width(14.dp))
            Text(
                text = textValue,
                fontSize = 18.sp,
                color = ColorFontesDark
            )
            if (text != "Categoria" && textValue != "Selecionar Conta") {
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    text = "(${formatarValor(textSaldo)})",
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic,
                    color = if (textSaldo > 0) ColorPositive else ColorNegative
                )
            }
        }
    }
}

@Composable
fun TransactionAddFieldsTextLeanding(
    divider: Boolean,
    text: String,
    textValue: String,
    onValueChange: (String) -> Unit,
    icon: Int,
    placeholder: String,
    singleLine: Boolean = true,
    maxLength: Int,
    focusRequester: FocusRequester,
    onClickKeyboard: () -> Unit,
    keyboardController: SoftwareKeyboardController?
) {
    if (divider) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(ColorLine))
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp)
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            color = ColorFontesDark,
            fontWeight = FontWeight.Bold
        )
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
                        color = Color.Transparent
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    modifier = Modifier.size(23.dp),
                    colorFilter = ColorFilter.tint(LightColor2)
                )
            }
            TextField(
                value = textValue,
                onValueChange = { newValue ->
                    if (newValue.length <= maxLength) {
                        onValueChange(newValue)
                    }
                },
                placeholder = {
                    Text(
                        text = placeholder,
                        fontSize = 18.sp,
                        color = ColorFontesLight
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .onFocusChanged { focusState ->
                        if (focusState.isFocused) {
                            onClickKeyboard()
                        }
                    },
                textStyle = TextStyle(color = ColorFontesDark, fontSize = 18.sp),
                singleLine = singleLine,
                keyboardOptions = KeyboardOptions.Default,
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    textColor = ColorFontesDark,
                    cursorColor = ColorFontesDark
                )
            )
        }
    }
}

@Composable
fun TransactionAddFieldsValueLeanding(
    divider: Boolean,
    text: String,
    textValue: String,
    onValueChange: (String) -> Unit,
    icon: Int,
    placeholder: String,
    singleLine: Boolean = true,
    maxLength: Int,
    focusRequester: FocusRequester,
    onClickKeyboard: () -> Unit,
    keyboardController: SoftwareKeyboardController?,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    if (divider) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(ColorLine))
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp)
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            color = ColorFontesDark,
            fontWeight = FontWeight.Bold
        )
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
                        color = Color.Transparent
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    modifier = Modifier.size(23.dp),
                    colorFilter = ColorFilter.tint(LightColor2)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "R$",
                    fontSize = 18.sp,
                    color = ColorFontesDark
                )
                TextField(
                    value = textValue,
                    onValueChange = { newValue ->
                        if (newValue.length <= maxLength) {
                            onValueChange(newValue)
                        }
                    },
                    placeholder = {
                        Text(
                            text = placeholder,
                            fontSize = 18.sp,
                            color = ColorFontesLight
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester)
                        .onFocusChanged { focusState ->
                            if (focusState.isFocused) {
                                onClickKeyboard()
                            }
                        },
                    textStyle = TextStyle(color = ColorFontesDark, fontSize = 18.sp),
                    singleLine = singleLine,
                    keyboardOptions = keyboardOptions,
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        disabledBorderColor = Color.Transparent,
                        textColor = ColorFontesDark,
                        cursorColor = ColorFontesDark
                    )
                )
            }
        }
    }
}

@Composable
fun TransactionAddFieldsTextLongLeanding(
    divider: Boolean,
    text: String,
    textValue: String,
    onValueChange: (String) -> Unit,
    icon: Int,
    placeholder: String,
    heightMin: Dp = 30.dp,
    colorBorder: Color = Color.Transparent,
    singleLine: Boolean = true,
    maxLength: Int,
    focusRequester: FocusRequester,
    onClickKeyboard: () -> Unit,
    keyboardController: SoftwareKeyboardController?
) {
    if (divider) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(ColorLine))
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp)
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            color = ColorFontesDark,
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(7.dp))
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
                    modifier = Modifier.size(23.dp),
                    colorFilter = ColorFilter.tint(LightColor2)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            TextField(
                value = textValue,
                onValueChange = { newValue ->
                    if (newValue.length <= maxLength) {
                        onValueChange(newValue)
                    }
                },
                placeholder = {
                    Text(
                        text = placeholder,
                        fontSize = 18.sp,
                        color = ColorFontesLight
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = heightMin)
                    .padding(end = 5.dp)
                    .border(
                        width = 1.dp,
                        color = colorBorder,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .focusRequester(focusRequester)
                    .onFocusChanged { focusState ->
                        if (focusState.isFocused) {
                            onClickKeyboard()
                        }
                    },
                textStyle = TextStyle(color = ColorFontesDark, fontSize = 18.sp),
                singleLine = singleLine,
                keyboardOptions = KeyboardOptions.Default,
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    textColor = ColorFontesDark,
                    cursorColor = ColorFontesDark
                )
            )
        }
    }
}