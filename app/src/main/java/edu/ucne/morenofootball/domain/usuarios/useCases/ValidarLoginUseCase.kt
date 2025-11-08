package edu.ucne.morenofootball.domain.usuarios.useCases

import edu.ucne.morenofootball.domain.usuarios.models.Login
import edu.ucne.morenofootball.utils.ValidationResult
import javax.inject.Inject

class ValidarLoginUseCase @Inject constructor() {
    operator fun invoke(credenciales: Login): ValidationResult {
        return when {
            credenciales.email.isBlank() -> ValidationResult(esValido = false, error = "Este campo es obligatorio *")
            credenciales.password.isBlank() -> ValidationResult(esValido = false, error = "Este campo es obligatorio *")
            else -> ValidationResult(esValido = true)
        }
    }
}