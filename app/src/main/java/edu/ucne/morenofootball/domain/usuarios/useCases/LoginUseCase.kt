package edu.ucne.morenofootball.domain.usuarios.useCases

import edu.ucne.morenofootball.domain.usuarios.UsuarioRepository
import edu.ucne.morenofootball.domain.usuarios.models.Login
import edu.ucne.morenofootball.utils.Resource
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repo: UsuarioRepository
) {
    suspend operator fun invoke(credenciales: Login): Resource<Unit> =
        repo.login(credenciales)
}