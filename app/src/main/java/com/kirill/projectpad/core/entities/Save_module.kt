package com.kirill.projectpad.core.entities

import android.widget.Toast
import com.kirill.projectpad.core.data.entities.Entity
import com.kirill.projectpad.pages.MainActivity
import com.kirill.projectpad.pages.MainActivity.Companion.entities
import com.kirill.projectpad.pages.MainActivity.Companion.full_path_to_save
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException

/**
 * Entity that responsible for saving and loading objects from memory
 */
class Save_module {

    private val saveFile: File = File(full_path_to_save)
    private val serializer: Project_serializer = Project_serializer

    /**
     * Save projects in file by writing to memory
     */
    fun save_projects() {
        try {
            if (saveFile.length().toInt() != 0) {
                if (saveFile.delete()) {
                    save_projects()
                } else {
                    Toast.makeText(
                        MainActivity.recyclerView.context,
                        "Error in delete save file",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            val writer = BufferedWriter(FileWriter(saveFile))
            for (at in entities) {
                writer.write(serializer.entity_serialize(at))
                writer.newLine()
            }
            writer.close()
        } catch (e: IOException) {
            Toast.makeText(
                MainActivity.recyclerView.context,
                "Error in saving",
                Toast.LENGTH_SHORT
            ).show()
        } catch (e: Exception) {
            Toast.makeText(
                MainActivity.recyclerView.context,
                "Error in save module",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    /**
     * Load projects directly from memory
     */
    fun load_projects_array() {
        try {
            if (saveFile.length() != 0L && saveFile.exists()) {
                entities = ArrayList()
                load_savefile()
            }
        } catch (e: IOException) {
            Toast.makeText(
                MainActivity.recyclerView.context,
                "Error in loading projects",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    /// /////////////////////////////////////////////////////////////////////////

    private fun load_savefile() {
        try {
            saveFile.forEachLine { line ->
                if (line != "0") {
                    val project: Entity = serializer.entity_deserialize(line)
                    entities.add(project)
                }
            }
        } catch (e: IOException) {
            Toast.makeText(
                MainActivity.recyclerView.context,
                "Error in load save file",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
