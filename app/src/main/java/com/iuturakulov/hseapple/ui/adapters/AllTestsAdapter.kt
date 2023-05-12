package com.iuturakulov.hseapple.ui.adapters

import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.api.OkHttpInstance
import com.iuturakulov.hseapple.utils.*
import kotlinx.android.synthetic.main.component_test.view.*
import timber.log.Timber
import java.io.IOException

class AllTestsAdapter : RecyclerView.Adapter<AllTestsAdapter.DataViewHolder>() {

    private var completedTestsList: ArrayList<com.iuturakulov.domain.entities.UserTaskEntity> =
        arrayListOf()

    private fun reloadItems() {
        try {
            val requestHttp =
                "course/task/${if (SELECTION == CourseSelection.CHOSEN_SECOND) 1 else 2}?status=true&form=lab"
            val response = OkHttpInstance.getInstance().newCall(
                OkHttpInstance.getRequest(
                    requestHttp, TEMP_TOKEN
                )
            ).execute()
            Timber.d("Tests: ${response.isSuccessful}")
            if (response.isSuccessful) {
                val tempArr =
                    Gson().fromJson(
                        response.body()?.string() ?: "",
                        Array<com.iuturakulov.domain.entities.UserTaskEntity>::class.java
                    )
                if (!tempArr.isNullOrEmpty()) {
                    completedTestsList.clear()
                    completedTestsList.addAll(tempArr)
                    completedTestsList.removeAll {
                        it.user.id != USER.id
                    }
                }
            }
        } catch (exception: IOException) {
            exception.printStackTrace()
        }
    }

    fun getAllItems(): ArrayList<com.iuturakulov.domain.entities.UserTaskEntity> {
        return completedTestsList
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
        fun bind(itemTask: com.iuturakulov.domain.entities.UserTaskEntity) {
            itemView.nameOfTask.text = allTests.find { it.id == itemTask.task.id }?.title
            itemView.deadlineOfTask.text = itemTask.task.dueDate.toString()
            itemView.beginTask.text =
                "${if (itemTask.task.title == "null") 0 else itemTask.task.title}/10"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.component_test, parent,
                false
            )
        )

    override fun getItemCount(): Int = completedTestsList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(completedTestsList[position])
    }

}