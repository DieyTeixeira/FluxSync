package com.dieyteixeira.fluxsync.ui.login.viewmodel

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dieyteixeira.fluxsync.app.repository.AuthRepository
import com.dieyteixeira.fluxsync.ui.login.state.LoginState
import com.dieyteixeira.fluxsync.ui.login.state.LoginUiState
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> get() = _loginState

    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState = _loginUiState.asStateFlow()

    fun onEmailChange(newEmail: String) {
        _loginUiState.value = _loginUiState.value.copy(email = newEmail)
    }

    fun onPasswordChange(newPassword: String) {
        _loginUiState.value = _loginUiState.value.copy(password = newPassword)
    }

    fun onConfirmPasswordChange(newPassword: String) {
        _loginUiState.value = _loginUiState.value.copy(confirmPassword = newPassword)
    }

    // Login (Entrar)
    fun signIn() {
        val email = _loginUiState.value.email
        val password = _loginUiState.value.password

        if (!validateField(email.isEmpty(), "Por favor, insira seu email")) return
        if (!validateField(!Patterns.EMAIL_ADDRESS.matcher(email).matches(), "Formato de email inválido")) return
        if (!validateField(password.isEmpty(), "Por favor, insira sua senha")) return
        if (!validateField(password.length < 8, "A senha deve ter no mínimo 8 caracteres")) return

        _loginState.value = LoginState.Loading

        viewModelScope.launch {
            try {
                Log.d("LoginViewModel", "Iniciando login...")
                _loginState.value = authRepository.signIn(email, password)
                Log.d("LoginViewModel", "Login concluído com sucesso.")
            } catch (e: FirebaseAuthException) {
                Log.e("LoginViewModel", "Erro de autenticação: ${e.errorCode}")
                _loginState.value = LoginState.Error(e.errorCode)
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Erro desconhecido: ${e.message}")
                _loginState.value = LoginState.Error("Erro desconhecido. Tente novamente mais tarde.")
            }
        }
    }

    // Sign Up (Criar conta)
    fun signUp() {
        val email = _loginUiState.value.email
        val password = _loginUiState.value.password

        if (!validateField(email.isEmpty(), "Por favor, insira seu email")) return
        if (!validateField(!Patterns.EMAIL_ADDRESS.matcher(email).matches(), "Formato de email inválido")) return

        _loginState.value = LoginState.Loading
        viewModelScope.launch {
            _loginState.value = authRepository.signUp(email, password)
        }
    }

    // Logout (Sair)
    fun signOut() {
        authRepository.signOut()
        _loginUiState.value = LoginUiState()
        _loginState.value = LoginState.LoggedOut
    }

    private fun validateField(condition: Boolean, message: String): Boolean {
        return if (condition) {
            _loginState.value = LoginState.Error(message)
            false
        } else {
            true
        }
    }
}