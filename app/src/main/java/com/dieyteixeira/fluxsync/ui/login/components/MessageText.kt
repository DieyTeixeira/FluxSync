package com.dieyteixeira.fluxsync.ui.login.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieyteixeira.fluxsync.app.theme.ColorError
import com.dieyteixeira.fluxsync.app.theme.ColorSuccess
import com.dieyteixeira.fluxsync.app.theme.DarkColorError
import com.dieyteixeira.fluxsync.app.theme.DarkColorSuccess
import kotlinx.coroutines.delay

@Composable
fun MensagemError(
    errorMessage: String,
    repeatMessage: Boolean,
    changeScreen: Boolean
) {
    var isVisible by remember { mutableStateOf(false) }
    var currentMessage by remember { mutableStateOf(errorMessage) }

    val errorMessageAjust = when (currentMessage) {
        "We have blocked all requests from this device due to unusual activity. Try again later." ->
            "Acesso bloqueado.\nTente novamente mais tarde"
        "ERROR_INVALID_CREDENTIAL" -> "Credenciais inválidas.\nTente novamente"
        "ERROR_INVALID_EMAIL" -> "O formato do email não é válido"
        "ERROR_USER_NOT_FOUND" -> "Usuário não encontrado.\nVerifique suas credenciais"
        "ERROR_WRONG_PASSWORD" -> "Senha incorreta"
        else -> currentMessage
    }

    LaunchedEffect(errorMessage, repeatMessage) {
        currentMessage = errorMessage
        isVisible = true
        delay(2500)
        isVisible = false
    }

    LaunchedEffect(changeScreen) {
        isVisible = false
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(durationMillis = 500)) +
                slideInVertically(
                    initialOffsetY = { if (changeScreen) -240 else it },
                    animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                ),
        exit = fadeOut(animationSpec = tween(durationMillis = 300)) +
                slideOutVertically(
                    targetOffsetY = { if (changeScreen) -240 else it },
                    animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
                )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .border(
                    width = 2.dp,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            ColorError,
                            DarkColorError
                        )
                    ),
                    shape = RoundedCornerShape(15.dp)
                )
                .background(
                    color = ColorError,
                    shape = RoundedCornerShape(15.dp)
                )
        ) {
            Text(
                text = errorMessageAjust,
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 18.sp,
                    fontStyle = FontStyle.Italic
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(8.dp)
            )
        }
    }
}

@Composable
fun MensagemSuccess(
    successMessage: String,
    onLoginSuccess: () -> Unit
) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(successMessage) {
        isVisible = true
        delay(2000)
        isVisible = false
        delay(300)
        onLoginSuccess()
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(durationMillis = 500)) +
                slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                ),
        exit = fadeOut(animationSpec = tween(durationMillis = 300)) +
                slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
                )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .border(
                    width = 2.dp,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            ColorSuccess,
                            DarkColorSuccess
                        )
                    ),
                    shape = RoundedCornerShape(15.dp)
                )
                .background(
                    color = ColorSuccess,
                    shape = RoundedCornerShape(15.dp)
                )
        ) {
            Text(
                text = "Login bem-sucedido!",
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 18.sp,
                    fontStyle = FontStyle.Italic
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(8.dp)
            )
        }
    }
}

@Composable
fun ErrorBoxSignIn(messageError: String, offsetY: Dp) {
    Box(
        modifier = Modifier
            .width(300.dp)
            .offset(y = offsetY, x = 75.dp)
            .border(1.dp, ColorError, RoundedCornerShape(10.dp))
            .background(Color.White, RoundedCornerShape(10.dp))
            .padding(8.dp, 5.dp)
    ) {
        Text(
            text = messageError,
            style = MaterialTheme.typography.bodyLarge,
            color = ColorError,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}