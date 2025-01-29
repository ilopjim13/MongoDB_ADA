package com.es.relacionesmongo.usuarioDireccion

import java.util.*

data class Comentario(
    val nombreUsuario:String,
    val noticia:String,
    val comentario:String,
    val fechaHora: Date
)