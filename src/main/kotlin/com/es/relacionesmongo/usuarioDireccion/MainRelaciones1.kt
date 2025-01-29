package com.es.relacionesmongo.usuarioDireccion

import com.es.relacionesmongo.ConexionMongo
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Aggregates
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Sorts
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

fun main() {

    val database = ConexionMongo.getDatabase("dbada")
    val collectionClientes: MongoCollection<Cliente> = database.getCollection("coll_usuarios", Cliente::class.java)
    collectionClientes.drop()

    insertClientes(collectionClientes)

    try {
        // CONSULTAS
        val filtro = Filters.eq("direccion.cp", "04638")

        println("CLIENTES CON CP 04638")
        collectionClientes.find(filtro).forEach {
            println(it)
        }

        // CONSULTA por tlfn
        val filtroTlfn = Filters.eq("telefonos", "950475656")

        println("CLIENTES CON TELEFONO 950475656")
        collectionClientes.find(filtroTlfn).forEach {
            println(it)
        }

        // CONSULTAR COUNT
        val filtroCount = Filters.eq("direccion.cp", "04638")
        println("NUM CLIENTES CON CP EN 04638")
        println(collectionClientes.find(filtroCount).count())
        println(collectionClientes.countDocuments(filtroCount))

    } catch (e: Exception) {
        println(e.printStackTrace())
    }

    // INSERTAR NOTICIAS
    val collNoticias: MongoCollection<Noticia> = database.getCollection("coll_noticias", Noticia::class.java)
    collNoticias.drop()
    try {

        val filtroCli = Filters.eq("_id", "antonio@gmail.com")
        val clienteEncontrado = collectionClientes.find(filtroCli).first()

        if (clienteEncontrado != null) {
            val noticia = Noticia("Noticia1", "Se cancelan las clases hasta nuevo aviso", Date.from(Instant.now()), listOf("MUNDO", "ACTUALIDAD") ,clienteEncontrado._id)
            collNoticias.insertOne(noticia)
        }

        if (clienteEncontrado != null) {
            val noticia1 = Noticia("Noticia2", "Caen los precios del alquiler", Date.from(Instant.now()), listOf("ACTUALIDAD") ,clienteEncontrado._id)
            val noticia2 = Noticia("Noticia3", "Se alcanza la paz mundial", Date.from(Instant.now()), listOf("MUNDO", "ACTUALIDAD") ,clienteEncontrado._id)
            val noticia3 = Noticia("Noticia4", "EEUU aparece por fin en la lista BRICS", Date.from(Instant.now().minus(2, ChronoUnit.DAYS)), listOf("MUNDO", "ACTUALIDAD") ,clienteEncontrado._id)
            val noticia4 = Noticia("Noticia5", "Decimosegundo intento de asalto al capitolio", Date.from(Instant.now()), null ,clienteEncontrado._id)
            val noticia5 = Noticia("Noticia6", "Scalpers entra en bancarrota, l@s cayetan@s se quedan sin referente", Date.from(Instant.now().minus(2, ChronoUnit.DAYS)), listOf() ,clienteEncontrado._id)
            val noticia6 = Noticia("Noticia7", "El Real Madrid gana su 57a Champions", Date.from(Instant.now().minus(2, ChronoUnit.DAYS)), listOf("DEPORTE", "ACTUALIDAD") ,clienteEncontrado._id)
            collNoticias.insertMany(listOf(noticia1, noticia2, noticia3, noticia4, noticia5, noticia6))
        }

    } catch (e: Exception) {
        e.printStackTrace()
    }

    // EJER PIZARRA VIERNES
    try {

        // 10 últimas noticias publicadas
        println("10 ULTIMAS NOTICIAS PUBLICADAS")
        val sortDescending = Sorts.descending("fechaPub")
        collNoticias.find()
            .sort(sortDescending)
            .limit(10)
            .forEach {
                println(it)
            }

        // Noticias que no tienen el campo tag
        println("Noticias que no tienen el campo tag")
        val filtroTag1 = Filters.exists("tag", false)
        val filtroTag2 = Filters.size("tag", 0)
        val filtroTag = Filters.or(filtroTag1, filtroTag2)

        collNoticias.find(filtroTag).forEach {
            println(it)
        }

        // Noticias de un periodo de fechas
        println("Noticias de un periodo de fechas")
        val filtroFecha = Filters.and(
            Filters.gte("fechaPub", Instant.now().minus(1, ChronoUnit.DAYS)),
            Filters.lte("fechaPub", Instant.now().plus(1, ChronoUnit.DAYS))
        )

        collNoticias.find(filtroFecha).forEach {
            println(it)
        }

    } catch (e: Exception) {
        e.printStackTrace()
    }


    // QUEREMOS MOSTRAR TODAS LAS NOTICIAS DE UN USUARIO CON LA INFO DEL USUARIO
    println("******QUEREMOS MOSTRAR TODAS LAS NOTICIAS DE UN USUARIO CON LA INFO DEL USUARIO")
    val coll = database.getCollection("collNoticias")
    val pipeline = listOf(
        Aggregates.lookup(
            "collUsuarios",
            "user",
            "_id",
            "cliente_info")
    )

    val resultado = coll.aggregate(pipeline).toList()
    resultado.forEach {
        println(it.toJson())
    }

    println("******QUEREMOS MOSTRAR TODAS LOS USUARIOS CON LA INFO DE LA NOTICIA")
    val coll2 = database.getCollection("collUsuarios")

    val pipeline2 = listOf(
        Aggregates.lookup(
            "collNoticias",
            "_id",
            "user",
            "noticia_info"
        )
        , Aggregates.match(
            Filters.eq("_id", "antonio@gmail.com")
        )

    )

    val resultado2 = coll2.aggregate(pipeline2).toList()
    resultado2.forEach {
        println(it.toJson())
    }



    ConexionMongo.close()
}

fun insertClientes(collectionClientes:MongoCollection<Cliente>) {
    try {
        // Declarar un cliente y una direccion
        val direccion = Direccion("alamo", "24", "04638", "Mojacar")
        val cliente = Cliente("maria@gmail.com", "Maria", "mar14", true, listOf("950475656", "666888999"), direccion)

        collectionClientes.insertOne(cliente)

        val direccion2 = Direccion("desconocida", "24", "04003", "Almeria")
        val direccion3 = Direccion("principal", "2", "04003", "Almeria")
        val direccion4 = Direccion("principal", "1", "04003", "Almeria")

        val cliente2 = Cliente("pedro@gmail.com", "Pedro", "periko", true, listOf("950475656", "666888999"), direccion2)
        val cliente3 = Cliente("ana@gmail.com", "Ana", "anuski", true, listOf("950475656", "666888999"), direccion3)
        val cliente4 = Cliente("antonio@gmail.com", "Antonio", "toni", true, listOf("950475658", "666888999"), direccion4)
        val cliente5 = Cliente("agustin@gmail.com", "Agustin", "agus", true, listOf("950475656", "666888999"), direccion4)

        val listaClientes = listOf<Cliente>(
            cliente2, cliente3, cliente4, cliente5
        )

        collectionClientes.insertMany(listaClientes)
    } catch (e: Exception) {
        println("Clave duplicada")
    }
}
