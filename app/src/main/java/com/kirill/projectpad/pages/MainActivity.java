package com.kirill.projectpad.pages;

import static com.kirill.projectpad.core.data.Global_settings.SAVE_FILE_NAME;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

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

    private int exit = 0;

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
    public void onBackPressed() {
        ++exit;
        switch (exit) {
            case 1:
                Toast.makeText(this, "Press back one more time to exit", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                finishAffinity();
                super.onBackPressed();
                break;
        }
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
//        if (INSTANCE.getTransports()[transportBlocks.indexOfChild(v)] == null) {
//            menu.setHeaderTitle(R.string.Add);
//            menu.add(R.string.Transport);
//            menu.add(R.string.MetroMap);
//            menu.add(R.string.Close);
//        } else {
//            menu.setHeaderTitle(R.string.Transport2);
//            menu.add(R.string.Delete);
//            menu.add(R.string.Change);
//            menu.add(R.string.Close);
//        }
    }

    private void chooseItem(MenuItem item) {
        String title = (String) item.getTitle();
        switch (title) {
            case "Delete":
            case "Удалить":
                break;
            case "Change":
            case "Изменить":
                break;
        }
    }
}