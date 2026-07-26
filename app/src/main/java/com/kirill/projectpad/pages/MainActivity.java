package com.kirill.projectpad.pages;

import static com.kirill.projectpad.core.data.Global_settings.SAVE_FILE_NAME;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kirill.projectpad.R;
import com.kirill.projectpad.core.data.Global_settings;
import com.kirill.projectpad.core.data.entities.view.Item;
import com.kirill.projectpad.core.data.entities.view.ItemAdapter;
import com.kirill.projectpad.core.entities.Net_worker;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private Net_worker net_worker;

    @SuppressLint("StaticFieldLeak")
    public static LinearLayout taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                //Move to detailed project view page
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
                //
            }
        });

        net_worker = new Net_worker();
        Global_settings.full_path_to_save = getApplicationContext().getFilesDir().getAbsolutePath() + SAVE_FILE_NAME;

        List<Item> items = new ArrayList<>();
        //TODO load items from local storage and create items
//        items.add(new Item());

        adapter = new ItemAdapter(items);
        recyclerView.setAdapter(adapter);
    }
}