package com.dieyteixeira.fluxsync.app.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
fun TextInput(
    textValue: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    focusRequester: FocusRequester = FocusRequester(),
    onClickKeyboard: () -> Unit = {},
    keyboardController: SoftwareKeyboardController? = null
) {
    BasicTextField(
        value = textValue,
        onValueChange = onValueChange,
        singleLine = true,
        textStyle = MaterialTheme.typography.displayLarge.copy(
            color = ColorFontesDark
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
            .background(Color.Transparent)
            .focusRequester(focusRequester)
            .onFocusChanged { focusState ->
                if (focusState.isFocused) {
                    onClickKeyboard()
                }
            },
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 4.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                if (textValue.isEmpty()) {
                    Text(
                        text = placeholder,
                        style = MaterialTheme.typography.displayLarge,
                        color = ColorFontesLight
                    )
                }
                innerTextField()
            }
        },
        keyboardOptions = KeyboardOptions.Default,
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        )
    )
}