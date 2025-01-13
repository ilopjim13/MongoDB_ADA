import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
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
        val coll = database.getCollection("collholamundo")

        // Creamos filtro por el que queremos filtrar
        val filter = Filters.eq("saludo", "Hola Mundo")

        // Aplicamos ese filtro a la búsqueda
        val resultsFlow = coll.find(filter)

        // Recorremos la lista para ver los resultados
        resultsFlow.forEach { println(it) }


    } catch (e: Exception) {
        println("Error al conectar a MongoDB: ${e.message}")
    } finally {
        // Cerrar la conexión
        mongoClient.close()
    }

}