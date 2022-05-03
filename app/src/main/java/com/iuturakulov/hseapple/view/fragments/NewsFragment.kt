package com.iuturakulov.hseapple.view.fragments

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
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
        val SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            val policy: StrictMode.ThreadPolicy = StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            updateDatabase()
        }
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

    private fun updateDatabase() {
        val client = OkHttpClient().newBuilder()
            .build()
        val request = Request.Builder()
            .url("http://80.66.64.53:8080/course/2/post?start=1&limit=10")
            .method("GET", null)
            .addHeader(
                "Authorization",
                TEMP_TOKEN
            )
            .addHeader("Content-Type", "application/json")
            .addHeader("Cookie", "JSESSIONID=53530B6092B00A54239E5E86BAEE3EE6")
            .build()
        try {
            val response = client.newCall(request).execute()
            val res: Array<PostEntity> =
                Gson().fromJson(response.body()?.string() ?: "", Array<PostEntity>::class.java)
            updateUI(res)
        } catch (exception: IOException) {
            exception.printStackTrace()
        }
    }

    private fun updateUI(list: Array<PostEntity>) {
        val coursesAdapter = NewsAdapter(list)
        newsRecyclerView.adapter = coursesAdapter
    }
}