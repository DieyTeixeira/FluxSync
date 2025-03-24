package com.dieyteixeira.fluxsync.ui.login.screen

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieyteixeira.fluxsync.app.theme.ColorError
import com.dieyteixeira.fluxsync.app.theme.ColorSuccess
import com.dieyteixeira.fluxsync.ui.login.components.TextWithIcon
import com.dieyteixeira.fluxsync.ui.login.components.containsDigit
import com.dieyteixeira.fluxsync.ui.login.components.containsLowerCase
import com.dieyteixeira.fluxsync.ui.login.components.containsSpecialCharacter
import com.dieyteixeira.fluxsync.ui.login.components.containsUpperCase
import com.dieyteixeira.fluxsync.ui.login.viewmodel.LoginViewModel

@Composable
fun SignUpScreen(
    loginViewModel: LoginViewModel,
    onSignUpClick: () -> Unit,
    onSignInClick: () -> Unit
) {

    val focusManager = LocalFocusManager.current
    val loginUiState by loginViewModel.loginUiState.collectAsState()
    var passwordVisible by remember { mutableStateOf(false) }
    var confPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = null,
                indication = null
            ) {
                focusManager.clearFocus()
            }
    ) {

        // Espaçamento para a barra de notificações
        Box(modifier = Modifier.fillMaxWidth().height(40.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Cadastro de usuário",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Gray,
                fontSize = 20.sp
            )
            OutlinedTextField(
                value = loginUiState.email,
                onValueChange = { loginViewModel.onEmailChange(it) },
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
                onValueChange = { loginViewModel.onPasswordChange(it) },
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
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray,
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
                    val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
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
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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
                value = loginUiState.confirmPassword,
                onValueChange = { loginViewModel.onConfirmPasswordChange(it) },
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
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray,
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
                    val image = if (confPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    val description = if (confPasswordVisible) "Ocultar senha" else "Mostrar senha"

                    Image(
                        imageVector = image,
                        contentDescription = description,
                        colorFilter = ColorFilter.tint(Color.DarkGray),
                        modifier = Modifier.clickable { confPasswordVisible = !confPasswordVisible }
                    )
                },
                shape = RoundedCornerShape(30),
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.Black,
                    fontSize = 16.sp,
                    lineHeight = 60.sp
                ),
                visualTransformation = if (confPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.LightGray,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.LightGray.copy(alpha = 0.5f),
                    unfocusedContainerColor = Color.LightGray.copy(alpha = 0.5f),
                    disabledIndicatorColor = Color.Gray,
                    errorIndicatorColor = Color.Red
                )
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.8f),
                        horizontalAlignment = Alignment.Start
                    ) {
                        TextWithIcon(
                            textValue = "Sua senha deve conter no mínimo 8 caracteres",
                            iconName = if (loginUiState.password.length >= 8) Icons.Filled.CheckCircle else Icons.Filled.Cancel,
                            iconColor = if (loginUiState.password.length >= 8) ColorSuccess else ColorError
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.Start
                        ) {
                            TextWithIcon(
                                textValue = "1 letra maiúscula",
                                iconName = if (containsUpperCase(loginUiState.password)) Icons.Filled.CheckCircle else Icons.Filled.Cancel,
                                iconColor = if (containsUpperCase(loginUiState.password)) ColorSuccess else ColorError
                            )
                            TextWithIcon(
                                textValue = "1 letra minúscula",
                                iconName = if (containsLowerCase(loginUiState.password)) Icons.Filled.CheckCircle else Icons.Filled.Cancel,
                                iconColor = if (containsLowerCase(loginUiState.password)) ColorSuccess else ColorError
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(
                            horizontalAlignment = Alignment.Start
                        ) {
                            TextWithIcon(
                                textValue = "1 número",
                                iconName = if (containsDigit(loginUiState.password)) Icons.Filled.CheckCircle else Icons.Filled.Cancel,
                                iconColor = if (containsDigit(loginUiState.password)) ColorSuccess else ColorError
                            )
                            TextWithIcon(
                                textValue = "1 caractere especial",
                                iconName = if (containsSpecialCharacter(loginUiState.password)) Icons.Filled.CheckCircle else Icons.Filled.Cancel,
                                iconColor = if (containsSpecialCharacter(loginUiState.password)) ColorSuccess else ColorError
                            )
                        }
                    }
                }
            }
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
                        onSignUpClick()
//                        viewModel.signUp()
                    }
            ) {
                Text(
                    text = "Cadastrar",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
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
                    ) { onSignInClick() }
            ) {
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.surfaceContainerLowest,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}