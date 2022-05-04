package com.iuturakulov.hseapple.view.adapters

import android.content.Intent
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.model.TaskEntity
import com.iuturakulov.hseapple.utils.*
import com.iuturakulov.hseapple.view.activities.TaskInfoActivity
import kotlinx.android.synthetic.main.component_event.view.*
import kotlinx.android.synthetic.main.component_test.view.*
import okhttp3.OkHttpClient
import okhttp3.Request
import timber.log.Timber
import java.io.IOException
import java.util.*

class CurrentTestsAdapter(
) : RecyclerView.Adapter<CurrentTestsAdapter.DataViewHolder>() {

    private var news: ArrayList<TaskEntity> = arrayListOf()

    private fun reloadItems() {
        val client = OkHttpClient().newBuilder()
            .build()
        val request = Request.Builder()
            .url("http://80.66.64.53:8080/course/${if (SELECTION == CourseSelection.CHOSEN_SECOND) 1 else 2}/task?start=1")
            .method("GET", null)
            .addHeader(
                "Authorization",
                TEMP_TOKEN
            )
            .addHeader("Content-Type", "application/json")
            .addHeader("Cookie", "JSESSIONID=53530B6092B00A54239E5E86BAEE3EE6")
            .build()
        try {
            val response = client.newCall(request).execute()
            Timber.d("Tests: ${response.isSuccessful}")
            if (response.isSuccessful) {
                val tempArr =
                    Gson().fromJson(response.body()?.string() ?: "", Array<TaskEntity>::class.java)
                if (!tempArr.isNullOrEmpty()) {
                    /*   allTests.clear()
                       news.clear()*/
                    allTests.addAll(tempArr)
                    news.addAll(tempArr)
                    news.removeAll {
                        it.isStatus
                    }
                }
            }
        } catch (exception: IOException) {
            exception.printStackTrace()
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