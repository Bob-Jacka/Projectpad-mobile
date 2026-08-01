package com.kirill.projectpad.pages;

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.kirill.projectpad.R
import com.kirill.projectpad.core.data.entities.Entity
import com.kirill.projectpad.pages.MainActivity.Companion.active_project_idx
import com.kirill.projectpad.pages.MainActivity.Companion.entities
import com.kirill.projectpad.pages.MainActivity.Companion.save_module

class ChangeProject : AppCompatActivity() {
    lateinit var apply_changes_btn: Button
    lateinit var project_name_tx: EditText
    lateinit var project_description_tx: EditText

    private lateinit var active_obj: Entity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_change_project)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        apply_changes_btn = findViewById(R.id.Change_project_btn)
        project_name_tx = findViewById(R.id.Text_project_name_to_change)
        project_description_tx = findViewById(R.id.Text_project_description_to_change)

        apply_changes_btn.setOnClickListener { apply_change_project() }

        //init current object and init page
        active_obj = entities[active_project_idx].also {
            project_name_tx.setText(it.project_name)
            project_description_tx.setText(it.description)
        }
    }

    fun apply_change_project() {
        if (project_name_tx.text.toString().isNotEmpty()) {
            active_obj.let {
                it.set_name(project_name_tx.text.toString())
                it.set_description(project_description_tx.text.toString())
            }

            save_module.save_projects()
            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                )
            )
        } else {
            Toast.makeText(this, "Project name cannot be an empty string", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onPause() {
        save_module.save_projects()
        super.onPause()
    }

    override fun onDestroy() {
        save_module.save_projects()
        super.onDestroy()
    }
}