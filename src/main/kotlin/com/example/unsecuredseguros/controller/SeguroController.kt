package com.example.unsecuredseguros.controller

import com.example.unsecuredseguros.model.Seguro
import com.example.unsecuredseguros.service.SeguroService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/seguros")
class SeguroController() {

    @Autowired
    private lateinit var seguroService: SeguroService

    @GetMapping("/{id}")
    fun getById(
        @PathVariable id: String?
    ): Seguro?{

        if (id.isNullOrEmpty()){
            return null
        }

        return seguroService.getById(id)
    }

    @PostMapping
    fun crearSeguro(@RequestBody seguro: Seguro): ResponseEntity<Seguro> {
        return ResponseEntity.ok(seguroService.crearSeguro(seguro))
    }

    @PutMapping("/{id}")
    fun actualizarSeguro(@PathVariable id: String, @RequestBody seguro: Seguro): ResponseEntity<Seguro> {
        return ResponseEntity.ok(seguroService.actualizarSeguro(id, seguro))
    }

    @GetMapping
    fun obtenerTodosLosSeguros(): ResponseEntity<List<Seguro>> {
        return ResponseEntity.ok(seguroService.obtenerTodosLosSeguros())
    }

    @DeleteMapping("/{id}")
    fun eliminarSeguro(@PathVariable id: Long): ResponseEntity<Unit> {
        seguroService.eliminarSeguro(id)
        return ResponseEntity.noContent().build()
    }
}