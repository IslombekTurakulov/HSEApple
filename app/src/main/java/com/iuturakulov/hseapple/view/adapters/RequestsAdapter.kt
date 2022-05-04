package com.iuturakulov.hseapple.view.adapters

import android.content.Context
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.model.RequestEntity
import com.iuturakulov.hseapple.utils.CourseSelection
import com.iuturakulov.hseapple.utils.SELECTION
import com.iuturakulov.hseapple.utils.TEMP_TOKEN
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber
import java.io.IOException

internal class RequestsAdapter(context: Context) :
    RecyclerView.Adapter<RequestsAdapter.ViewHolder?>() {
    private val context: Context
    private lateinit var mItems: ArrayList<RequestEntity>

    private fun reloadItems() {
        try {
            mItems = ArrayList()
            val client = OkHttpClient().newBuilder()
                .build()
            val request = Request.Builder()
                .url("http://80.66.64.53:8080/course/${if (SELECTION ==CourseSelection.CHOSEN_SECOND) 1 else 2}/application/list?approved=false")
                .method("GET", null)
                .addHeader(
                    "Authorization",
                    TEMP_TOKEN
                )
                .addHeader("Content-Type", "application/json")
                .addHeader("Cookie", "JSESSIONID=53530B6092B00A54239E5E86BAEE3EE6")
                .build()
            var response: Response? = null
            try {
                response = client.newCall(request).execute()
            } catch (e: IOException) {
                e.printStackTrace();
            }
            if (response != null) {
                val res = Gson().fromJson(
                    response.body()?.string() ?: "",
                    Array<RequestEntity>::class.java
                )
                if (!res.isNullOrEmpty()) {
                    mItems.addAll(
                        res
                    )
                }
            }
        } catch (e: Exception) {
            Timber.e(e.message!!)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var vh: ViewHolder? = null
        try {
            val view: View =
                LayoutInflater.from(context).inflate(R.layout.layout_requests, parent, false)
            vh = ViewHolder(view)
        } catch (e: Exception) {
            Timber.e(e.message!!)
        }
        return vh!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            holder.nameOfPerson.text = mItems[position].userID.toString()
            holder.descriptionOfPerson.text =
                if (mItems[position].courseID == 0L) "SECOND COURSE" else "THIRD COURSE"
        } catch (e: Exception) {
            Timber.e(e.message!!)
        }
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    fun addItem(item: RequestEntity, position: Int) {
        try {
            mItems.add(position, item)
            notifyItemInserted(position)
        } catch (e: Exception) {
            Timber.e(e.message!!)
        }
    }

    internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameOfPerson: TextView = itemView.findViewById<View>(R.id.nameOfPerson) as TextView
        var descriptionOfPerson: TextView =
            itemView.findViewById<View>(R.id.descriptionOfPerson) as TextView
    }

    fun removeItem(position: Int): RequestEntity? {
        var item: RequestEntity? = null
        try {
            item = mItems[position]
            mItems.removeAt(position)
            notifyItemRemoved(position)
        } catch (e: Exception) {
            Timber.e(e.message!!)
        }
        return item
    }

    fun getAllItems(): ArrayList<RequestEntity> {
        return mItems
    }

    init {
        val SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            val policy: StrictMode.ThreadPolicy = StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            reloadItems()
        }
        this.context = context
    }
}
