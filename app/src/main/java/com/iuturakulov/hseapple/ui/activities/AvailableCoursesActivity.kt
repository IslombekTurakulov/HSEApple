package com.iuturakulov.hseapple.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.model.Courses
import com.iuturakulov.hseapple.ui.adapters.CoursesAdapter
import com.iuturakulov.hseapple.ui.fragments.ProfileFragment
import kotlinx.android.synthetic.main.activity_available_courses.*
import kotlinx.android.synthetic.main.toolbar_available_courses.*


class AvailableCoursesActivity : AppCompatActivity(R.layout.activity_available_courses) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_available_courses)
        title = getString(R.string.available_courses)
        val list: ArrayList<Courses> = arrayListOf()
        list.add(Courses(resources.getString(R.string.second_course), ""))
        list.add(Courses(resources.getString(R.string.third_course), ""))
        updateItems(list)
        available_courses_text.text = title
        profile_person_view.setOnClickListener {
           supportFragmentManager.beginTransaction()
                .replace(R.id.frame, ProfileFragment()).commit()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }

    private fun updateItems(list: ArrayList<Courses>) {
        val coursesAdapter = CoursesAdapter(list)
        coursesRecyclerView.adapter = coursesAdapter
        swipe_refresh_layout.setOnRefreshListener {
            coursesAdapter.updateData(list)
            swipe_refresh_layout.isRefreshing = false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_available_courses, menu)
        return true
    }
}