package com.dieyteixeira.fluxsync.ui.home.components

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.dieyteixeira.fluxsync.R
import kotlin.math.PI
import kotlin.math.sin

@RequiresApi(Build.VERSION_CODES.S)
private fun getRenderEffect(): RenderEffect {

    val blurEffect = RenderEffect
        .createBlurEffect(80f, 80f, Shader.TileMode.MIRROR)

    val alphaMatrix = RenderEffect.createColorFilterEffect(
        ColorMatrixColorFilter(
            ColorMatrix(
                floatArrayOf(
                    1f, 0f, 0f, 0f, 0f,
                    0f, 1f, 0f, 0f, 0f,
                    0f, 0f, 1f, 0f, 0f,
                    0f, 0f, 0f, 50f, -5000f
                )
            )
        )
    )

    return RenderEffect
        .createChainEffect(alphaMatrix, blurEffect)
}

fun Easing.transform(from: Float, to: Float, value: Float): Float {
    return transform(((value - from) * (1f / (to - from))).coerceIn(0f, 1f))
}

operator fun PaddingValues.times(value: Float): PaddingValues = PaddingValues(
    top = calculateTopPadding() * value,
    bottom = calculateBottomPadding() * value,
    start = calculateStartPadding(LayoutDirection.Ltr) * value,
    end = calculateEndPadding(LayoutDirection.Ltr) * value
)

@Composable
fun CentralButton(
    isMenuExtended: MutableState<Boolean>,
    onClickConta: () -> Unit,
    onClickCategoria: () -> Unit,
    onClickTransacao: () -> Unit
) {

    val color = MaterialTheme.colorScheme.surfaceContainer

    val fabAnimationProgress by animateFloatAsState(
        targetValue = if (isMenuExtended.value) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1000,
            easing = LinearEasing
        )
    )

    val clickAnimationProgress by animateFloatAsState(
        targetValue = if (isMenuExtended.value) 1f else 0f,
        animationSpec = tween(
            durationMillis = 400,
            easing = LinearEasing
        )
    )

    val renderEffect = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        getRenderEffect().asComposeRenderEffect()
    } else {
        null
    }

    MainScreen(
        renderEffect = renderEffect,
        fabAnimationProgress = fabAnimationProgress,
        clickAnimationProgress = clickAnimationProgress,
        color = color,
        toggleAnimation = { isMenuExtended.value = isMenuExtended.value.not() },
        onClickConta = onClickConta,
        onClickCategoria = onClickCategoria,
        onClickTransacao = onClickTransacao
    )
}

@Composable
fun MainScreen(
    renderEffect: androidx.compose.ui.graphics.RenderEffect?,
    fabAnimationProgress: Float = 0f,
    clickAnimationProgress: Float = 0f,
    color: Color,
    toggleAnimation: () -> Unit = { },
    onClickConta: () -> Unit = { },
    onClickCategoria: () -> Unit = { },
    onClickTransacao: () -> Unit = { }
) {
    Box(
        Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Diamond(
            color = color.copy(alpha = 0.5f),
            animationProgress = 0.5f
        )
        FabGroup(
            renderEffect = renderEffect,
            animationProgress = fabAnimationProgress,
            backgroundColor = color
        )
        FabGroup(
            renderEffect = null,
            animationProgress = fabAnimationProgress,
            backgroundColor = color,
            toggleAnimation = toggleAnimation,
            onClickConta = onClickConta,
            onClickCategoria = onClickCategoria,
            onClickTransacao = onClickTransacao
        )
        Diamond(
            color = Color.White,
            animationProgress = clickAnimationProgress
        )
    }
}

@Composable
fun Diamond(color: Color, animationProgress: Float) {
    val animationValue = sin(PI * animationProgress).toFloat()

    Box(
        modifier = Modifier
            .padding(18.dp)
            .size(40.dp)
            .scale(2 - animationValue)
            .rotate(45f)
            .border(
                width = 3.dp,
                color = color.copy(alpha = color.alpha * animationValue),
                shape = RoundedCornerShape(0.dp, 10.dp, 0.dp, 10.dp)
            )
    )
}

