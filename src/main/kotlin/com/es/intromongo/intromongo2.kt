package com.es.intromongo

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import io.github.cdimascio.dotenv.dotenv
import org.bson.Document

fun main() {

    // 1º Realizar la conexión con MongoDB (Cluster)
    // declaro un objeto para usar las clases de dotenv
    val dotenv = dotenv()
    // guardo en una variable la url de conexión
    val urlConnectionMongo = dotenv["URL_MONGODB"]
    // podemos realizar la conexion con el cluster
    val cluster: MongoClient = MongoClients.create(urlConnectionMongo)

    // 2º Nos conectamos a la base de datos
    val bd = cluster.getDatabase("adaprueba")

    // 3º Con la BD, podemos realizar las consultas a la colección que queramos
    val collection = bd.getCollection("documentoholamundo")

    val docConsulta = collection.find()

    docConsulta.forEach { doc ->
        println(doc.toString())
    }


}