package com.kirill.projectpad.pages;

import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.kirill.projectpad.R
import com.kirill.projectpad.pages.MainActivity.Companion.active_project_idx
import com.kirill.projectpad.pages.MainActivity.Companion.entities

class DetailedProjectView : AppCompatActivity() {

    private lateinit var title_et: EditText
    private lateinit var description_et: EditText
    private lateinit var type_et: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detailed_project_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        title_et = findViewById(R.id.Entity_title)
        description_et = findViewById(R.id.Entity_description_et)
        type_et = findViewById(R.id.Type_et)

        entities[active_project_idx].let {
            title_et.setText(it.project_name)
            description_et.setText(it.description)
            type_et.setText(it.type)
        }
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}