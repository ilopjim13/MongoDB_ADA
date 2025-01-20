package com.es.ejercicios

import com.es.ejercicios.console.Console
import com.es.ejercicios.database.ConexionMongo
import com.es.ejercicios.database.Database

fun main(){
    try {
        val console = Console()

        val database = Database()

        var option:Int

        do {
            console.showMenu()
            option = console.getOption()
            executeMenu(option, database, console)
        } while (option != 5)

    }catch (e: Exception) {
        println("Error al conectar a MongoDB: ${e.message}")
    } finally {
        ConexionMongo.close()
    }

}

/*
 PREGUNTAS

 a) ¿Qué ventajas e inconvenientes encuentras al usar una base de datos documental con MongoDB?

 - Una de las ventajas que tiene es la gran flexibilidad a la hora de introducir datos, ya que, es muy sencillo y no tiene,
   pérdida, pero un inconveniente grande es su inconsistencia de datos ya que somos nosotros los que debemos de asegurar que
   no se repitan esos datos,puesto que, por mucho que sean igual todos los datos se tomará como uno diferente. Sus transacciones
   no son tan robustas como las de SQL.

 b) ¿Cuáles han sido los principales problemas que has tenido al desarrollar la aplicación?

 - Pues los problemas que he tenido son a la hora de la consistencia de datos de que no se repitan.

 c) ¿Cómo solucionarías el problema de la inconsistencia de datos en una base de datos documental? (El hecho de que en los documentos de una colección no sea obligatorio seguir un esquema único)

 - Para solucionar la inconsistencia de datos en MongoDB, puedes usar validación de esquemas con JSON Schema, librerías ODM como Mongoose,
   controlar versiones de documentos y realizar monitoreos y auditorías regulares para verificar la integridad de los datos.

 */

