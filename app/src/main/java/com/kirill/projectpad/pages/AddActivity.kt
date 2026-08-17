package com.kirill.projectpad.pages

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.kirill.projectpad.R
import com.kirill.projectpad.core.data.entities.Entity
import com.kirill.projectpad.pages.MainActivity.Companion.entities

//Activity for adding project
class AddActivity : AppCompatActivity() {

    private lateinit var type_btn: Button
    private lateinit var project_name_tx: EditText
    private lateinit var project_description_tx: EditText
    private lateinit var add_project_btn: Button

    private lateinit var entity_name_tv: TextView
    private lateinit var entity_description_tv: TextView

    private var type: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        type_btn = findViewById(R.id.Choose_type_btn)
        entity_name_tv = findViewById(R.id.Entity_name_tv)
        entity_description_tv = findViewById(R.id.Entity_description_tv)
        project_name_tx = findViewById(R.id.Text_project_name_to_change)
        project_description_tx = findViewById(R.id.Text_project_description_to_change)
        add_project_btn = findViewById(R.id.Add_project_btn)

        registerForContextMenu(type_btn)

        add_project_btn.setOnClickListener { add_project() }
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun add_project() {
        if (type.isNotEmpty()) {
            val project_name = project_name_tx.text.toString()
            val project_description: String = project_description_tx.text.toString()

            val entity = Entity(project_name, type, project_description)
            entities.add(entity)
            MainActivity.save_module.save_projects()
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            Toast.makeText(this, "Type is not chosen", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menu.setHeaderTitle("Available types:")
        MenuInflater(v.context).inflate(R.menu.type_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.title) {
            "Idea", "Note", "Project" -> {
                type = item.title.toString()
                type_btn.text = item.title.toString()
                type_btn.setBackgroundColor(Color.GREEN)

                entity_name_tv.text = "$type name"
                entity_description_tv.text = "$type description"
                true
            }

            else -> {
                true
            }
        }
    }
}