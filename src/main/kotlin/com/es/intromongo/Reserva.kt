package com.es.intromongo

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.time.Instant

data class Reserva(
    @BsonId
    val id: ObjectId,
    val destino: String)
