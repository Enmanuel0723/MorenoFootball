package edu.ucne.morenofootball.data.productos

import edu.ucne.morenofootball.data.productos.remote.dto.request.ProductoRequest
import edu.ucne.morenofootball.data.productos.remote.dto.request.ProductoRequestEdit
import edu.ucne.morenofootball.data.productos.remote.dto.response.ProductoResponse
import edu.ucne.morenofootball.domain.productos.models.Producto
import edu.ucne.morenofootball.domain.productos.models.ProductoReq
import edu.ucne.morenofootball.domain.productos.models.ProductoReqEdit

fun ProductoReq.toDto() = ProductoRequest(
    nombre,
    descripcion,
    imagenUrl,
    precio,
    tipoProducto,
    stock
)

fun ProductoReqEdit.toDto() = ProductoRequestEdit(
    productoId,
    nombre,
    descripcion,
    imagenUrl,
    precio,
    tipoProducto,
    stock
)

fun ProductoResponse.toDomain() = Producto(
    productoId,
    nombre,
    descripcion,
    tipoProducto,
    imagenUrl,
    precio,
    stock,
    itbis,
    estaDisponible,
    estaActivo,
)