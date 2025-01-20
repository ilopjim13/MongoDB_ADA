package com.es.arraysmongo

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import com.mongodb.client.model.FindOneAndUpdateOptions
import com.mongodb.client.model.ReturnDocument
import com.mongodb.client.model.Updates
import org.bson.Document
import org.bson.types.ObjectId

fun main() {

    // 1º Sería conectarnos a la BD
    val database = ConexionMongo.getDatabase("dbada")

    // 2º Conseguir la coleccion
    val collection: MongoCollection<Juego> = database.getCollection("colljuego", Juego::class.java)

    // VOY A VACIAR LA COLECCIÓN
    collection.drop()

    // INSERCION
    val juegoNuevo = Juego(ObjectId(), "Alfonso", listOf<Int>(50, 70, 100, 90))

    collection.insertOne(juegoNuevo)

    val juegoNuevo2 = Juego(ObjectId(), "Alfonso", listOf<Int>(50, 70, 99, 90))
    val juegoNuevo3 = Juego(ObjectId(), "Maria", listOf<Int>(57, 74, 100, 90))
    val juegoNuevo4 = Juego(ObjectId(), "Jose", listOf<Int>(88, 70, 10, 20))
    val juegoNuevo5 = Juego(ObjectId(), "Ana", listOf<Int>(20, 70, 54, 30))
    val juegoNuevo6 = Juego(ObjectId(), "Diego", listOf<Int>(20, 70, 54))

    collection.insertMany(listOf(juegoNuevo2, juegoNuevo3, juegoNuevo4, juegoNuevo5, juegoNuevo6))


    // BUSQUEDA
    // 1º Podemos buscar un valor concreto dentro de un array -> Devuelve todos los documentos que coincidan con el filtro
    println("\n// Podemos buscar un valor concreto dentro de un array -> Devuelve todos los documentos que coincidan con el filtro")
    val filtroSimple = Filters.eq("puntuaciones", 100)
    val resultadoFiltroSimple = collection.find(filtroSimple)

    resultadoFiltroSimple.forEach { juego ->
        println("id: ${juego.id}\nnombre: ${juego.nombre}\n--------")
    }

    // Podemos buscar tamaño del array
    println("\n// Podemos buscar tamaño del array")
    val filtroSizeArr = Filters.size("puntuaciones", 3)
    val resultadoFiltroSize = collection.find(filtroSizeArr).forEach { juego ->
        println("id: ${juego.id}\nnombre: ${juego.nombre}\n--------")
    }

    // Filtrar todos los valores que cumplan una condición específica
    println("\n// Filtrar todos los valores que cumplan una condición específica")
    val filtroCondicion = Filters.elemMatch("puntuaciones", Document("\$lte", 20))
    collection.find(filtroCondicion).forEach { juego ->
        println("id: ${juego.id}\nnombre: ${juego.nombre}\n--------")
    }

    // ACTUALIZACIÓN / MODIFICACION

    // Modificar un elemento concreto de un array -> Sabemos la posición
    // Pensar el filtro
    val filtroUpdateSimple = Filters.eq("nombre", "Diego")
    // Pensar el update
    val updateSet = Updates.set("puntuaciones.2", 100) //Nombre del registro a actualizar, la posicion dentro del array

    // Devuelve el documento actualizado
    val returnDoc = FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER)
    val operacionUpdate = collection.findOneAndUpdate(filtroUpdateSimple, updateSet, returnDoc)

    println("id: ${operacionUpdate.id}\nnombre: ${operacionUpdate.nombre}\n--------")







    // CIERRO LA CONEXION
    ConexionMongo.close()


}