@Composable
fun FabGroup(
    animationProgress: Float = 0f,
    renderEffect: androidx.compose.ui.graphics.RenderEffect? = null,
    backgroundColor: Color,
    toggleAnimation: () -> Unit = { },
    onClickConta: () -> Unit = { },
    onClickCategoria: () -> Unit = { },
    onClickTransacao: () -> Unit = { }
) {

    Box(
        Modifier
            .fillMaxSize()
            .graphicsLayer { this.renderEffect = renderEffect }
            .padding(bottom = 10.dp),
        contentAlignment = Alignment.BottomCenter
    ) {

        // Desenha o primeiro FAB animado
        AnimatedFab(
            icon = R.drawable.icon_banco,
            text = "Conta",
            modifier = Modifier
                .padding(
                    PaddingValues(
                        bottom = 45.dp,
                        end = 100.dp
                    ) * FastOutSlowInEasing.transform(0f, 0.8f, animationProgress)
                ),
            opacity = LinearEasing.transform(0.2f, 0.7f, animationProgress),
            iconRotation = -45f,
            iconColor = Color.White,
            backgroundColor = backgroundColor,
            onClick = {
                toggleAnimation()
                onClickConta()
            }
        )

        // Desenha o segundo FAB animado
        AnimatedFab(
            icon = R.drawable.icon_documento,
            text = "Transação",
            modifier = Modifier
                .padding(
                    PaddingValues(
                        bottom = 92.dp,
                    ) * FastOutSlowInEasing.transform(0.1f, 0.9f, animationProgress)
                ),
            opacity = LinearEasing.transform(0.3f, 0.8f, animationProgress),
            iconRotation = -45f,
            iconColor = Color.White,
            backgroundColor = backgroundColor,
            onClick = {
                toggleAnimation()
                onClickTransacao()
            }
        )

        // Desenha o terceiro FAB animado
        AnimatedFab(
            icon = R.drawable.icon_aplicativos,
            text = "Categoria",
            modifier = Modifier.padding(
                PaddingValues(
                    bottom = 45.dp,
                    start = 100.dp
                ) * FastOutSlowInEasing.transform(0.2f, 1.0f, animationProgress)
            ),
            opacity = LinearEasing.transform(0.4f, 0.9f, animationProgress),
            iconRotation = -45f,
            iconColor = Color.White,
            backgroundColor = backgroundColor,
            onClick = {
                toggleAnimation()
                onClickCategoria()
            }
        )

        // Desenha o FAB central animado (sem ícone)
        AnimatedFab(
            modifier = Modifier
                .scale(1f - LinearEasing.transform(0.5f, 0.85f, animationProgress)),
            iconRotation = -45f,
            backgroundColor = backgroundColor
        )

        // Desenha o FAB principal com o ícone de adicionar (+), que alterna a animação
        AnimatedFab(
            icon = R.drawable.icon_mais,
            modifier = Modifier
                .rotate(
                    225 * FastOutSlowInEasing
                        .transform(0.35f, 0.65f, animationProgress)
                ),
            onClick = {
                toggleAnimation()
            },
            iconColor = if (animationProgress >= 0.8f) backgroundColor else Color.White,
            backgroundColor = Color.Transparent,
            iconRotation = -45f
        )
    }
}

@Composable
fun AnimatedFab(
    modifier: Modifier,
    icon: Int? = null,
    opacity: Float = 1f,
    backgroundColor: Color,
    iconColor: Color? = null,
    iconRotation: Float = 0f,
    text: String? = null,
    onClick: () -> Unit = {}
) {
    FloatingActionButton(
        onClick = onClick,
        elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp),
        backgroundColor = backgroundColor,
        shape = RoundedCornerShape(0.dp, 10.dp, 0.dp, 10.dp),
        modifier = modifier
            .scale(0.9f)
            .rotate(45f)
    ) {
        icon?.let { id ->
            Image(
                painter = painterResource(id = id),
                contentDescription = null,
                colorFilter = ColorFilter.tint(iconColor?.copy(alpha = opacity) ?: Color.White),
                modifier = Modifier
                    .size(20.dp)
                    .rotate(iconRotation)
            )
        }
    }
}