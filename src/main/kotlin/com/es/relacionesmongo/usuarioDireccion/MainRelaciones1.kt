package com.es.relacionesmongo.usuarioDireccion

import com.es.relacionesmongo.ConexionMongo
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import java.util.*

fun main() {

    // Abrir la conexión con la BD
    val database = ConexionMongo.getDatabase("dbada")

    // Obtener la colección
    val collection: MongoCollection<Cliente> = database.getCollection("coll_usuarios", Cliente::class.java)
    val collNoticia: MongoCollection<Noticia> = database.getCollection("coll_noticias", Noticia::class.java)

    try {
        // Declarar un cliente y una direccion
        //val direccion = Direccion("alamo", "24", "04638", "Mojacar")
        //val cliente = Cliente("maria@gmail.com", "Maria", "mar14", true, listOf("950475656", "666888999"), direccion)
//
        //collection.insertOne(cliente)
//
        //val direccion2 = Direccion("desconocida", "24", "04003", "Almeria")
        //val direccion3 = Direccion("principal", "2", "04003", "Almeria")
        //val direccion4 = Direccion("principal", "1", "04003", "Almeria")
//
        //val cliente2 = Cliente("pedro@gmail.com", "Pedro", "periko", true, listOf("950475656", "666888999"), direccion2)
        //val cliente3 = Cliente("ana@gmail.com", "Ana", "anuski", true, listOf("950475656", "666888999"), direccion3)
        //val cliente4 = Cliente("antonio@gmail.com", "Antonio", "toni", true, listOf("950475656", "666888999"), direccion4)
        //val cliente5 = Cliente("agustin@gmail.com", "Agustin", "agus", true, listOf("950475656", "666888999"), direccion4)
//
        //val listaClientes = listOf(
        //    cliente2, cliente3, cliente4, cliente5
        //)

        //collection.insertMany(listaClientes)

        val filtro = Filters.eq("direccion.cp", "04638")

        collection.find(filtro).forEach {
            println(it)
        }

        // CONSULTA POR TLF

        val filtroTlf = Filters.eq("telefonos", "950475656")

        collection.find(filtroTlf).forEach {
            println(it)
        }

        // Consulta para contar el numero de usuarios de un CP en concreto

        val filtroNum = Filters.eq("direccion.cp", "04638")

        println(collection.find(filtroNum).count())

        insertarNoticias(collNoticia, collection)

    } catch (e: Exception) {
        println(e.message)
    }


    ConexionMongo.close()
}


fun insertarNoticias(collNoticia: MongoCollection<Noticia>, collection: MongoCollection<Cliente>) {

    val clientes = collection.find()

    val noticia2 = Noticia(null, "Pedro salta la comba", ".............", Date(), listOf("salto", "comba"), clientes.toList().random()._id)
    val noticia3 = Noticia(null, "Ana escribe una noticia", "..........", Date(), listOf("escritura", "noticias"), clientes.toList().random()._id)
    val noticia4 = Noticia(null, "Antonio juega al lol", ".............", Date(), listOf("juegos", "lol"), clientes.toList().random()._id)
    val noticia5 = Noticia(null, "Agustin51 destruye una mesa", "......", Date(), listOf("streamers", "mesas"), clientes.toList().random()._id)

    val listaNoticias = listOf(
        noticia2, noticia3, noticia4, noticia5
    )

    collNoticia.insertMany(listaNoticias)
}