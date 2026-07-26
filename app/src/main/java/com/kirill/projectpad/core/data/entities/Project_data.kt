package com.kirill.projectpad.core.data.entities

/**
 * Class containing book data to transmit or save
 */
class Project_data {

    private lateinit var project_name: String
    private var description: String? = null

    constructor() {
        //
    }

    constructor(project_name: String, description: String? = null) {
        this.project_name = project_name
        this.description = description
    }
}