import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import io.github.cdimascio.dotenv.dotenv

fun main() {

    val dotenv = dotenv()
    val connectString = dotenv["URL_MONGODB_2"]

    // Configuramos la uri del cluster
    val mongoClient: MongoClient = MongoClients.create(connectString)
    val databaseName = "dbada"

    try {
        // Obtener la base de datos
        val database = mongoClient.getDatabase(databaseName)

        // Listar las colecciones
        val collections = database.listCollectionNames()

        // Mostrar las colecciones
        println("Colecciones en la base de datos '$databaseName':")
        for (collectionName in collections) {
            println("- $collectionName")
        }
    } catch (e: Exception) {
        println("Error al conectar a MongoDB: ${e.message}")
    } finally {
        // Cerrar la conexión
        mongoClient.close()
    }


}