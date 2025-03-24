package com.dieyteixeira.fluxsync.ui.login.screen

import android.util.Patterns
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieyteixeira.fluxsync.ui.login.components.ErrorBoxSignIn
import com.dieyteixeira.fluxsync.ui.login.viewmodel.LoginViewModel

@Composable
fun SignInScreen(
    loginViewModel: LoginViewModel,
    sizeScreen: Dp,
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit
) {

    val focusManager = LocalFocusManager.current
    val loginUiState by loginViewModel.loginUiState.collectAsState()

    var passwordVisible by remember { mutableStateOf(false) }
    var hasEmailError by remember { mutableStateOf(false) }
    var hasPasswordError by remember { mutableStateOf(false) }
    var focusedField by remember { mutableStateOf("") }
    var offsetValue by remember { mutableStateOf(0.dp) }
    val offsetY by animateDpAsState(offsetValue, tween(500))
    var messageError by remember { mutableStateOf("") }

    LaunchedEffect(focusedField, hasEmailError, hasPasswordError) {
        messageError = when {
            focusedField == "password" && hasPasswordError -> {
                offsetValue = 75.dp
                "A senha deve ter no mínimo 8 caracteres"
            }
            focusedField == "email" && hasEmailError -> {
                offsetValue = 10.dp
                "Formato de email inválido"
            }
            else -> {
                offsetValue = 0.dp
                ""
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = null,
                indication = null
            ) {
                focusedField = ""
                focusManager.clearFocus()
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(sizeScreen)
        )
        Box {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = loginUiState.email,
                    onValueChange = {
                        loginViewModel.onEmailChange(it)
                        hasEmailError = it.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(it).matches()
                        if (focusedField != "password") focusedField = "password"
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(
                            Color.White,
                            RoundedCornerShape(30)
                        ),
                    placeholder = {
                        Text(
                            text = "Email",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Gray,
                            fontSize = 16.sp,
                            lineHeight = 60.sp
                        )
                    },
                    leadingIcon = {
                        Image(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Ícone de email",
                            colorFilter = ColorFilter.tint(Color.DarkGray)
                        )
                    },
                    shape = RoundedCornerShape(30),
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        color = Color.Black,
                        fontSize = 16.sp,
                        lineHeight = 60.sp
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.LightGray,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color.LightGray.copy(alpha = 0.5f),
                        unfocusedContainerColor = Color.LightGray.copy(alpha = 0.5f),
                        disabledIndicatorColor = Color.Gray,
                        errorIndicatorColor = Color.Red
                    )
                )
                OutlinedTextField(
                    value = loginUiState.password,
                    onValueChange = {
                        loginViewModel.onPasswordChange(it)
                        hasPasswordError = it.isNotEmpty() && it.length < 8
                        if (focusedField != "email") focusedField = "email"
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(
                            Color.White,
                            RoundedCornerShape(30)
                        ),
                    placeholder = {
                        Text(
                            text = "Senha",
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 16.sp,
                            lineHeight = 60.sp
                        )
                    },
                    leadingIcon = {
                        Image(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Ícone de senha",
                            colorFilter = ColorFilter.tint(Color.DarkGray)
                        )
                    },
                    trailingIcon = {
                        val image =
                            if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                        val description = if (passwordVisible) "Ocultar senha" else "Mostrar senha"

                        Image(
                            imageVector = image,
                            contentDescription = description,
                            colorFilter = ColorFilter.tint(Color.DarkGray),
                            modifier = Modifier.clickable { passwordVisible = !passwordVisible }
                        )
                    },
                    shape = RoundedCornerShape(30),
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        color = Color.Black,
                        fontSize = 16.sp,
                        lineHeight = 60.sp
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.LightGray,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color.LightGray.copy(alpha = 0.5f),
                        unfocusedContainerColor = Color.LightGray.copy(alpha = 0.5f),
                        disabledIndicatorColor = Color.Gray,
                        errorIndicatorColor = Color.Red
                    ),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(
                            MaterialTheme.colorScheme.surfaceContainerLowest,
                            RoundedCornerShape(30)
                        )
                        .clickable(
                            interactionSource = null,
                            indication = null
                        ) {
                            focusedField = ""
//                            viewModel.signIn()
                            onSignInClick()
                        }
                ) {
                    Text(
                        text = "Entrar",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                Text(
                    text = "Esqueceu a senha?",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    modifier = Modifier
                        .align(Alignment.End)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp)
                            .background(Color.LightGray)
                    ) {}
                    Text(
                        text = "Ou",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp)
                            .background(Color.LightGray)
                    ) {}
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.surfaceContainerLowest,
                            RoundedCornerShape(30)
                        )
                        .clickable(
                            interactionSource = null,
                            indication = null
                        ) { onSignUpClick() }
                ) {
                    Text(
                        text = "Criar conta",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.surfaceContainerLowest,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            if (messageError.isNotEmpty()) {
                ErrorBoxSignIn(messageError, offsetY)
            }
        }
    }
}