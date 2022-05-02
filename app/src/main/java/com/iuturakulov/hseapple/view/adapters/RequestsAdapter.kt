package com.iuturakulov.hseapple.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.model.api.RequestEntity
import com.iuturakulov.hseapple.utils.TOKEN_API
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
            val client = OkHttpClient()
            // Third course validation
            val request = Request.Builder()
                .url("80.66.64.53:8080/course/1/application/list?approved=false")
                .get()
                .addHeader("Content-Type", "application/json")
                .addHeader("Prefer", "code=200")
                .addHeader("token", TOKEN_API)
                .build()
            var response: Response? = null
            try {
                response = client.newCall(request).execute()
            } catch (e: IOException) {
                e.printStackTrace();
            }
            if (response != null) {
                mItems.addAll(
                    Gson().fromJson(
                        response.body()?.string() ?: "",
                        Array<RequestEntity>::class.java
                    )
                )
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
        reloadItems()
        this.context = context
    }
}
