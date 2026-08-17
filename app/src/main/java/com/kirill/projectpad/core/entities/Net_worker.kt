package com.kirill.projectpad.core.entities

import android.widget.Toast
import com.kirill.projectpad.pages.MainActivity
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException

/**
 * Main class to interact with api on desktop client
 */
class Net_worker private constructor() {
    var is_api_connected: Boolean = false

    private object Rest_paths {
        const val POST_PATH: String = "mobile_connect"
        const val GET_PATH_REPLICA: String = ""
        const val GET_PATH_PROJECT_PAD: String = ""
        const val REPLICA_PATH: String = "" //path to Replica path endpoint
        const val BASE_URL: String =
            "http://192.168.0.9:5000" //global path to same net as desktop client
    }

    private val client: OkHttpClient = OkHttpClient()

    /**
     * Make sure that desktop client is running and connect to
     * 
     * @return Nothing
     */
    fun connect_to_api(onSuccess: (String) -> Unit, onError: (Throwable) -> Unit) {
        val request = Request.Builder()
            .url("${Rest_paths.BASE_URL}/status")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    onError(Exception("HTTP error: ${response.code}"))
                    return
                }

                val body = response.body?.string()
                onSuccess(body ?: "")
            }

            override fun onFailure(call: Call, e: IOException) {
                onError(e)
            }
        })
    }

    fun check_internet_connection(): Boolean {
        val runtime = Runtime.getRuntime()
        try {
            val ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8")
            return (ipProcess.waitFor() == 0)
        } catch (e: IOException) {
            Toast.makeText(
                MainActivity.project_view.context,
                "Error in checking internet connection: $e",
                Toast.LENGTH_SHORT
            ).show()
        } catch (e: InterruptedException) {
            Toast.makeText(
                MainActivity.project_view.context,
                "Error in checking internet connection: $e",
                Toast.LENGTH_SHORT
            ).show()
        }
        return false
    }

    /**
     * Get projects data from remote desktop (ProjectPad)
     * 
     * @throws IOException
     */
    @Throws(IOException::class)
    fun get_data_projectpad() {
        val request: Request = Request.Builder()
            .url(Rest_paths.BASE_URL + "/" + Rest_paths.GET_PATH_PROJECT_PAD)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                Toast.makeText(
                    MainActivity.project_view.context,
                    "Unexpected code: $response",
                    Toast.LENGTH_SHORT
                ).show()
            }
            val body = response.body!!.string()
        }
    }

    /**
     * Get projects data from remote desktop (Replica)
     * 
     * @throws IOException
     */
    @Throws(IOException::class)
    fun get_data_replica() {
        val request: Request = Request.Builder()
            .url(Rest_paths.BASE_URL + "/" + Rest_paths.GET_PATH_REPLICA)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                Toast.makeText(
                    MainActivity.project_view.context,
                    "Unexpected code: $response",
                    Toast.LENGTH_SHORT
                ).show()
            }
            val body = response.body!!.string()
        }
    }

    /**
     * Send data to desktop client
     * 
     * @param jsonBody serialized body of project data
     * @throws IOException in case of net errors
     */
    @Throws(IOException::class)
    fun post_project_data(
        jsonBody: String,
        onSuccess: (String) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val contentType = "application/json".toMediaTypeOrNull()
        val body = jsonBody.toRequestBody(contentType)

        val request: Request = Request.Builder()
            .url("${Rest_paths.BASE_URL}/${Rest_paths.POST_PATH}")
            .post(body)
            .addHeader("Content-Type", "application/json")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    onError(Exception("HTTP error: ${response.code}"))
                    return
                }

                val body = response.body?.string()
                onSuccess(body ?: "")
            }

            override fun onFailure(call: Call, e: IOException) {
                onError(e)
            }
        })
    }

    companion object {
        private var net_worker: Net_worker? = null
        val instance: Net_worker
            get() {
                if (net_worker == null) {
                    net_worker = Net_worker()
                }
                return net_worker!!
            }
    }
}
