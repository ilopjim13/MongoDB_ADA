package com.es.ejercicios

import com.es.ejercicios.console.Console
import com.es.ejercicios.database.ConexionMongo
import com.es.ejercicios.database.Database

fun main(){
    try {
        val console = Console()

        val database = Database()

        var option:Int

        do {
            console.showMenu()
            option = console.getOption()
            executeMenu(option, database, console)
        } while (option != 5)

    }catch (e: Exception) {
        println("Error al conectar a MongoDB: ${e.message}")
    } finally {
        ConexionMongo.close()
    }

}

