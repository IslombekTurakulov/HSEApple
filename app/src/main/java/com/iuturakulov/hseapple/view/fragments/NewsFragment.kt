package com.iuturakulov.hseapple.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.model.api.PostEntity
import com.iuturakulov.hseapple.utils.*
import com.iuturakulov.hseapple.view.activities.CreateNewsActivity
import com.iuturakulov.hseapple.view.adapters.NewsAdapter
import kotlinx.android.synthetic.main.fragment_news.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


class NewsFragment : Fragment(R.layout.fragment_news) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateDatabase()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.create_event -> {
                showToast("Create event")
                val startupIntent =
                    Intent(APP_ACTIVITY.applicationContext, CreateNewsActivity::class.java)
                startupIntent.flags =
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(startupIntent)
                APP_ACTIVITY.overridePendingTransition(
                    android.R.anim.fade_in,
                    android.R.anim.fade_out
                )
                updateDatabase()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    val temp = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJtaWNyb3NvZnQ6aWRlbnRpdHlzZXJ2ZXI6MGQ0NmFlMDUtM2JiYy00MzQ2LWEzYTMtMWQ3MzJiNDllYTUzIiwiaXNzIjoiaHR0cDovL2F1dGguaHNlLnJ1L2FkZnMvc2VydmljZXMvdHJ1c3QiLCJpYXQiOjE2NTExMzgyNzIsIm5iZiI6MTY1MTEzODI3MiwiZXhwIjoxNjUxMTQxODcyLCJnaXZlbl9uYW1lIjoi0JPRgNC40LPQvtGA0LjQuSIsImNvbW1vbm5hbWUiOiLQodC-0YHQvdC-0LLRgdC60LjQuSDQk9GA0LjQs9C-0YDQuNC5INCc0LjRhdCw0LnQu9C-0LLQuNGHIiwiZmFtaWx5X25hbWUiOiLQodC-0YHQvdC-0LLRgdC60LjQuSIsImVtYWlsIjoiZ21zb3Nub3Zza2l5QGVkdS5oc2UucnUiLCJhcHB0eXBlIjoiUHVibGljIiwiYXBwaWQiOiIwZDQ2YWUwNS0zYmJjLTQzNDYtYTNhMy0xZDczMmI0OWVhNTMiLCJhdXRobWV0aG9kIjoidXJuOm9hc2lzOm5hbWVzOnRjOlNBTUw6Mi4wOmFjOmNsYXNzZXM6UGFzc3dvcmRQcm90ZWN0ZWRUcmFuc3BvcnQiLCJhdXRoX3RpbWUiOiIyMDIyLTA0LTI3VDE1OjU3OjI2LjU5M1oiLCJ2ZXIiOiIxLjAifQ.mG2KAlMopVguX952qDxahpNsJvCylxCCEldK-_filqo"

    private fun updateDatabase() {
        val client = OkHttpClient()
        val request = Request.Builder()
                // /course/:courseID/post?start=1&limit=2
            .url("https://80.66.64.53:8080/course/1/post?start=1&limit=2")
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", temp)
            .get()
            .build()
        var response: Response? = null
        try {
            response = client.newCall(request).execute()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        if (response != null) {
            val res: Array<PostEntity> =
                Gson().fromJson(response.body()?.string() ?: "", Array<PostEntity>::class.java)
            updateUI(res)
        }
    }

    private fun updateUI(list: Array<PostEntity>) {
        val coursesAdapter = NewsAdapter(list)
        newsRecyclerView.adapter = coursesAdapter
    }
}