package com.dieyteixeira.fluxsync.ui.login.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextWithIcon(
    textValue: String,
    iconName: ImageVector? = null,
    iconColor: Color? = null
) {
    Spacer(modifier = Modifier.height(5.dp))
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (iconName != null && iconColor != null) {
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = iconName,
                contentDescription = null,
                modifier = Modifier
                    .size(15.dp),
                tint = iconColor
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = textValue,
            style = TextStyle.Default.copy(
                fontSize = 12.sp
            ),
            color = Color.DarkGray
        )
    }
}

fun containsUpperCase(password: String): Boolean {
    return password.any { it.isUpperCase() }
}

fun containsLowerCase(password: String): Boolean {
    return password.any { it.isLowerCase() }
}

fun containsDigit(password: String): Boolean {
    return password.any { it.isDigit() }
}

fun containsSpecialCharacter(password: String): Boolean {
    val specialChars = "!@#$%^&*()-_=+{}[]|:;\"'<>,.?/~`"
    return password.any { it in specialChars }
}