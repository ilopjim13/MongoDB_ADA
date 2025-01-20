package com.es.ejercicios.console

class Console() {

    fun showMenu() {
        println("1. Busqueda por género")
        println("2. Registrar nuevo Juego")
        println("3. Eliminar por género")
        println("4. Modificar juego")
        println("5. Salir")
    }

    fun showMessage(message:String) {
        println(message)
    }

    fun getOption():Int {
        var option:Int

        do {
            print("-- Selecciona una opcion válida (1-5): ")
            option = readln().toIntOrNull() ?: -1
        } while (option !in (1..5))

        return option

    }

    fun getGenero():String {
        print("Introduce un género: ")
         return readln()
    }

    fun getTitulo():String {
        print("Introduce un título: ")
        return readln()
    }

    fun getPrecio():Double {
        var precio:Double
        do {
            print("Introduce un precio: ")
            precio = readln().toDoubleOrNull() ?: -1.0
        } while (precio < 0)

        return precio

    }



}