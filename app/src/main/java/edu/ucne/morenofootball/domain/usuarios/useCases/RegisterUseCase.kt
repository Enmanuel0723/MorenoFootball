package edu.ucne.morenofootball.domain.usuarios.useCases

import edu.ucne.morenofootball.domain.usuarios.UsuarioRepository
import edu.ucne.morenofootball.domain.usuarios.models.Register
import edu.ucne.morenofootball.utils.Resource
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repo: UsuarioRepository,
) {
    suspend operator fun invoke(credenciales: Register): Resource<Unit> =
        repo.register(credenciales)
}