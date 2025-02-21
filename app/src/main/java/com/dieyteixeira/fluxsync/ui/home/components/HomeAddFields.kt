package com.dieyteixeira.fluxsync.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.Divider
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieyteixeira.fluxsync.R
import com.dieyteixeira.fluxsync.app.theme.ColorBackground
import com.dieyteixeira.fluxsync.app.theme.ColorCards
import com.dieyteixeira.fluxsync.app.theme.ColorFontesDark
import com.dieyteixeira.fluxsync.app.theme.ColorLine

@Composable
fun HomeAddFieldsText(
    divider: Boolean,
    text: String,
    textValue: String,
    onValueChange: (String) -> Unit,
    leadingIcon: ImageVector,
    placeholder: String,
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
        TextField(
            value = textValue,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    fontSize = 18.sp,
                    color = ColorFontesDark
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = ColorFontesDark
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
            singleLine = false,
            keyboardOptions = KeyboardOptions.Default,
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide() // Fecha o teclado ao pressionar "Done"
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

@Composable
fun HomeAddFieldsTextIcon(
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
                placeholder = {
                    Text(
                        text = placeholder,
                        fontSize = 18.sp,
                        color = ColorFontesDark
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
                singleLine = false,
                keyboardOptions = KeyboardOptions.Default,
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide() // Fecha o teclado ao pressionar "Done"
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