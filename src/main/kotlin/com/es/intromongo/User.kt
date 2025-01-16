package com.es.intromongo

import org.bson.types.ObjectId

data class User(
    val id: ObjectId,
    val nombre: String,
    val edad: Int,
    val email: String)
