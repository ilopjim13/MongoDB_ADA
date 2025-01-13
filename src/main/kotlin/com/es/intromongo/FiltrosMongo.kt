import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.model.Filters
import io.github.cdimascio.dotenv.dotenv

fun main() {

    val dotenv = dotenv()
    val connectString = dotenv["URL_MONGODB_2"]

    // Configuramos la uri del cluster
    val mongoClient: MongoClient = MongoClients.create(connectString)
    val databaseName = "dbada"

    // Obtener la base de datos
    val database = mongoClient.getDatabase(databaseName)

    // Obtener la colección
    val collection = database.getCollection("collholamundo")

    val filter = Filters.eq("nombre", "Juan")
    // Busca documentos donde el campo "nombre" sea igual a "Juan"
    collection.find(filter).forEach { println(it) }

    val filter2 = Filters.gt("edad", 25)
    // Busca documentos donde el campo "edad" sea mayor a 25
    collection.find(filter2).forEach { println(it) }

    val filter3 = Filters.and(Filters.gt("edad", 20), Filters.lt("edad", 30))
    // Busca documentos donde "edad" sea mayor a 20 y menor a 30
    collection.find(filter3).forEach { println(it) }

    val filter4 = Filters.`in`("pais", listOf("España", "México", "Argentina"))
    // Busca documentos donde "pais" esté en la lista ["España", "México", "Argentina"]
    collection.find(filter4).forEach { println(it) }

    val filter5 = Filters.or(Filters.eq("ciudad", "Madrid"), Filters.eq("ciudad", "Barcelona"))
    // Busca documentos donde "ciudad" sea "Madrid" o "Barcelona"
    collection.find(filter5).forEach { println(it) }

    val filter6 = Filters.regex("nombre", "^Ju.*")
    // Busca documentos donde "nombre" empiece con "Ju"
    collection.find(filter6).forEach { println(it) }





}