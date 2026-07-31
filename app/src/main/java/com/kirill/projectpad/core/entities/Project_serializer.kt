package com.kirill.projectpad.core.entities

import com.google.gson.Gson
import com.kirill.projectpad.core.data.entities.Entity


/**
 * Object for serialize/deserialize actions
 */
object Project_serializer {

    private var gson: Gson = Gson()

    /**
     * Convert from object into bite stream
     */
    fun entity_serialize(entity_data: Entity): String {
        return gson.toJson(entity_data)
    }

    /**
     * Convert from bite stream into book data object
     */
    fun entity_deserialize(string: String): Entity {
        return gson.fromJson(string, Entity::class.java)
    }
}