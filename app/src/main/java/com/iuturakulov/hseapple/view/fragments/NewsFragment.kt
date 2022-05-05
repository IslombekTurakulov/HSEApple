package com.iuturakulov.hseapple.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.utils.APP_ACTIVITY
import com.iuturakulov.hseapple.utils.USER_CHAT
import com.iuturakulov.hseapple.utils.showToast
import com.iuturakulov.hseapple.view.activities.CreateNewsActivity
import com.iuturakulov.hseapple.view.adapters.NewsAdapter
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.toolbar_create_news.*

class NewsFragment : Fragment(R.layout.fragment_news) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        news_info.text = "Новости"
        if (USER_CHAT.role != "teacher") {
            create_event_layout.visibility = View.GONE
        } else {
            create_event_layout.visibility = View.VISIBLE
        }
        val newsAdapter = NewsAdapter(APP_ACTIVITY.applicationContext)
        newsRecyclerView.adapter = newsAdapter
        initializeAdapter(newsAdapter)
        create_event_tool.setOnClickListener {
            showToast("Create event")
            val startupIntent =
                Intent(APP_ACTIVITY.applicationContext, CreateNewsActivity::class.java)
            startActivity(startupIntent)
            APP_ACTIVITY.overridePendingTransition(
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )
            val coursesAdapter = NewsAdapter(requireContext())
            newsRecyclerView.adapter = coursesAdapter
            initializeAdapter(coursesAdapter)
        }
    }

    private fun initializeAdapter(coursesAdapter: NewsAdapter) {
        if (coursesAdapter.getAllItems().isNotEmpty()) {
            isEventsEmptyImage.visibility = View.GONE
            isEventsEmptyText.visibility = View.GONE
        } else {
            isEventsEmptyImage.visibility = View.VISIBLE
            isEventsEmptyText.visibility = View.VISIBLE
        }
    }
}