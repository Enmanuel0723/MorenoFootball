package edu.ucne.morenofootball.ui.presentation.miCuenta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.morenofootball.domain.usuarios.useCases.UsuarioUseCases
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MiCuentaViewModel @Inject constructor(
    private val usuarioUseCases: UsuarioUseCases
) : ViewModel() {
    private val _state = MutableStateFlow(MiCuentaUiState())
    val state = _state.asStateFlow()

    private val _navigateToLogin = MutableSharedFlow<Unit>()
    val navigateToLogin = _navigateToLogin.asSharedFlow()

    init {
        getUsername()
    }

    fun onEvent(event: MiCuentaUiEvent) {
        when (event) {
            is MiCuentaUiEvent.Logout -> logout()
        }
    }

    private fun logout() {
        viewModelScope.launch {
            usuarioUseCases.logoutUseCase()
            _navigateToLogin.emit(Unit)
        }
    }

    private fun getUsername() {
        viewModelScope.launch {
            usuarioUseCases.getUsuarioLoggeadoUseCase()?.let {
                _state.value = _state.value.copy(username = it.username)
            }
        }
    }
}
