package com.kirill.projectpad.core.entities

import com.google.gson.Gson


/**
 * Object for serialize/deserialize actions
 */
object Project_serializer {

    private var gson: Gson = Gson()

    /**
     * Convert from object into bite stream
     */
    fun book_serialize(project_data: Project_data): String {
        return gson.toJson(project_data)
    }

    /**
     * Convert from bite stream into book data object
     */
    fun book_deserialize(string: String): Project_data {
        return gson.fromJson(string, Project_data::class.java)
    }
}