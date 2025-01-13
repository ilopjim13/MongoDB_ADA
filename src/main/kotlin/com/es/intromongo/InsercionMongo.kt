import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
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

        // Creamos el documento que queramos insertar
        val nuevoDocumento: Document = Document().append("saludo", "Ciao")

        // Insertamos en la base de datos
        coll.insertOne(nuevoDocumento)

    } catch (e: Exception) {
        println("Error al conectar a MongoDB: ${e.message}")
    } finally {
        // Cerrar la conexión
        mongoClient.close()
    }
}