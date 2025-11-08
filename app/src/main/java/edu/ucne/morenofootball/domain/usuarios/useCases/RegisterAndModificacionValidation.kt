package edu.ucne.morenofootball.domain.usuarios.useCases

fun validarClaveSegura(clave: String): String? {
    if (clave.isBlank()) return "Este campo es obligatorio *"
    if (clave.length < 8) return "La clave debe tener al menos 8 caracteres *"
    if (!clave.any { it.isUpperCase() }) return "La clave debe contener al menos una letra mayúscula *"
    if (!clave.any { it.isLowerCase() }) return "La clave debe contener al menos una letra minúscula *"
    if (!clave.any { it.isDigit() }) return "La clave debe contener al menos un número *"
    if (!clave.any { "!@#$%^&*()-_=+[]{}|;:'\",.<>?/".contains(it) }) return "La clave debe contener al menos un carácter especial *"
    return null
}

fun validarEmail(email: String): String? {
    if (email.isBlank()) return "Este campo es obligatorio *"
    val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
    return if (emailRegex.matches(email)) "El email es válido *"
    else null
}