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
        // updateDatabase()
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
                // updateDatabase()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateDatabase() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://stoplight.io/mocks/hseapple/nis-app/38273133/course/${if (SELECTION != CourseSelection.CHOSEN_SECOND) 0 else 1}/post")
            .get()
            .addHeader("Content-Type", "application/json")
            .addHeader("token", TOKEN_API)
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