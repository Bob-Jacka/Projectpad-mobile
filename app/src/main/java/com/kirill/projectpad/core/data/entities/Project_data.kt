package com.kirill.projectpad.core.data.entities

/**
 * Class containing project data (or other data, such as idea) to transmit or save
 */
data class Entity(
    var project_name: String,
    val type: String,
    var description: String = ""
) {

    fun get_name(): String {
        return project_name
    }

    fun get_description(): String {
        return description ?: ""
    }

    fun set_name(new_name: String) {
        this.project_name = new_name
    }

    fun set_description(new_description: String) {
        this.description = new_description
    }

    fun get_type(): String {
        return type
    }
}