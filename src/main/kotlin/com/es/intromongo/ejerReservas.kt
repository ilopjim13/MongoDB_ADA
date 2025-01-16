import com.es.intromongo.Reserva
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import io.github.cdimascio.dotenv.dotenv
import org.bson.Document
import org.bson.types.ObjectId
import java.time.Instant
import java.time.temporal.ChronoUnit


fun main() {

    // Insertar un viaje nuevo
    val db = ConexionBD.getDatabase("dbada")
    // Crear el documento
    val viajeNuevo = Document()
        .append("origen", "xxx")
        .append("destino", "yyy")
        .append("fecha_ida", Instant.now().plus(1, ChronoUnit.DAYS))
        .append("fecha_vuelta", Instant.now().plus(5, ChronoUnit.DAYS))
        .append("precio", 1000.0)

    //db.getCollection("collreservas_viaje").insertOne(viajeNuevo)

    val reserva: Reserva = Reserva(id = ObjectId(),"Miami")
    db.getCollection("collreservas_viaje", Reserva::class.java).insertOne(reserva)

    ConexionBD.close()

}

object ConexionBD {

    private val mongoClient: MongoClient by lazy {
        val dotenv = dotenv()
        val connectString = dotenv["URL_MONGODB_2"]

        MongoClients.create(connectString)
    }

    fun getDatabase(bd: String) : MongoDatabase {
        return mongoClient.getDatabase(bd)
    }

    fun close() {
        mongoClient.close()
    }

}