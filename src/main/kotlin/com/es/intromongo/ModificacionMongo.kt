package com.es.intromongo

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import io.github.cdimascio.dotenv.dotenv
import org.bson.Document

fun main() {

    try {
        val db = ConexionBD.getDatabase("dbada")

        val coll = db.getCollection("collprueba", User::class.java)

        val filter = Filters.eq("nombre", "Alicia")
        //val updates = Updates.set("email", "alicia@gmail.com")
//        coll.updateOne(filter, updates).also {
//            println("Numero de documentos afectados: ${it.matchedCount}")
//
//        }
        val updates2 = Document("\$set", Document("email", "alicia@hola.com"))
        coll.updateOne(filter, updates2).also {
            println("Numero de documentos afectados: ${it.matchedCount}")

        }

        
    } catch (e: Exception) {
        println("Error al conectar a MongoDB: ${e.message}")
    } finally {
        // Cerrar la conexión
        ConexionBD.close()
    }

}

object ConexionBD {

    private val mongoClient: MongoClient by lazy {
        val dotenv = dotenv()
        val connectString = dotenv["URL_MONGODB_2"]

        MongoClients.create(connectString)
    }

    fun getDatabase(bd: String) : MongoDatabase {
        return mongoClient.getDatabase(bd)
    }

    fun close() {
        mongoClient.close()
    }

}