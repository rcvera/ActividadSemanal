package suda.com.taller.Bicicletas.model

import jakarta.persistence.*

@Entity
@Table(name = "reparaciones")
class Reparacion(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column(nullable = false)
    var bicicleta: String = "",

    // Al guardar la reparación también se guarda el cliente
    @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE], fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id", nullable = false)
    var cliente: Cliente = Cliente(),

    // Lista simple de repuestos en una tabla aparte
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "reparacion_repuestos", joinColumns = [JoinColumn(name = "reparacion_id")])
    @Column(name = "repuesto")
    var repuestos: MutableList<String> = mutableListOf(),

    @Column(nullable = false)
    var costo: Double = 0.0,

    @Column(nullable = false)
    var lista: Boolean = false
)
