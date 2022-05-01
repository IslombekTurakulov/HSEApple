package com.iuturakulov.hseapple.view.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.model.models.Courses
import com.iuturakulov.hseapple.utils.*
import com.iuturakulov.hseapple.view.activities.MainActivity
import com.iuturakulov.hseapple.view.adapters.CoursesAdapter.DataViewHolder
import kotlinx.android.synthetic.main.component_list_course.view.*

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
        holder.itemView.courseButton.setOnClickListener {
            SELECTION =
                if (courses[position].nameOfCourse == holder.itemView.resources.getString(R.string.second_course)) {
                    CourseSelection.CHOSEN_SECOND
                } else {
                    CourseSelection.CHOSEN_THIRD
                }
            val intent = Intent(holder.itemView.context, MainActivity::class.java)
            holder.itemView.context.startActivity(intent)
            Toast.makeText(holder.itemView.context, "Loading", Toast.LENGTH_SHORT).show()
        }
    }


    fun addData(list: List<Courses>) {
        courses.addAll(list)
    }

}