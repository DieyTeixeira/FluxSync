package com.dieyteixeira.fluxsync.ui.login.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dieyteixeira.fluxsync.R
import com.dieyteixeira.fluxsync.app.theme.ManageStatusBarIcons
import com.dieyteixeira.fluxsync.ui.login.components.CircleLoading
import com.dieyteixeira.fluxsync.ui.login.components.MensagemError
import com.dieyteixeira.fluxsync.ui.login.components.MensagemSuccess
import com.dieyteixeira.fluxsync.ui.login.state.LoginState
import com.dieyteixeira.fluxsync.ui.login.viewmodel.LoginViewModel

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    // Cor dos ícones da status bar
    ManageStatusBarIcons(isIconBlack = true)

    val loginState by loginViewModel.loginState.collectAsState()
    var errorMessage by remember { mutableStateOf("") }
    var successMessage by remember { mutableStateOf("") }
    var changeScreen by remember { mutableStateOf(false) }
    var repeatMessage by remember { mutableStateOf(false) }

    /* ---------------------------- Configuração da animação da tela ---------------------------- */
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val halfScreen = ((screenHeight / 2) + 40).dp
    val medScreen = halfScreen + 90.dp
    val plusScreen = halfScreen + 130.dp
    val roundedShape = 40.dp
    val animDuration = 500
    val setLogo by animateDpAsState(if (changeScreen) medScreen else 0.dp, tween(animDuration))
    val scaleLogo by animateFloatAsState(if (changeScreen) 0.6f else 1f, tween(animDuration))
    val setInfo by animateDpAsState(if (changeScreen) 240.dp else 0.dp, tween(animDuration))
    val setScreen by animateDpAsState(if (changeScreen) 0.dp else halfScreen, tween(animDuration))
    val sizeScreen by animateDpAsState(if (changeScreen) plusScreen else halfScreen, tween(animDuration))
    val shapeUp by animateDpAsState(if (changeScreen) roundedShape else 0.dp, tween(animDuration))
    val shapeDown by animateDpAsState(if (changeScreen) 0.dp else roundedShape, tween(animDuration))
    /* ------------------------------------------------------------------------------------------ */

    val currentLoginState by rememberUpdatedState(loginState)
    LaunchedEffect(currentLoginState) {
        when (currentLoginState) {
            is LoginState.Loading -> {
                errorMessage = ""
            }
            is LoginState.Success -> {
                successMessage = (loginState as LoginState.Success).userId
            }
            is LoginState.Error -> {
                errorMessage = (loginState as LoginState.Error).message
            }
            else -> {}
        }
    }

    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceContainerLow)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(80.dp))
            Image(
                painter = painterResource(id = R.drawable.logo_sifrao),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(250.dp)
                    .offset(y = setLogo)
                    .scale(scaleLogo)
                    .clip(CircleShape)
                    .clickable { loginViewModel.signOut() }
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .offset(y = setInfo),
                contentAlignment = Alignment.Center
            ) {
                when (loginState) {
                    is LoginState.Loading -> {
                        Log.d("LoginScreen", "Exibindo Loading")
                        CircleLoading(Color.White)
                    }
                    is LoginState.Success -> {
                        Log.d("LoginScreen", "Exibindo MensagemSuccess")
                        MensagemSuccess(successMessage, onLoginSuccess)
                    }
                    is LoginState.Error -> {
                        Log.d("LoginScreen", "Exibindo MensagemError")
                        MensagemError(errorMessage, repeatMessage, changeScreen)
                    }
                    else -> {
                        Log.d("LoginScreen", "Nenhum estado ativo")
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = setScreen)
                        .height(sizeScreen)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(
                                shapeDown,
                                shapeDown,
                                shapeUp,
                                shapeUp
                            )
                        )
                )
                this@Column.AnimatedVisibility(
                    visible = !changeScreen,
                    enter = fadeIn(animationSpec = tween(animDuration + 200)),
                    exit = fadeOut(animationSpec = tween(animDuration - 100))
                ) {
                    SignInScreen(
                        loginViewModel = loginViewModel,
                        sizeScreen = halfScreen,
                        onSignInClick = {
                            repeatMessage = !repeatMessage
                            if (loginState != LoginState.Loading) {
                                onSignInClick()
                            }
                        },
                        onSignUpClick = {
                            changeScreen = !changeScreen
                        }
                    )
                }
                this@Column.AnimatedVisibility(
                    visible = changeScreen,
                    enter = fadeIn(animationSpec = tween(animDuration + 200)),
                    exit = fadeOut(animationSpec = tween(animDuration - 100))
                ) {
                    SignUpScreen(
                        loginViewModel = loginViewModel,
                        onSignUpClick = {
                            repeatMessage = !repeatMessage
                            onSignUpClick()
                        },
                        onSignInClick = {
                            changeScreen = !changeScreen
                        }
                    )
                }
            }
        }
    }
}