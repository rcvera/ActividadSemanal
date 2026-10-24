package suda.com.taller.Bicicletas.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import suda.com.taller.Bicicletas.model.Reparacion

@Repository
interface ReparacionRepository : JpaRepository<Reparacion, Int> {
    fun findByLista(lista: Boolean): List<Reparacion>
    fun findByClienteNombreContainingIgnoreCase(nombre: String): List<Reparacion>
}
