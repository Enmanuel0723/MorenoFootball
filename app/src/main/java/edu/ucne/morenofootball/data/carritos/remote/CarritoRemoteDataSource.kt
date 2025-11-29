package edu.ucne.morenofootball.data.carritos.remote

import edu.ucne.morenofootball.data.carritos.remote.dto.request.AgregarProductoParams
import edu.ucne.morenofootball.data.carritos.remote.dto.request.ActionWithProductFromCardParams
import edu.ucne.morenofootball.data.carritos.remote.dto.request.UserOrSessionIdParams
import edu.ucne.morenofootball.data.carritos.remote.dto.response.CarritoResponse
import edu.ucne.morenofootball.data.usuarios.remote.UsuarioRemoteDataSource.Companion.NETWORK_ERROR
import edu.ucne.morenofootball.utils.Resource
import okio.IOException
import retrofit2.Response
import javax.inject.Inject

class CarritoRemoteDataSource @Inject constructor(
    private val api: CarritoApiService
) {
    private suspend fun <T, R> executeApiCall(
        apiCall: suspend (T) -> Response<R>,
        request: T,
        networkError: String = NETWORK_ERROR
    ): Resource<R> =
        try {
            val response = apiCall(request)
            if (response.isSuccessful)
                response.body()?.let { Resource.Success(it) }
                    ?: Resource.Error("Respuesta vacía del servidor")
            else Resource.Error(response.message())
        } catch (e: IOException) {
            Resource.Error(networkError)
        }

    suspend fun listByUsuarioId(params: UserOrSessionIdParams): Resource<List<CarritoResponse>> =
        executeApiCall(
            apiCall = { api.listByUsuarioId(params) },
            request = Unit,
        )
    suspend fun getTotalCarrito(params: UserOrSessionIdParams): Resource<Double> =
        executeApiCall(
            apiCall = { api.getTotalCarrito(params) },
            request = Unit,
        )
    suspend fun agregarProducto(params: AgregarProductoParams): Resource<CarritoResponse> =
        executeApiCall(
            apiCall = { api.agregarProducto(it) },
            request = params
        )
    suspend fun aumentarCantidad(params: ActionWithProductFromCardParams): Resource<CarritoResponse> =
        executeApiCall(
            apiCall = { api.aumentarCantidad(it) },
            request = params
        )
    suspend fun disminuirCantidad(params: ActionWithProductFromCardParams): Resource<CarritoResponse> =
        executeApiCall(
            apiCall = { api.disminuirCantidad(it) },
            request = params
        )

    suspend fun vaciarCarrito(params: UserOrSessionIdParams): Resource<Unit> =
        executeApiCall(
            apiCall = { api.vaciarCarrito(params) },
            request = Unit
        )

    suspend fun deleteProduct(params: ActionWithProductFromCardParams): Resource<Unit> =
        executeApiCall(
            apiCall = { api.deleteProduct(params) },
            request = Unit
        )
}
