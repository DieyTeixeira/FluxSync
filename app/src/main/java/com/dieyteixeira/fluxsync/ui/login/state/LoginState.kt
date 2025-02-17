package com.dieyteixeira.fluxsync.ui.login.state

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    object LoggedIn : LoginState()
    object LoggedOut : LoginState()
    data class Success(val userId: String) : LoginState()
    data class Error(val message: String) : LoginState()
}

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val changeScreen: Boolean = false,
)