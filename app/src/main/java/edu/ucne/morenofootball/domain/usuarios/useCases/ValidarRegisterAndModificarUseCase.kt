package edu.ucne.morenofootball.domain.usuarios.useCases

import edu.ucne.morenofootball.domain.usuarios.models.Register
import edu.ucne.morenofootball.utils.ValidationResult
import javax.inject.Inject

class ValidarRegisterAndModificarUseCase @Inject constructor(){
    operator fun invoke(credenciales: Register): ValidationResult {
        val mensajeEmailValidado = validarEmail(credenciales.email)
        val mensajeClaveValidada = validarClaveSegura(credenciales.password)

        return when {
            !mensajeEmailValidado.isNullOrBlank() -> ValidationResult(esValido = false, error = mensajeEmailValidado)
            !mensajeClaveValidada.isNullOrBlank() -> ValidationResult(esValido = false, error = mensajeClaveValidada)
            credenciales.username.isBlank() -> ValidationResult(esValido = false, error = "Este campo es obligatorio *")
            credenciales.username.length < 3 -> ValidationResult(esValido = false, error = "Este campo debe tener mínimo 3 caracteres *")
            else -> ValidationResult(true)
        }
    }
}