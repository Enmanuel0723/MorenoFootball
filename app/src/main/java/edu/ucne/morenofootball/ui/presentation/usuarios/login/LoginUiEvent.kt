package edu.ucne.morenofootball.ui.presentation.usuarios.login

interface LoginUiEvent {
    data class Login(val email: String, val clave: String): LoginUiEvent
    data class OnEmailChange(val email: String): LoginUiEvent
    data class OnPasswordChange(val password: String): LoginUiEvent
    data object OnTogglePasswordVisibility: LoginUiEvent
}