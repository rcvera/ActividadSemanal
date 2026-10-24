package suda.com.taller.Bicicletas.dto


data class ClienteDto(
    val nombre: String,
    val telefono: String
)

data class NuevaReparacion(
    val bicicleta: String,
    val cliente: ClienteDto,
    val repuestos: List<String> = emptyList(),
    val costo: Double
)
