package com.kirill.projectpad.core.entities;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Main class to interact with api on desktop client
 */
public final class Net_worker extends AppCompatActivity {

    private final OkHttpClient client;
    private final String BASE_URL = "http://192.168.1.45:5000"; //global path to same net as desktop client

    public Net_worker() {
        client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://jsonplaceholder.typicode.com/users/1")
                .build();

        new Thread(() -> {
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String jsonData = response.body().string();
                    Log.d("NetWorker", jsonData);

                    new Handler(Looper.getMainLooper()).post(() -> {
                    });
                } else {
                    Toast.makeText(null, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                Toast.makeText(null, "Network error: " + e, Toast.LENGTH_SHORT).show();
                new Handler(Looper.getMainLooper()).post(() -> {
                });
            }
        }).start();
    }

    public String getBASE_URL() {
        return this.BASE_URL;
    }

    public boolean connect_to_api() {
        return false;
    }

    /**
     * Get projects data from remote desktop
     *
     * @throws IOException
     */
    public void get_data() throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL + "/api/data")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                Toast.makeText(null, "Unexpected code: " + response, Toast.LENGTH_SHORT).show();
            }
            String body = response.body().string();
            System.out.println("Response: " + body);
        }
    }

    /**
     * Send data to desktop client
     *
     * @param jsonBody serialized body of book data
     * @throws IOException in case of net errors
     */
    public void post_project_data(String jsonBody) throws IOException {
        RequestBody body = RequestBody.create(
                jsonBody,
                MediaType.parse("application/json")
        );

        Request request = new Request.Builder()
                .url(BASE_URL + "/api/data")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                Toast.makeText(null, "Unexpected code " + response, Toast.LENGTH_SHORT).show();
            }
            System.out.println(response.body().string());
        }
    }
}
