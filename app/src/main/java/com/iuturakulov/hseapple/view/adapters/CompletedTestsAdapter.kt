package com.iuturakulov.hseapple.view.adapters

import android.content.Intent
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.model.TaskEntity
import com.iuturakulov.hseapple.utils.allTests
import com.iuturakulov.hseapple.utils.taskInfo
import com.iuturakulov.hseapple.view.activities.TaskInfoActivity
import kotlinx.android.synthetic.main.component_test.view.*

class CompletedTestsAdapter(
) : RecyclerView.Adapter<CompletedTestsAdapter.DataViewHolder>() {

    private var news: ArrayList<TaskEntity> = arrayListOf()

    private fun reloadItems() {
        news.addAll(allTests)
        news.removeAll {
            !it.isStatus
        }
    }

    fun getAllItems(): ArrayList<TaskEntity> {
        return news
    }

    init {
        val sdkInt = android.os.Build.VERSION.SDK_INT;
        if (sdkInt > 8) {
            val policy: StrictMode.ThreadPolicy = StrictMode.ThreadPolicy.Builder()
                .permitAll().build()
            StrictMode.setThreadPolicy(policy)
            reloadItems()
        }
    }

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(itemTask: TaskEntity) {
            itemView.nameOfTask.text = itemTask.title
            itemView.deadlineOfTask.text = itemTask.createdAt.toString()
            itemView.beginTask.setOnClickListener {
                val intent =
                    Intent(itemView.context, TaskInfoActivity::class.java)
                taskInfo = itemTask
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.component_test, parent,
                false
            )
        )

    override fun getItemCount(): Int = news.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(news[position])
    }

}