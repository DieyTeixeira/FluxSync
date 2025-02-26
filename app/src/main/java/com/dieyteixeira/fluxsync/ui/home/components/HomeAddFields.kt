package com.dieyteixeira.fluxsync.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieyteixeira.fluxsync.app.components.ButtonPersonalFilled
import com.dieyteixeira.fluxsync.app.theme.ColorFontesDark
import com.dieyteixeira.fluxsync.app.theme.ColorFontesLight
import com.dieyteixeira.fluxsync.app.theme.ColorLine
import com.dieyteixeira.fluxsync.app.theme.LightColor2

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
        Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(ColorLine))
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(25.dp, 20.dp, 25.dp, 5.dp)
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
        Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(ColorLine))
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(25.dp, 20.dp, 25.dp, 5.dp)
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
                    fontSize = 18.sp,
                    color = ColorFontesLight
                ) },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .onFocusChanged { focusState -> if (focusState.isFocused) { onClickKeyboard() } },
                textStyle = TextStyle(color = ColorFontesDark, fontSize = 18.sp),
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
        Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(ColorLine))
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(25.dp, 20.dp, 25.dp, 20.dp)
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
            Spacer(modifier = Modifier.width(15.dp))
            Text(
                text = textValue,
                fontSize = 18.sp,
                color = ColorFontesDark
            )
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
        Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(ColorLine))
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(25.dp, 20.dp, 25.dp, 20.dp)
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