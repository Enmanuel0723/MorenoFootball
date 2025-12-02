package edu.ucne.morenofootball.domain.usuarios.useCases

import edu.ucne.morenofootball.domain.usuarios.UsuarioRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repo: UsuarioRepository
) {
    suspend operator fun invoke() = repo.logout()
}