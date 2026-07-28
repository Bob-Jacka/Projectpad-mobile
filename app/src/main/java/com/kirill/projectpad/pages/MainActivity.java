package com.kirill.projectpad.pages;

import static com.kirill.projectpad.core.data.Global_settings.SAVE_FILE_NAME;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kirill.projectpad.R;
import com.kirill.projectpad.core.data.Global_settings;
import com.kirill.projectpad.core.data.entities.view.Item;
import com.kirill.projectpad.core.data.entities.view.ItemAdapter;
import com.kirill.projectpad.core.entities.Net_worker;
import com.kirill.projectpad.core.entities.Save_module;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    public static RecyclerView recyclerView;
    public Save_module save_module;
    private ItemAdapter adapter;
    private Net_worker net_worker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        save_module = new Save_module();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        net_worker = new Net_worker();

        save_module.load_projects_array();

        Global_settings.full_path_to_save = getApplicationContext().getFilesDir().getAbsolutePath() + SAVE_FILE_NAME;

        List<Item> items = new ArrayList<>();
        //TODO load items from local storage and create items
//        items.add(new Item());

        adapter = new ItemAdapter(items, new ItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(@NotNull Item item) {
                //
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onPause() {
        save_module.save_projects();
        super.onPause();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        addMenu(menu, v);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        chooseItem(item);
        return super.onContextItemSelected(item);
    }

    private void addMenu(ContextMenu menu, View v) {
        menu.setHeaderTitle("Actions with project:");
        menu.add("Open");
        menu.add("Delete");
        menu.add("Close");
    }

    private void chooseItem(MenuItem item) {
        String title = (String) item.getTitle();
        switch (title) {
            case "Delete":
            case "Удалить":
                break;
            case "Open":
            case "Открыть":
                break;
        }
    }
}