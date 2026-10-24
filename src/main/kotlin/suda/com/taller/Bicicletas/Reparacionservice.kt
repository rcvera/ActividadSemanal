package suda.com.taller.Bicicletas.service

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import suda.com.taller.Bicicletas.dto.NuevaReparacion
import suda.com.taller.Bicicletas.model.Cliente
import suda.com.taller.Bicicletas.model.Reparacion
import suda.com.taller.Bicicletas.repository.ReparacionRepository

@Service
class ReparacionService(
    private val repository: ReparacionRepository
) {

    fun listar(): List<Reparacion> = repository.findAll()

    fun listarPorEstado(lista: Boolean): List<Reparacion> = repository.findByLista(lista)

    fun buscarPorCliente(nombre: String): List<Reparacion> =
        repository.findByClienteNombreContainingIgnoreCase(nombre)

    fun obtener(id: Int): Reparacion =
        repository.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Reparación $id no encontrada")
        }

    @Transactional
    fun crear(dto: NuevaReparacion): Reparacion {
        validar(dto)
        val reparacion = Reparacion(
            bicicleta = dto.bicicleta,
            cliente = Cliente(nombre = dto.cliente.nombre, telefono = dto.cliente.telefono),
            repuestos = dto.repuestos.toMutableList(),
            costo = dto.costo,
            lista = false
        )
        return repository.save(reparacion)
    }

    @Transactional
    fun actualizar(id: Int, dto: NuevaReparacion): Reparacion {
        validar(dto)
        val r = obtener(id)
        r.bicicleta = dto.bicicleta
        r.cliente.nombre = dto.cliente.nombre
        r.cliente.telefono = dto.cliente.telefono
        r.repuestos = dto.repuestos.toMutableList()
        r.costo = dto.costo
        return repository.save(r)
    }

    @Transactional
    fun marcarLista(id: Int): Reparacion {
        val r = obtener(id)
        r.lista = true
        return repository.save(r)
    }

    @Transactional
    fun eliminar(id: Int) {
        if (!repository.existsById(id)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Reparación $id no encontrada")
        }
        repository.deleteById(id)
    }

    private fun validar(dto: NuevaReparacion) {
        if (dto.bicicleta.isBlank())
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "La bicicleta es obligatoria")
        if (dto.cliente.nombre.isBlank())
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre del cliente es obligatorio")
        if (dto.costo < 0)
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "El costo no puede ser negativo")
    }
}