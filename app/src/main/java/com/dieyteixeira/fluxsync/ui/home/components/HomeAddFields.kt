package com.dieyteixeira.fluxsync.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dieyteixeira.fluxsync.R
import com.dieyteixeira.fluxsync.app.components.ButtonPersonalFilled
import com.dieyteixeira.fluxsync.app.components.IconCategoria
import com.dieyteixeira.fluxsync.app.components.IconConta
import com.dieyteixeira.fluxsync.app.theme.ColorFontesDark
import com.dieyteixeira.fluxsync.app.theme.ColorFontesLight
import com.dieyteixeira.fluxsync.app.theme.ColorGrayDark
import com.dieyteixeira.fluxsync.app.theme.ColorLine
import com.dieyteixeira.fluxsync.app.theme.ColorNegative
import com.dieyteixeira.fluxsync.app.theme.ColorPositive
import com.dieyteixeira.fluxsync.ui.home.state.formatarValor

@Composable
fun HomeAddFieldsText(
    text: String,
    textValue: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    singleLine: Boolean = true,
    maxLength: Int = 100
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.headlineSmall,
                color = ColorFontesDark
            )
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
                        style = MaterialTheme.typography.bodyLarge,
                        color = ColorFontesLight
                    )
                },
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = ColorFontesDark),
                singleLine = singleLine,
                keyboardOptions = KeyboardOptions.Default,
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(ColorLine)
        )
    }
}

@Composable
fun HomeAddFieldsTextLeanding(
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
            .padding(25.dp, 15.dp, 25.dp, 0.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineSmall,
            color = ColorFontesDark
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
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.surfaceContainerLow)
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
                        style = MaterialTheme.typography.bodyLarge,
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
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = ColorFontesDark),
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
fun HomeAddFieldsTextLongLeanding(
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
            .padding(25.dp, 15.dp, 25.dp, 7.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineSmall,
            color = ColorFontesDark
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
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.surfaceContainerLow)
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
                        style = MaterialTheme.typography.bodyLarge,
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
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = ColorFontesDark),
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
fun HomeAddFieldsTextImage(
    interactionSource: MutableInteractionSource,
    divider: Boolean,
    text: String,
    textValue: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    icon: Int,
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
            .padding(25.dp, 20.dp, 25.dp, 5.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineSmall,
            color = ColorFontesDark
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
                        color = Color.Black,
                        shape = RoundedCornerShape(100)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(icon),
                    contentDescription = "Ícone",
                    modifier = Modifier
                        .size(20.dp)
                )
            }
            TextField(
                value = textValue,
                onValueChange = onValueChange,
                placeholder = { Text(
                    text = placeholder,
                    style = MaterialTheme.typography.bodyLarge,
                    color = ColorFontesLight
                ) },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .onFocusChanged { focusState ->
                        if (focusState.isFocused) {
                            onClickKeyboard()
                        }
                    },
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = ColorFontesDark),
                singleLine = false,
                keyboardOptions = KeyboardOptions.Default,
                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
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
fun HomeAddFieldsTextIcon(
    interactionSource: MutableInteractionSource,
    divider: Boolean,
    text: String,
    textValue: String,
    icon: Int,
    onClick: () -> Unit = {},
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
            .padding(25.dp, 15.dp, 25.dp, 15.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineSmall,
            color = ColorFontesDark
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
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.surfaceContainerLow)
                )
            }
            Spacer(modifier = Modifier.width(15.dp))
            Text(
                text = textValue,
                style = MaterialTheme.typography.bodyLarge,
                color = ColorFontesDark
            )
        }
    }
}

@Composable
fun HomeAddFieldsInsert(
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
            .padding(25.dp, 15.dp, 25.dp, 15.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineSmall,
            color = ColorFontesDark
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
            when (text) {
                "Categoria" -> {
                    IconCategoria(
                        icon = icon,
                        color = color
                    )
                }
                "Subcategoria" -> {
                    IconCategoria(
                        icon = icon,
                        color = color
                    )
                }
                else -> {
                    IconConta(
                        icon = icon,
                        color = color
                    )
                }
            }
            Spacer(modifier = Modifier.width(14.dp))
            Text(
                text = textValue,
                style = MaterialTheme.typography.bodyLarge,
                color = ColorFontesDark
            )
            if (text != "Categoria" && textValue != "Selecionar Conta" && text != "Subcategoria") {
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    text = "(${formatarValor(textSaldo)})",
                    style = MaterialTheme.typography.bodyLarge,
                    fontStyle = FontStyle.Italic,
                    color = if (textSaldo > 0) ColorPositive else ColorNegative
                )
            }
        }
    }
}

@Composable
fun HomeAddFieldsTextButtons(
    divider: Boolean,
    text: String,
    textValue1: String,
    colorText1: Color,
    color1: Color,
    colorBorder1: Color,
    textValue2: String,
    colorText2: Color,
    color2: Color,
    colorBorder2: Color,
    textValue3: String,
    colorText3: Color,
    color3: Color,
    colorBorder3: Color,
    onClick1: () -> Unit = {},
    onClick2: () -> Unit = {},
    onClick3: () -> Unit = {}
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
            .padding(25.dp, 15.dp, 25.dp, 15.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineSmall,
            color = ColorFontesDark
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(7.dp))
            ButtonPersonalFilled(
                onClick = onClick1,
                text = textValue1,
                colorText = colorText1,
                color = color1,
                colorBorder = colorBorder1,
                height = 35.dp,
                width = 100.dp
            )
            Spacer(modifier = Modifier.width(10.dp))
            ButtonPersonalFilled(
                onClick = onClick2,
                text = textValue2,
                colorText = colorText2,
                color = color2,
                colorBorder = colorBorder2,
                height = 35.dp,
                width = 100.dp
            )
            Spacer(modifier = Modifier.width(10.dp))
            ButtonPersonalFilled(
                onClick = onClick3,
                text = textValue3,
                colorText = colorText3,
                color = color3,
                colorBorder = colorBorder3,
                height = 35.dp,
                width = 100.dp
            )
        }
    }
}

@Composable
fun ButtonsIncDec(
    value: Int,
    width: Dp = 50.dp,
    onClickMenos: () -> Unit,
    onClickMais: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(35.dp)
                .background(color = MaterialTheme.colorScheme.surfaceContainerLow, shape = RoundedCornerShape(15.dp, 0.dp, 0.dp, 15.dp))
                .clickable() { onClickMenos() },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_menos_p),
                contentDescription = "Menos",
                modifier = Modifier.size(25.dp),
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
        Box(
            modifier = Modifier
                .height(35.dp)
                .width(width)
                .background(ColorGrayDark),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$value",
                style = MaterialTheme.typography.bodyLarge,
                color = ColorFontesDark,
                modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 0.dp)
            )
        }
        Box(
            modifier = Modifier
                .size(35.dp)
                .background(color = MaterialTheme.colorScheme.surfaceContainerLow, shape = RoundedCornerShape(0.dp, 15.dp, 15.dp, 0.dp))
                .clickable() { onClickMais() },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_mais_p),
                contentDescription = "Mais",
                modifier = Modifier.size(25.dp),
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
    }
}