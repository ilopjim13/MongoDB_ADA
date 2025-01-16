package com.es.intromongo

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import io.github.cdimascio.dotenv.dotenv
import org.bson.Document

fun main() {

    val dotenv = dotenv()
    val connectString = dotenv["URL_MONGODB_2"]

    // Configuramos la uri del cluster
    val mongoClient: MongoClient = MongoClients.create(connectString)
    val databaseName = "dbada"

    try {
        // Obtener la base de datos
        val database: MongoDatabase = mongoClient.getDatabase(databaseName)

        // OBTENGO LA COLECCIÓN
        val coll = database.getCollection("collprueba")

        // PASOS PARA REALIZAR LA ELIMINACION
        // 1 - filtro
        val filter = Filters.eq("email", "alfon@gmail.com")

        coll.deleteOne(filter).also {
            println(it)
        }






    } catch (e: Exception) {
        println("Error al conectar a MongoDB: ${e.message}")
    } finally {
        // Cerrar la conexión
        mongoClient.close()
    }

}