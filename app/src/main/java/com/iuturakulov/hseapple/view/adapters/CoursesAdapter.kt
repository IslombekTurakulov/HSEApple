package com.iuturakulov.hseapple.view.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.model.api.RequestEntity
import com.iuturakulov.hseapple.model.models.Courses
import com.iuturakulov.hseapple.utils.CourseSelection
import com.iuturakulov.hseapple.utils.SELECTION
import com.iuturakulov.hseapple.utils.arrayOfRequestCourses
import com.iuturakulov.hseapple.view.activities.MainActivity
import com.iuturakulov.hseapple.view.adapters.CoursesAdapter.DataViewHolder
import kotlinx.android.synthetic.main.component_list_course.view.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class CoursesAdapter(
    private var courses: ArrayList<Courses>
) : RecyclerView.Adapter<DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(itemNews: Courses) {
            itemView.nameOfCourseField.text = itemNews.nameOfCourse
            if (!itemNews.image.isNullOrEmpty()) {
                Glide.with(itemView.courseImage.context)
                    .load(itemNews.image)
                    .into(itemView.courseImage)
            } else {
                itemView.courseImage.setImageDrawable(itemView.context.getDrawable(R.drawable.app_logo))
            }
        }
    }

    fun updateData(users: ArrayList<Courses>?) {
        if (!users.isNullOrEmpty()) {
            courses = users
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.component_list_course, parent,
                false
            )
        )

    override fun getItemCount(): Int = courses.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(courses[position])
        val course: Courses = courses[position]
        val itemView: View = holder.itemView
        // initializePreRequests(course, itemView)
        holder.itemView.courseButton.setOnClickListener {
            SELECTION =
                if (courses[position].nameOfCourse == holder.itemView.resources.getString(R.string.second_course)) {
                    CourseSelection.CHOSEN_SECOND
                } else {
                    CourseSelection.CHOSEN_THIRD
                }
            val intent = Intent(holder.itemView.context, MainActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            holder.itemView.context.startActivity(intent)
            Toast.makeText(holder.itemView.context, "Loading", Toast.LENGTH_SHORT).show()
         /*   if (holder.itemView.courseButton.text == holder.itemView.resources.getString(R.string.make_a_request_course)) {
                when (courses[position].nameOfCourse) {
                    holder.itemView.resources.getString(R.string.second_course) -> {
                        initializeButtons(1)
                    }
                    else -> {
                        initializeButtons(2)
                    }
                }
            } else {
                SELECTION =
                    if (courses[position].nameOfCourse == holder.itemView.resources.getString(R.string.second_course)) {
                        CourseSelection.CHOSEN_SECOND
                    } else {
                        CourseSelection.CHOSEN_THIRD
                    }
                val intent = Intent(holder.itemView.context, MainActivity::class.java)
                holder.itemView.context.startActivity(intent)
                Toast.makeText(holder.itemView.context, "Loading", Toast.LENGTH_SHORT).show()
            }*/
        }
    }

    private fun initializePreRequests(
        position: Courses,
        holder: View
    ) {
        if (position.nameOfCourse == holder.resources.getString(R.string.second_course)) {
            val secondCourse = initializeButtons(1)
            if (secondCourse != null && secondCourse.approved) {
                holder.courseButton.text =
                    holder.resources.getString(R.string.get_a_request_course)
            }
            arrayOfRequestCourses.add(secondCourse!!)
        } else {
            val thirdCourse = initializeButtons(2)
            if (thirdCourse != null && thirdCourse.approved) {
                holder.courseButton.text =
                    holder.resources.getString(R.string.get_a_request_course)
            }
            arrayOfRequestCourses.add(thirdCourse!!)
        }
    }

    private fun initializeButtons(courseId: Int): RequestEntity? {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("http:80.66.64.53:8080/user/request/${courseId}/1")
            .get()
            .addHeader("Content-Type", "application/json")
            .addHeader("token", "123")
            .build()
        var response: Response? = null
        try {
            response = client.newCall(request).execute()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        if (response != null) {
            return Gson().fromJson(
                response.body()?.string() ?: "",
                RequestEntity::class.java
            )
        }
        return null
    }
}