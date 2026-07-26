package com.kirill.projectpad.pages;

import static com.kirill.book_manager_mobile.core.data.Global_settings.SAVE_FILE_NAME;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.kirill.book_manager_mobile.R;
import com.kirill.book_manager_mobile.core.entities.Net_worker;

public class MainActivity extends AppCompatActivity {

    private Net_worker net_worker;

    @SuppressLint("StaticFieldLeak")
    public static LinearLayout taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        net_worker = new Net_worker();
        saveFileName = getApplicationContext().getFilesDir().getAbsolutePath() + SAVE_FILE_NAME;
    }
}