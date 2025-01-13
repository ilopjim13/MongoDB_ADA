package com.es.intromongo

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import io.github.cdimascio.dotenv.dotenv
import org.bson.Document

fun main() {

    val dotenv = dotenv()

    println(dotenv["URL_MONGODB"])

    val connectString = dotenv["URL_MONGODB"]

    val client: MongoClient = MongoClients.create(connectString)

    val database: MongoDatabase = client.getDatabase("adaprueba")

    val coll: MongoCollection<Document> = database.getCollection("documentoholamundo")

    coll.insertOne(Document(mapOf("name" to "Alex")))

}