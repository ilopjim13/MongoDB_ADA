package com.es.relacionesmongo.usuarioDireccion

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId
import java.util.Date

data class Noticia(
    @BsonId
    val id: ObjectId?,
    val titulo: String,
    val cuerpo: String,
    val fecha_pub: Date,
    @BsonProperty("tag")
    val tag: List<String>,
    val user: String
)