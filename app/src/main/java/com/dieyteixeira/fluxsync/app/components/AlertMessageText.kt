package com.dieyteixeira.fluxsync.app.components

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieyteixeira.fluxsync.app.theme.ColorError
import com.dieyteixeira.fluxsync.app.theme.ColorSuccess
import com.dieyteixeira.fluxsync.app.theme.DarkColorSuccess
import kotlinx.coroutines.delay

@Composable
fun AlertFirebaseMensagem(
    message: String,
    tipo: String,
    onClickCancel: () -> Unit
) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(message) {
        delay(500)
        isVisible = true
        delay(3000)
        isVisible = false
        delay(1000)
        onClickCancel()
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(durationMillis = 500)) +
                slideInVertically(
                    initialOffsetY = { 300 },
                    animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                ),
        exit = fadeOut(animationSpec = tween(durationMillis = 300)) +
                slideOutVertically(
                    targetOffsetY = { 300 },
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
//                            if (tipo == "success") {
//                                ColorSuccess
//                                DarkColorSuccess
//                            } else {
//                                ColorError
//                                DarkColorError
//                            }
                        )
                    ),
                    shape = RoundedCornerShape(15.dp)
                )
                .background(
                    color = if (tipo == "success") {
                        ColorSuccess
                    } else {
                        ColorError
                    },
                    shape = RoundedCornerShape(15.dp)
                )
        ) {
            Text(
                text = message,
                color = Color.White,
                style = TextStyle.Default.copy(
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