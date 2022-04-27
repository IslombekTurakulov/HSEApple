package com.iuturakulov.hseapple.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.model.models.Courses
import com.iuturakulov.hseapple.view.adapters.CoursesAdapter
import kotlinx.android.synthetic.main.fragment_available_courses.*


class AvailableCoursesActivity : AppCompatActivity(R.layout.fragment_available_courses) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Доступные курсы"
        val list: ArrayList<Courses> = arrayListOf()
        list.add(Courses(resources.getString(R.string.second_course), null))
        list.add(Courses(resources.getString(R.string.third_course), null))
        updateItems(list)
    }

    private fun updateItems(list: ArrayList<Courses>) {
        val coursesAdapter = CoursesAdapter(list)
        coursesRecyclerView.adapter = coursesAdapter
        swipe_refresh_layout.setOnRefreshListener {
            coursesAdapter.updateData(list)
            swipe_refresh_layout.isRefreshing = false
        }
    }

}