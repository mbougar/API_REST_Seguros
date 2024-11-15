package com.example.unsecuredseguros.model

import jakarta.persistence.*
import jakarta.validation.constraints.Min
import java.util.*

@Entity
@Table(name = "seguros")
data class Seguro(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idSeguro")
    val idSeguro: Int = 0,

    @Column(name = "nif", nullable = false, length = 10)
    val nif: String,

    @Column(name = "nombre", nullable = false, length = 100)
    val nombre: String,

    @Column(name = "ape1", nullable = false, length = 100)
    val ape1: String,

    @Column(name = "ape2", length = 100)
    val ape2: String? = null,

    @Column(name = "edad", nullable = false)
    @Min(1)
    val edad: Int,

    @Column(name = "num_hijos", nullable = false)
    @Min(0)
    val numHijos: Int,

    @Column(name = "fecha_creacion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    val fechaCreacion: Date,

    @Column(name = "sexo", nullable = false, length = 10)
    val sexo: String,

    @Column(name = "casado", nullable = false)
    val casado: Boolean,

    @Column(name = "embarazada", nullable = false)
    val embarazada: Boolean
)