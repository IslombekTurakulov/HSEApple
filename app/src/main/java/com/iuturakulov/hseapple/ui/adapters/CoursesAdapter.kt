package com.iuturakulov.hseapple.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.api.OkHttpInstance
import com.iuturakulov.hseapple.model.Courses
import com.iuturakulov.hseapple.model.RequestEntity
import com.iuturakulov.hseapple.ui.activities.MainActivity
import com.iuturakulov.hseapple.ui.adapters.CoursesAdapter.DataViewHolder
import com.iuturakulov.hseapple.utils.CourseSelection
import com.iuturakulov.hseapple.utils.SELECTION
import com.iuturakulov.hseapple.utils.TEMP_TOKEN
import com.iuturakulov.hseapple.utils.arrayOfRequestCourses
import kotlinx.android.synthetic.main.component_list_course.view.*
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

    fun updateData(courses: ArrayList<Courses>?) {
        if (!courses.isNullOrEmpty()) {
            this.courses = courses
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
            if (secondCourse != null && secondCourse.approved == true) {
                holder.courseButton.text =
                    holder.resources.getString(R.string.get_a_request_course)
            }
            arrayOfRequestCourses.add(secondCourse!!)
        } else {
            val thirdCourse = initializeButtons(2)
            if (thirdCourse != null && thirdCourse.approved == true) {
                holder.courseButton.text =
                    holder.resources.getString(R.string.get_a_request_course)
            }
            arrayOfRequestCourses.add(thirdCourse!!)
        }
    }

    private fun initializeButtons(courseId: Int): RequestEntity? {
        var response: Response? = null
        try {
            response = OkHttpInstance.getInstance()
                .newCall(OkHttpInstance.getRequest("user/request/${courseId}/1", TEMP_TOKEN))
                .execute()
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