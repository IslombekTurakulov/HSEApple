package com.iuturakulov.hseapple.ui.adapters

import android.content.Intent
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.api.OkHttpInstance
import com.iuturakulov.domain.entities.TaskEntity
import com.iuturakulov.domain.entities.UserTaskEntity
import com.iuturakulov.hseapple.ui.activities.TaskInfoActivity
import com.iuturakulov.hseapple.utils.*
import kotlinx.android.synthetic.main.component_event.view.*
import kotlinx.android.synthetic.main.component_test.view.*
import timber.log.Timber
import java.io.IOException
import java.util.*

class CurrentTestsAdapter : RecyclerView.Adapter<CurrentTestsAdapter.DataViewHolder>() {

    private var news: ArrayList<com.iuturakulov.domain.entities.TaskEntity> = arrayListOf()

    private fun reloadItems() {
        try {
            val requestHttp =
                "course/${if (SELECTION == CourseSelection.CHOSEN_SECOND) 1 else 2}/task?start=1"
            val response = OkHttpInstance.getInstance().newCall(
                OkHttpInstance.getRequest(
                    requestHttp, ACCESS_TOKEN
                )
            ).execute()
            Timber.d("Tests: ${response.isSuccessful}")
            if (response.isSuccessful) {
                val tempArr =
                    Gson().fromJson(response.body()?.string() ?: "", Array<com.iuturakulov.domain.entities.TaskEntity>::class.java)
                if (!tempArr.isNullOrEmpty()) {
                    news.clear()
                    allTests.clear()
                    allTests.addAll(tempArr)
                    news.addAll(tempArr)
                    news.removeAll {
                        it.status
                    }
                }
            }
        } catch (exception: IOException) {
            exception.printStackTrace()
        }
    }

    fun getAllItems(): ArrayList<com.iuturakulov.domain.entities.TaskEntity> {
        return news
    }

    fun setAllItems(list: ArrayList<com.iuturakulov.domain.entities.UserTaskEntity>) {
        news.removeAll {
            list.find { it2 ->
                it2.id == it.id
            } != null
        }
    }

    init {
        val sdkInt = android.os.Build.VERSION.SDK_INT
        if (sdkInt > 8) {
            val policy: StrictMode.ThreadPolicy = StrictMode.ThreadPolicy.Builder()
                .permitAll().build()
            StrictMode.setThreadPolicy(policy)
            reloadItems()
        }
    }

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(itemTask: com.iuturakulov.domain.entities.TaskEntity) {
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