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
import com.iuturakulov.hseapple.view.adapters.CompletedTestsAdapter
import com.iuturakulov.hseapple.view.adapters.NewsAdapter
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.toolbar_create_news.*

class NewsFragment : Fragment(R.layout.fragment_news) {

    lateinit var newsAdapter: NewsAdapter
    lateinit var completedTestsAdapter: CompletedTestsAdapter

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
        newsAdapter = NewsAdapter(APP_ACTIVITY.applicationContext)
        completedTestsAdapter = CompletedTestsAdapter()
        newsRecyclerView.adapter = newsAdapter
        gradesRecyclerView.adapter = completedTestsAdapter
        initializeNewsAdapter()
        initializeGradesAdapter()
        create_event_tool.setOnClickListener {
            showToast("Create event")
            val startupIntent =
                Intent(APP_ACTIVITY.applicationContext, CreateNewsActivity::class.java)
            startActivity(startupIntent)
            APP_ACTIVITY.overridePendingTransition(
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )
            newsAdapter = NewsAdapter(requireContext())
            newsRecyclerView.adapter = newsAdapter
            initializeNewsAdapter()
        }
    }

    private fun initializeNewsAdapter() {
        if (newsAdapter.getAllItems().isNotEmpty()) {
            isEventsEmptyImage.visibility = View.GONE
            isEventsEmptyText.visibility = View.GONE
        } else {
            isEventsEmptyImage.visibility = View.VISIBLE
            isEventsEmptyText.visibility = View.VISIBLE
        }
    }

    private fun initializeGradesAdapter() {
        if (completedTestsAdapter.getAllItems().isNotEmpty()) {
            isGradesEmptyImage.visibility = View.GONE
            isGradesEmptyText.visibility = View.GONE
        } else {
            isGradesEmptyImage.visibility = View.VISIBLE
            isGradesEmptyText.visibility = View.VISIBLE
        }
    }
}