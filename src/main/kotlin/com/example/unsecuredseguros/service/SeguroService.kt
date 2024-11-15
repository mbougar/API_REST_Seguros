package com.example.unsecuredseguros.service

import com.example.unsecuredseguros.model.Seguro
import com.example.unsecuredseguros.repository.SeguroRepository
import com.example.unsecuredseguros.utilis.DniValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class SeguroService() {

    @Autowired
    private lateinit var seguroRepository: SeguroRepository

    fun getById(id: String): Seguro? {
        val idL = id.toLongOrNull() ?: throw ResponseStatusException(
            HttpStatus.BAD_REQUEST, "ID inválido: $id no es un número válido"
        )

        return seguroRepository.findByIdOrNull(idL)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Seguro no encontrado con ID: $idL")
    }

    fun crearSeguro(seguro: Seguro): Seguro? {

        validarSeguro(seguro)

        return if (seguroRepository.findByIdOrNull(seguro.idSeguro.toLong()) == null) {
            seguroRepository.save(seguro)
        } else {
            null
        }
    }

    fun actualizarSeguro(id: String, seguro: Seguro): Seguro? {

        validarSeguro(seguro)

        val idL = id.toLongOrNull() ?: throw ResponseStatusException(
            HttpStatus.BAD_REQUEST, "ID inválido: $id no es un número válido"
        )

        if (seguro.idSeguro.toLong() != idL) return null

        return if (seguroRepository.findByIdOrNull(seguro.idSeguro.toLong()) != null) {
            seguroRepository.save(seguro)
        } else {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Seguro no encontrado con ID: $idL")
        }
    }

    fun obtenerTodosLosSeguros(): List<Seguro> {
        return seguroRepository.findAll()
    }

    fun eliminarSeguro(id: Long): ResponseEntity<Unit> {
        if (!seguroRepository.existsById(id)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Seguro no encontrado")
        }
        seguroRepository.deleteById(id)
        return ResponseEntity.noContent().build()
    }

    fun validarSeguro(seguro: Seguro) {
        if (!DniValidator.esDniValido(seguro.nif)) throw ResponseStatusException(HttpStatus.BAD_REQUEST,"El DNI introducido no es válido")
        if (seguro.edad in 0..17) throw ResponseStatusException(HttpStatus.BAD_REQUEST,"No es posible ser menor de edad para hacer un seguro")
        if (seguro.numHijos < 0) throw ResponseStatusException(HttpStatus.BAD_REQUEST,"El número de hijos no puede ser menor que 0")
        if (!seguro.casado) {
            if (seguro.numHijos > 0) throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Si el estado civil es soltero, el número de hijos debe ser 0")
        }
        if (seguro.embarazada) {
            if (seguro.sexo != "Mujer") throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Si está embarazada, el sexo debe ser Mujer")
        }
    }
}