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

        // PASOS PARA REALIZAR LA MODIFICACION
        // 1 - filtro
        val filter = Filters.eq("email", "alfonso@hotmail.com")

        // 2 - establecer los campos a actualizar
        val update = Updates.set("email", "alfon@gmail.com")

        // 3 - realizo la operación
        coll.updateOne(filter, update).also {
            println(it)
        }

        // OTRAS OPERACIONES UPDATES
        val filter2 = Filters.eq("email", "alfon@gmail.com")
        val updateInc = Updates.inc("edad", 1);
        coll.updateOne(filter2, updateInc).also {
            println(it)
        }

        // ALTERNATIVA A UPDATES
        // Document (operador, el campo a cambiar)
        val updateConDocument = Document("\$inc", Document("edad", 1))
        coll.updateOne(filter2, updateConDocument).also {
            println(it)
        }

        // COMBIAR VARIAS ACCIONES CON DOCUMENT
        val updateConDocumentCombi =
            Document("\$mul", Document("edad", 2))
                .append("\$set", Document("nombre", "Alfon"))
        coll.updateOne(filter2, updateConDocumentCombi).also {
            println(it)
        }

        val updateConUpdatesCombi = Updates.combine(
            Updates.mul("edad", 2),
            Updates.set("nombre", "Alfonso")
        )
        coll.updateOne(filter2, updateConUpdatesCombi).also {
            println(it)
        }

        // RENOMBRAR UN CAMPO
        val updateNombreCampo = Updates.rename("nombre", "name")
        coll.updateOne(filter2, updateNombreCampo).also {
            println(it)
        }

        // REPLACE ONE
        // 1º FILTRO
        val filtroReplace = Filters.lt("edad", 40)
        val documentParaReemplazar = Document()
            .append("nombre", "Elon")
            .append("edad", 39)
            .append("email", "elon@elon.com")

        coll.replaceOne(filtroReplace, documentParaReemplazar)




    } catch (e: Exception) {
        println("Error al conectar a MongoDB: ${e.message}")
    } finally {
        // Cerrar la conexión
        mongoClient.close()
    }

}