package edu.ucne.morenofootball.domain.productos.models

data class Producto(
    val productoId: Int = 0,
    val nombre: String = "",
    val descripcion: String = "",
    val tipoProducto: Int = 1,
    val imagenUrl: String = "",
    val precio: Double = 0.0,
    val stock: Int = 0,
    val itbis: Double = 0.0,
    val estaDisponible: Boolean = false,
    val estaActivo: Boolean = true,
)