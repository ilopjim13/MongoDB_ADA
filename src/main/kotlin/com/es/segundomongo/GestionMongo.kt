package com.es.segundomongo

import com.mongodb.client.FindIterable
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import io.github.cdimascio.dotenv.dotenv
import org.bson.Document

fun main() {

    try {
        // Obtener la base de datos
        val db = ConexionBD.getDatabase("dbada")

        // OBTENGO LA COLECCIÓN
        val coll:MongoCollection<Usuario> = db.getCollection("collprueba", Usuario::class.java)

        //coll.insertOne(Usuario("jose luis", 22, "jose@gmail.com"))

        val filtro = Filters.eq("nombre", "jose luis")
        val busqueda: FindIterable<Usuario> = coll.find(filtro)

        busqueda.forEach { user -> println(user.nombre) }






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