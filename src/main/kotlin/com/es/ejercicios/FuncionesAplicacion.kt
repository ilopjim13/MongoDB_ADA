package com.es.ejercicios

import com.es.ejercicios.console.Console
import com.es.ejercicios.database.Database
import com.es.ejercicios.model.Juego
import java.util.*

fun executeMenu(option:Int, database: Database, console: Console) {
    when (option) {
        1 -> buscarGenero(console, database)
        2 -> registrarJuego(console, database)
        3 -> eliminarJuegoGenero(console, database)
        4 -> modificarJuego(console, database)
    }
}

fun buscarGenero(console: Console, database: Database) {
    val genero = console.getGenero()
    database.busquedaGenero(genero)
}

fun registrarJuego(console: Console, database: Database) {
    val titulo = console.getTitulo()
    val genero = console.getGenero()
    val precio = console.getPrecio()
    if (database.comprobarJuegoRepetido(titulo)) database.registrarJuego(Juego(titulo, genero, precio, Date()))
    else console.showMessage("Este juego ya existe en la base de datos")
}

fun eliminarJuegoGenero(console: Console, database: Database) {
    val genero = console.getGenero()
    database.eliminarPorGenero(genero)
}

fun modificarJuego(console: Console, database: Database) {
    println("Indica el juego que vas a modificar: ")
    val nombreJuego = console.getTitulo()

    println("Actualiza sus parametros")
    val genero = console.getGenero()
    val precio = console.getPrecio()
    database.modificarJuego(nombreJuego, Juego(nombreJuego, genero, precio, Date()))
}