package com.iuturakulov.hseapple.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.ui.activities.CreateTestsActivity
import com.iuturakulov.hseapple.ui.adapters.CompletedTestsAdapter
import com.iuturakulov.hseapple.ui.adapters.CurrentTestsAdapter
import com.iuturakulov.hseapple.utils.APP_ACTIVITY
import com.iuturakulov.hseapple.utils.USER_CHAT
import com.iuturakulov.hseapple.utils.showToast
import kotlinx.android.synthetic.main.fragment_tests.*
import kotlinx.android.synthetic.main.toolbar_news.*

class TestsFragment : Fragment(R.layout.fragment_tests) {

    lateinit var currentTestsAdapter: CurrentTestsAdapter
    lateinit var completedTestsAdapter: CompletedTestsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tests, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (USER_CHAT!!.role != "teacher") {
            create_lab_task.visibility = View.INVISIBLE
        } else {
            create_lab_task.visibility = View.VISIBLE
        }
        news_text_info.text = requireActivity().getString(R.string.tests_menu_text)
        currentTestsAdapter = CurrentTestsAdapter()
        completedTestsAdapter = CompletedTestsAdapter()
        currentTestsRecyclerView.adapter = currentTestsAdapter
        completedTestsRecyclerView.adapter = completedTestsAdapter
        initializeCurrentTests()
        initializeCompletedTests()
        create_lab_task.setOnClickListener {
            showToast("Lab task selected")
            val startupIntent =
                Intent(APP_ACTIVITY.applicationContext, CreateTestsActivity::class.java)
            startActivity(startupIntent)
            APP_ACTIVITY.overridePendingTransition(
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )
            currentTestsAdapter = CurrentTestsAdapter()
            completedTestsAdapter = CompletedTestsAdapter()
            currentTestsRecyclerView.adapter = currentTestsAdapter
            completedTestsRecyclerView.adapter = completedTestsAdapter
            initializeCurrentTests()
            initializeCompletedTests()
        }
    }

    private fun initializeCompletedTests() {
        if (completedTestsAdapter.getAllItems().isEmpty()) {
            isCompletedTestsEmptyImage.visibility = View.VISIBLE
            isCompletedTestsEmptyText.visibility = View.VISIBLE
        } else {
            isCompletedTestsEmptyImage.visibility = View.GONE
            isCompletedTestsEmptyText.visibility = View.GONE
        }
    }

    private fun initializeCurrentTests() {
        if (currentTestsAdapter.getAllItems().isEmpty()) {
            isCurrentTestsEmptyImage.visibility = View.VISIBLE
            isCurrentTestsEmptyText.visibility = View.VISIBLE
        } else {
            isCurrentTestsEmptyImage.visibility = View.GONE
            isCurrentTestsEmptyText.visibility = View.GONE
        }
    }
}