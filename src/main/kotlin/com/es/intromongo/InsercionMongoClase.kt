import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
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
        val database = mongoClient.getDatabase(databaseName)

        // Obtener la colección
        val collection = database.getCollection("collprueba")

        // Establecer diferentes filtros de búsqueda
        val filtroBuscarPorNombre = Filters.eq("nombre", "Alicia")

        // Puedo ejecutar la búsqueda
        val busqueda1 = collection.find(filtroBuscarPorNombre)

        println("EQ")
        busqueda1.forEach { println(it) }

        // Filtro and lógico -> Queremos buscar los documentos cuya edad esté entre 30 y 80
        val filtroBusquedad = Filters.and(Filters.gte("edad", 30), Filters.lt("edad", 80))

        // Ejecuto la búsqueda (y recorro a la vez)
        println("AND")
        collection.find(filtroBusquedad).forEach { println(it) }

        // Filtro regex -> Encontrar los documentos cuyo email termine en hotmail.com
        val filtroRegex = Filters.regex("email", ".*hotmail\\.com$")

        println("REGEX")
        collection.find(filtroRegex).forEach { println(it) }




    } catch (e: Exception) {
        println("Error al conectar a MongoDB: ${e.message}")
    } finally {
        // Cerrar la conexión
        mongoClient.close()
    }


}