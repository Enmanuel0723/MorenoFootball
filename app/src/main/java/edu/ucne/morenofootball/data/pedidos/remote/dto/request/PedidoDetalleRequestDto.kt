package edu.ucne.morenofootball.data.pedidos.remote.dto.request

data class PedidoDetalleRequestDto(
    val detalleId: Int,
    val pedidoId: Int,
    val productoId: Int,
    val cantidad: Int,
)
