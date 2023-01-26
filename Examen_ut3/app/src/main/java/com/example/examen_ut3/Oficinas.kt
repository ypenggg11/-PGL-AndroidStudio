package com.example.examen_ut3

data class Oficinas(val cod_oficina: String, val nombre: String, val direccion: String, val localidad: String) {
    override fun toString(): String {
        return "Oficinas( Id: '$cod_oficina', Name: '$nombre', Direccion: '$direccion', Localidad: '$localidad' )"
    }
}
