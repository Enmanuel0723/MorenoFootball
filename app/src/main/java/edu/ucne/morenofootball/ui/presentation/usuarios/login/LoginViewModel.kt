package edu.ucne.morenofootball.ui.presentation.usuarios.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.morenofootball.domain.usuarios.models.Login
import edu.ucne.morenofootball.domain.usuarios.useCases.UsuarioUseCases
import edu.ucne.morenofootball.utils.Resource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val usuarioUseCases: UsuarioUseCases,
) : ViewModel() {
    private val _state = MutableStateFlow(LoginUiState())
    val state = _state.asStateFlow()

    private val _navigateToHome = MutableSharedFlow<Unit>()
    val navigateToHome = _navigateToHome.asSharedFlow()

    init {
        getUsuarioLoggeado()
    }

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.Login -> login(event.email, event.clave)
            is LoginUiEvent.OnPasswordChange -> _state.update { it.copy(password = event.password, errorPass = null) }
            is LoginUiEvent.OnEmailChange -> _state.update { it.copy(email = event.email, errorEmail = null) }
            is LoginUiEvent.OnTogglePasswordVisibility -> _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
        }
    }

    private fun getUsuarioLoggeado() {
        viewModelScope.launch {
            val user = usuarioUseCases.getUsuarioLoggeadoUseCase()
            if(user != null) {
                _navigateToHome.emit(Unit)
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun login(email: String, password: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val emailValidation = usuarioUseCases.validarLoginRegisterUseCase.validarEmail(email)
            val passwordValidation = usuarioUseCases.validarLoginRegisterUseCase.validarPassword(password)
            if (!emailValidation.esValido || !passwordValidation.esValido) {
                _state.update {
                    it.copy(
                        errorEmail = emailValidation.error,
                        errorPass = passwordValidation.error,
                        isLoading = false
                    )
                }
                return@launch
            }

            val result = usuarioUseCases.loginUseCase(
                Login(
                    email = email,
                    password = password
                )
            )
            when (result) {
                is Resource.Error<*> -> _state.update { it.copy(message = result.message, isLoading = false) }
                is Resource.Loading<*> -> _state.update { it.copy(message = "", isLoading = true) }
                is Resource.Success<*> -> {
                    Log.d("LoginViewModel", "Info de sesion: ${result.data}")
                    _navigateToHome.emit(Unit)
                    _state.update {
                        it.copy(
                            message = "¡Sesión iniciada!",
                            isLoading = false,
                            errorPass = null,
                            errorEmail = null
                        )
                    }
                }
            }
        }
    }
}