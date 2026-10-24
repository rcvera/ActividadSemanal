package suda.com.taller.Bicicletas.model

import jakarta.persistence.*

@Entity
@Table(name = "clientes")
class Cliente(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column(nullable = false)
    var nombre: String = "",

    @Column(nullable = false)
    var telefono: String = ""
)
