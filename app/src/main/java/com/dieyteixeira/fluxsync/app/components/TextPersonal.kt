package com.dieyteixeira.fluxsync.app.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieyteixeira.fluxsync.app.theme.ColorFontesDark
import com.dieyteixeira.fluxsync.app.theme.ColorFontesLight

@Composable
fun TextInputIcon(
    textValue: String,
    onValueChange: (String) -> Unit,
    icon: ImageVector,
    placeholder: String,
    focusRequester: FocusRequester,
    onClickKeyboard: () -> Unit,
    keyboardController: SoftwareKeyboardController?
) {
    TextField(
        value = textValue,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                fontSize = 18.sp,
                color = ColorFontesLight
            )
        },
        leadingIcon = {
            Icon(
                imageVector = icon,
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

@Composable
fun TextInput(
    textValue: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    TextField(
        value = textValue,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                fontSize = 18.sp,
                color = ColorFontesLight
            )
        },
        modifier = Modifier
            .fillMaxWidth(),
        textStyle = TextStyle(color = ColorFontesDark, fontSize = 18.sp),
        singleLine = false,
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