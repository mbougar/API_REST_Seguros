package com.example.unsecuredseguros.utilis

object DniValidator {

    fun esDniValido(dni: String): Boolean {

        val dniRegex = Regex("^\\d{8}[A-Z]$")
        if (!dniRegex.matches(dni)) {
            return false
        }

        val numero = dni.substring(0, 8).toInt()
        val letra = dni.last()

        val letras = "TRWAGMYFPDXBNJZSQVHLCKE"
        val letraCorrecta = letras[numero % 23]

        return letra == letraCorrecta
    }
}