package com.kirill.projectpad.core.entities;

import static com.kirill.projectpad.core.data.Global_settings.projects;
import static com.kirill.projectpad.core.data.Global_settings.empty_line;
import static com.kirill.projectpad.core.entities.Project_serializer.INSTANCE;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.kirill.projectpad.R;
import com.kirill.projectpad.core.data.entities.Project_data;
import com.kirill.projectpad.pages.MainActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Entity that responsible for saving and loading objects from memory
 */
public final class Save_module extends AppCompatActivity {

    public static String saveFileName;
    private final File saveFile = new File(saveFileName);
    private final Project_serializer serializer = INSTANCE;

    /**
     * Save projects in file by writing to memory
     */
    public void save_projects() {
        try {
            if (saveFile.length() != 0) {
                if (saveFile.delete()) {
                    save_projects();
                } else {
                    Toast.makeText(this, "Error in delete save file", Toast.LENGTH_SHORT).show();
                }
            }
            final BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile));
            for (Project_data at : projects) {
                if (at == null) {
                    writer.write(empty_line);
                    writer.newLine();
                } else {
                    writer.write(serializer.book_serialize(at));
                    writer.newLine();
                }
            }
            writer.close();
        } catch (IOException e) {
            Toast.makeText(MainActivity.recyclerView.getContext(), "Error in saving", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(MainActivity.recyclerView.getContext(), "Error in save module", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Load projects directly from memory
     */
    public void load_projects_array() {
        try {
            if (saveFile.length() != 0L && saveFile.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(saveFile));
                projects = new ArrayList<>();
                load_savefile(reader);
            }
        } catch (IOException e) {
            Toast.makeText(MainActivity.recyclerView.getContext(), "Error in loading projects", Toast.LENGTH_SHORT).show();
        }
    }

    /// /////////////////////////////////////////////////////////////////////////

    private void load_savefile(@NonNull BufferedReader reader) {
        int increment = 0;
        String saveLine;
        try {
            final List<Object> file_list = Arrays.asList(reader.lines().toArray());
            do {
                saveLine = (String) file_list.get(increment);
                if (saveLine != null && !saveLine.equals(empty_line)) {
                    Project_data book = serializer.book_deserialize(saveLine);
//                    add_project(book);
                }
                ++increment;
            } while (increment < file_list.size());
            reader.close();
        } catch (IOException e) {
            Toast.makeText(MainActivity.recyclerView.getContext(), "Error in load save file", Toast.LENGTH_SHORT).show();
        }
    }
}
