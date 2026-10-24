package suda.com.taller.Bicicletas.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import suda.com.taller.Bicicletas.dto.NuevaReparacion
import suda.com.taller.Bicicletas.model.Reparacion
import suda.com.taller.Bicicletas.service.ReparacionService

@RestController
@RequestMapping("/api/reparaciones")
class ReparacionController(
    private val service: ReparacionService
) {

    // GET /api/reparaciones            -> todas
    // GET /api/reparaciones?lista=true -> solo terminadas
    // GET /api/reparaciones?cliente=juan
    @GetMapping
    fun listar(
        @RequestParam(required = false) lista: Boolean?,
        @RequestParam(required = false) cliente: String?
    ): List<Reparacion> = when {
        lista != null -> service.listarPorEstado(lista)
        !cliente.isNullOrBlank() -> service.buscarPorCliente(cliente)
        else -> service.listar()
    }

    @GetMapping("/{id}")
    fun obtener(@PathVariable id: Int): Reparacion = service.obtener(id)

    @PostMapping
    fun crear(@RequestBody dto: NuevaReparacion): ResponseEntity<Reparacion> =
        ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto))

    @PutMapping("/{id}")
    fun actualizar(@PathVariable id: Int, @RequestBody dto: NuevaReparacion): Reparacion =
        service.actualizar(id, dto)

    @PatchMapping("/{id}/lista")
    fun marcarLista(@PathVariable id: Int): Reparacion = service.marcarLista(id)

    @DeleteMapping("/{id}")
    fun eliminar(@PathVariable id: Int): ResponseEntity<Void> {
        service.eliminar(id)
        return ResponseEntity.noContent().build()
    }
}