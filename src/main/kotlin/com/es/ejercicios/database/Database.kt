package com.es.ejercicios.database

import com.es.ejercicios.model.Juego
import com.mongodb.client.FindIterable
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates

class Database {

    fun busquedaGenero(genero: String): FindIterable<Juego>? {
        val database = ConexionMongo.getDatabase("ivan")
        val collection: MongoCollection<Juego> = database.getCollection("juegos", Juego::class.java)

        val filtro = Filters.eq("genero", genero)
        val resultadoFiltro = collection.find(filtro)
        resultadoFiltro.sortedBy { it.titulo }.forEach { juego ->
            println("Titulo: ${juego.titulo}\nGenero: ${juego.genero}\nPrecio: ${juego.precio}\nFecha: ${juego.fecha}\n--------------")
        }
        return resultadoFiltro
    }

    fun registrarJuego(juegoNuevo: Juego) {
        val database = ConexionMongo.getDatabase("ivan")
        val collection: MongoCollection<Juego> = database.getCollection("juegos", Juego::class.java)
        collection.insertOne(juegoNuevo)
    }

    fun comprobarJuegoRepetido(titulo: String): Boolean {
        val database = ConexionMongo.getDatabase("ivan")
        val collection: MongoCollection<Juego> = database.getCollection("juegos", Juego::class.java)
        val filtro = Filters.eq("titulo", titulo)
        val resultadoFiltro = collection.find(filtro)
        return !resultadoFiltro.iterator().hasNext()
    }

    fun eliminarPorGenero(genero: String) {
        val database = ConexionMongo.getDatabase("ivan")
        val collection: MongoCollection<Juego> = database.getCollection("juegos", Juego::class.java)
        val filter = Filters.eq("genero", genero)
        collection.deleteOne(filter).also {
            println(it)
        }
    }

    fun modificarJuego(nombreJuego: String, juegoModificado: Juego) {
        val database = ConexionMongo.getDatabase("ivan")
        val collection: MongoCollection<Juego> = database.getCollection("juegos", Juego::class.java)

        val filtroUpdate = Filters.eq("titulo", nombreJuego)
        val updateJuego = Updates.combine(
            Updates.set("genero", juegoModificado.genero),
            Updates.set("precio", juegoModificado.precio),
        )
        collection.updateOne(filtroUpdate, updateJuego).also {
            println(it)
        }
    }
}