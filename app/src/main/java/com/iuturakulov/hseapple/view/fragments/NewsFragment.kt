package com.iuturakulov.hseapple.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.model.api.PostEntity
import com.iuturakulov.hseapple.utils.*
import com.iuturakulov.hseapple.view.adapters.NewsAdapter
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import kotlinx.android.synthetic.main.fragment_available_courses.swipe_refresh_layout
import kotlinx.android.synthetic.main.fragment_news.*
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
        swipe_refresh_layout.setOnRefreshListener {
            updateDatabase()
            swipe_refresh_layout.isRefreshing = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.create_event -> {
                showToast("Create event")
                replaceFragment(CreateNewsFragment(), false)
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
                Gson().fromJson(response.body().string(), Array<PostEntity>::class.java)
            updateUI(res)
        }
    }

    private fun updateUI(list: Array<PostEntity>) {
        val coursesAdapter = NewsAdapter(list)
        newsRecyclerView.adapter = coursesAdapter
    }
}