package com.kirill.projectpad.core.data.entities

import java.time.LocalDateTime

/**
 * Class containing project data (or other data, such as idea) to transmit or save
 */
data class Entity(
    var title: String,
    val type: String,
    var description: String = "",

    val languages: MutableList<String> = mutableListOf(),
    val project_priority: String = "",
    val project_domains: MutableList<String> = mutableListOf(),
    val created_at: String = LocalDateTime.now().toString(),
    val last_updated: String = created_at,
    val status: String = "Planned",
    val git_url: String = "",
    val has_auto_tests: Boolean = false
) {

    fun get_name(): String {
        return title
    }

    fun get_description(): String {
        return description
    }

    fun set_name(new_name: String) {
        this.title = new_name
    }

    fun set_description(new_description: String) {
        this.description = new_description
    }

    fun get_type(): String {
        return type
    }
}