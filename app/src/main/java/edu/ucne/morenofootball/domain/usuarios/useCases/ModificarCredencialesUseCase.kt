package edu.ucne.morenofootball.domain.usuarios.useCases

import edu.ucne.morenofootball.domain.usuarios.UsuarioRepository
import edu.ucne.morenofootball.domain.usuarios.models.ModificarCredenciales
import edu.ucne.morenofootball.utils.Resource
import javax.inject.Inject

class ModificarCredencialesUseCase @Inject constructor(
    private val repo: UsuarioRepository,
) {
    suspend operator fun invoke(credenciales: ModificarCredenciales): Resource<Unit> =
        repo.modificarCredenciales(credenciales)
}