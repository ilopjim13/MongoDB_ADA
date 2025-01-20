package com.es.arraysmongo

//import com.mongodb.client.MongoCollection
//import com.mongodb.client.model.Filters
//import com.mongodb.client.model.FindOneAndUpdateOptions
//import com.mongodb.client.model.ReturnDocument
//import com.mongodb.client.model.Updates
//import org.bson.Document
//import org.bson.types.ObjectId
//
//fun main() {
//
//    // 1º Sería conectarnos a la BD
//    val database = ConexionMongo.getDatabase("dbada")
//
//    // 2º Conseguir la coleccion
//    val collection: MongoCollection<Juego> = database.getCollection("colljuego", Juego::class.java)
//
//    // VOY A VACIAR LA COLECCIÓN
//    collection.drop()
//
//    // INSERCION
//    val juegoNuevo = Juego(ObjectId(), "Alfonso", listOf<Int>(50, 70, 100, 90))
//
//    collection.insertOne(juegoNuevo)
//
//    val juegoNuevo2 = Juego(ObjectId(), "Pedro", listOf<Int>(50, 70, 99, 90))
//    val juegoNuevo3 = Juego(ObjectId(), "Maria", listOf<Int>(57, 74, 100, 90))
//    val juegoNuevo4 = Juego(ObjectId(), "Jose", listOf<Int>(88, 70, 10, 20))
//    val juegoNuevo5 = Juego(ObjectId(), "Ana", listOf<Int>(20, 70, 54, 30))
//    val juegoNuevo6 = Juego(ObjectId(), "Diego", listOf<Int>(20, 70, 54))
//
//    collection.insertMany(listOf(juegoNuevo2, juegoNuevo3, juegoNuevo4, juegoNuevo5, juegoNuevo6))
//
//
//    // BUSQUEDA
//    // 1º Podemos buscar un valor concreto dentro de un array -> Devuelve todos los documentos que coincidan con el filtro
//    println("\n// Podemos buscar un valor concreto dentro de un array -> Devuelve todos los documentos que coincidan con el filtro")
//    val filtroSimple = Filters.eq("puntuaciones", 100)
//    val resultadoFiltroSimple = collection.find(filtroSimple)
//
//    resultadoFiltroSimple.forEach { juego ->
//        println("id: ${juego.id}\nnombre: ${juego.nombre}\n--------")
//    }
//
//    // Podemos buscar tamaño del array
//    println("\n// Podemos buscar tamaño del array")
//    val filtroSizeArr = Filters.size("puntuaciones", 3)
//    val resultadoFiltroSize = collection.find(filtroSizeArr).forEach { juego ->
//        println("id: ${juego.id}\nnombre: ${juego.nombre}\n--------")
//    }
//
//    // Filtrar todos los valores que cumplan una condición específica
//    println("\n// Filtrar todos los valores que cumplan una condición específica")
//    val filtroCondicion = Filters.elemMatch("puntuaciones", Document("\$lte", 20))
//    collection.find(filtroCondicion).forEach { juego ->
//        println("id: ${juego.id}\nnombre: ${juego.nombre}\n--------")
//    }
//
//    // ACTUALIZACIÓN / MODIFICACION
//
//    // Modificar un elemento concreto de un array -> Sabemos la posición
//    // Pensar el filtro
//    val filtroUpdateSimple = Filters.eq("nombre", "Diego")
//    // Pensar el update
//    val updateSet = Updates.set("puntuaciones.2", 100) //Nombre del registro a actualizar, la posicion dentro del array
//
//    // Devuelve el documento actualizado
//    val returnDoc = FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER)
//    val operacionUpdate = collection.findOneAndUpdate(filtroUpdateSimple, updateSet, returnDoc)
//
//    println("id: ${operacionUpdate.id}\nnombre: ${operacionUpdate.nombre}\n--------")
//
//
//    // Modificar un elemento concreto de un array -> Primer elemento
//    println("\n// Modificar un elemento concreto de un array -> Primer elemento")
//    val filtroUpdatePrimera = Filters.eq("puntuaciones", 50)
//
//    val updateInc = Updates.inc("puntuaciones.$", 2)
//
//    val operacionUpdateInc = collection.findOneAndUpdate(filtroUpdatePrimera, updateInc, returnDoc)
//
//    println("id: ${operacionUpdateInc.id}\nnombre: ${operacionUpdateInc.nombre}\n--------")
//
//    // Modificar un elemento con varios condicionales
//    // Filtro -> Para Maria y cuya puntuacion sea mayor a 90
//    println("\n// Filtro -> Para Maria y cuya puntuacion sea mayor a 90\n")
//    val filterCombi = Filters.and(
//        Filters.gte("puntuaciones", 90),
//        Filters.eq("nombre", "Maria")
//    )
//
//    val updateCombi = Updates.inc("puntuaciones.$", -2)
//
//    val operacionUpdateCombi = collection.findOneAndUpdate(filterCombi, updateCombi, returnDoc)
//
//    println("id: ${operacionUpdateCombi.id}\nnombre: ${operacionUpdateCombi.nombre}\n--------")
//
//
//    // MODIFICAR TODOS LOS ELEMENTOS DENTRO DE UN ARRAY QUE CUMPLA UNA CONDICIÓN
//    /*
//    $map -> map es la operación para modificar todos los elementos dentro de un array
//        -> "input": $nombre del array
//        -> "as": alias del elemento que se itera
//        -> "in": define cómo se transforma cada elemento
//            DENTRO DE in SE DEFINEN LAS REGLAS A APLICAR A LOS ELEMENTOS
//            -> $cond: condicion
//                -> if: la condición que evalúa cada elemento
//                -> then: el valor que se usará si la condición es verdadera
//                -> else: el valor que se usará si la condición es falsa
//     */
//
//    // MODIFICAR TODAS LAS PUNTUACIONES MAYORES O IGUALES A 70 EN EL ARRAY DE JOSE
////    val filtroNom = Filters.eq("nombre", "Jose")
////    val updatesMap = Updates.set("puntuaciones",
////        Document("\$map", Document()
////            .append("input", "\$puntuaciones")
////            .append("as", "puntuacion")
////            .append("in", Document("\$cond", Document()
////                .append("if", Document("\$gte", listOf("\$\$puntuacion", 70))
////                .append("then", 0)
////                .append("else", "\$\$puntuacion"))))))
////
////    val resultadoMap = collection.findOneAndUpdate(filtroNom, updatesMap, returnDoc)
////
////    println("id: ${resultadoMap.id}\nnombre: ${resultadoMap.nombre}\n--------")
//
//    /*
//    DEPARTAMENTOS QUE CONTIENEN UNA LISTA DE EMPLEADOS
//    DEPARTAMENTO {
//        "nombre": "Informatica"
//        "empleados": ["Maria", "Pepe"...]
//    }
//
//    1. Listar todos los empleados de un departamento en concreto
//    2. Insertar un nuevo empleado en un departamento en concreto
//    3. Buscar los departamentos que no tengan empleados
//    4. Cambiar el nombre de un empleado en concreto de un departamento en concreto
//    5. Actualizar el nombre del departamento e imprimir toda su información por pantalla
//     */
//
//
//
//
//
//
//
//
//    // CIERRO LA CONEXION
//    ConexionMongo.close()
//
//
//}