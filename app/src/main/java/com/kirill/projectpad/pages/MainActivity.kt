package com.kirill.projectpad.pages


import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kirill.projectpad.R
import com.kirill.projectpad.core.data.entities.Entity
import com.kirill.projectpad.core.data.entities.view.Item
import com.kirill.projectpad.core.data.entities.view.ItemAdapter
import com.kirill.projectpad.core.entities.Net_worker
import com.kirill.projectpad.core.entities.Save_module


class MainActivity : AppCompatActivity() {
    private lateinit var net_worker_module: Net_worker
    private lateinit var adapter: ItemAdapter

    private lateinit var add_button: Button
    private lateinit var transmit_button: Button

    private lateinit var manager: NotificationManager
    private var items: MutableList<Item> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        full_path_to_save = applicationContext.filesDir.absolutePath + SAVE_FILE_NAME
        init_page_entities()

        entities.forEach {
            items.add(Item(it.get_name(), it.get_type(), it.get_description()))
        }

        add_callbacks()
    }

    override fun onStop() {
        save_module.save_projects()
        super.onStop()
    }

    override fun onPause() {
        save_module.save_projects()
        super.onPause()
    }

    override fun onDestroy() {
        save_module.save_projects()
        super.onDestroy()
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        active_project_idx = recyclerView.indexOfChild(block_view)
        return when (item.title) {
            //Open detailed project view
            "Open" -> {
                startActivity(
                    Intent(
                        this,
                        DetailedProjectView::class.java
                    )
                )
                true
            }
            //Edit project object
            "Edit" -> {
                startActivity(
                    Intent(
                        this,
                        ChangeProject::class.java
                    )
                )
                true
            }

            "Delete" -> {
                entities.removeAt(active_project_idx)
                true
            }

            else -> super.onContextItemSelected(item)
        }
    }

    /**
     * Create and initialize Main activity entities
     */
    private fun init_page_entities() {
        save_module = Save_module()
        recyclerView = findViewById(R.id.Project_view)
        add_button = findViewById(R.id.Add_btn)
        transmit_button = findViewById(R.id.Transmit_btn)
        net_worker_module = Net_worker.instance

        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)

        adapter = ItemAdapter(items)
        recyclerView.setLayoutManager(LinearLayoutManager(this))
        recyclerView.setAdapter(adapter)
        registerForContextMenu(recyclerView)
        save_module.load_projects_array()
        if (entities.isEmpty()) {
            transmit_button.visibility = View.INVISIBLE
        } else {
            val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Project pad calls")
                .setContentText("Make push. In other case that might be your last minute with projects")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build()
            manager.notify(1, notification)
        }
    }

    /**
     * Add callbacks to buttons and adapters
     */
    private fun add_callbacks() {
        add_button.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    AddActivity::class.java
                )
            )
        }

        transmit_button.setOnClickListener {
            if (net_worker_module.check_internet_connection()) {
                val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Project pad calls")
                    .setContentText("Connecting to api")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .build()
                manager.notify(1, notification)
                Toast.makeText(this, "Connecting to api", Toast.LENGTH_SHORT).show()
                net_worker_module.connect_to_api()
            } else {
                Toast.makeText(this, "Check your internet connection", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //Android entities and global data:
    companion object {
        @JvmStatic
        @SuppressLint("StaticFieldLeak")
        lateinit var recyclerView: RecyclerView

        @JvmStatic
        @SuppressLint("StaticFieldLeak")
        lateinit var save_module: Save_module

        @SuppressLint("StaticFieldLeak")
        lateinit var block_view: View //view to get focused project

        @JvmStatic
        var entities: MutableList<Entity> = kotlin.collections.ArrayList()

        @JvmStatic
        val SAVE_FILE_NAME: String = "/saveProjects"

        @JvmStatic
        lateinit var full_path_to_save: String

        @JvmStatic
        var active_project_idx: Int = 0

        const val CHANNEL_ID = "project_pad_channel"
        const val CHANNEL_NAME = "PP channel"
    }
}