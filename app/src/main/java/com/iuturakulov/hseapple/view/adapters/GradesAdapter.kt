package com.iuturakulov.hseapple.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.model.models.Grade
import kotlinx.android.synthetic.main.component_grade.view.*
import java.text.SimpleDateFormat

class GradesAdapter(
    private val users: ArrayList<Grade>
) : RecyclerView.Adapter<GradesAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: Grade) {
            itemView.nameOfTask.text = user.nameOfTask
            itemView.btnGradeOfTask.text = user.grade.toString()
            itemView.deadlineOfTask.text = SimpleDateFormat("yyyyMMdd_HHmmss").format(user.date.toLocalTime())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.component_grade, parent,
                false
            )
        )

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(users[position])

    fun addData(list: List<Grade>) {
        users.addAll(list)
    }

}