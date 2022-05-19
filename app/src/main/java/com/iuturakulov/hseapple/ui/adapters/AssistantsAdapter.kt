package com.iuturakulov.hseapple.ui.adapters

import android.content.Context
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cometchat.pro.models.User
import com.google.gson.Gson
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.utils.API_KEY
import com.iuturakulov.hseapple.utils.APP_ID
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber
import java.io.IOException

class AssistantsAdapter(context: Context) :
    RecyclerView.Adapter<AssistantsAdapter.ViewHolder?>() {
    private val context: Context
    private lateinit var mItems: ArrayList<User>

    private fun reloadItems() {
        try {
            mItems = ArrayList()
            val client = OkHttpClient().newBuilder()
                .build()
            val request = Request.Builder()
                .url("https://${APP_ID}.api-eu.cometchat.io/v3/users?searchIn=&count=false&perPage=100&page=1&withTags=false&roles=assistant")
                .get()
                .addHeader("Accept", "application/json")
                .addHeader("apiKey", API_KEY)
                .addHeader("Content-Type", "application/json")
                .build()
            var response: Response? = null
            try {
                response = client.newCall(request).execute()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            if (response != null) {
                val res = Gson().fromJson(
                    response.body()?.string() ?: "",
                    Array<User>::class.java
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
            holder.nameOfPerson.text = mItems[position].name.toString()
            holder.descriptionOfPerson.text = mItems[position].role
        } catch (e: Exception) {
            Timber.e(e.message!!)
        }
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    fun addItem(item: User, position: Int) {
        try {
            mItems.add(position, item)
            notifyItemInserted(position)
        } catch (e: Exception) {
            Timber.e(e.message!!)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameOfPerson: TextView = itemView.findViewById<View>(R.id.nameOfPerson) as TextView
        var descriptionOfPerson: TextView =
            itemView.findViewById<View>(R.id.descriptionOfPerson) as TextView
    }

    fun removeItem(position: Int): User? {
        var item: User? = null
        try {
            item = mItems[position]
            mItems.removeAt(position)
            notifyItemRemoved(position)
        } catch (e: Exception) {
            Timber.e(e.message!!)
        }
        return item
    }

    fun getAllItems(): ArrayList<User> {
        return mItems
    }

    init {
        val SDK_INT = android.os.Build.VERSION.SDK_INT
        if (SDK_INT > 8) {
            val policy: StrictMode.ThreadPolicy = StrictMode.ThreadPolicy.Builder()
                .permitAll().build()
            StrictMode.setThreadPolicy(policy)
            reloadItems()
        }
        this.context = context
    }
}
