package com.iuturakulov.hseapple.ui.adapters

import android.content.Context
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.api.OkHttpInstance
import com.iuturakulov.domain.entities.RequestEntity
import com.iuturakulov.hseapple.utils.CourseSelection
import com.iuturakulov.hseapple.utils.SELECTION
import com.iuturakulov.hseapple.utils.TEMP_TOKEN
import okhttp3.Response
import timber.log.Timber
import java.io.IOException

internal class RequestsAdapter(context: Context) :
    RecyclerView.Adapter<RequestsAdapter.ViewHolder?>() {
    private val context: Context
    private lateinit var mItems: ArrayList<com.iuturakulov.domain.entities.RequestEntity>

    private fun reloadItems() {
        try {
            mItems = ArrayList()
            val requestHttp =
                "course/${if (SELECTION == CourseSelection.CHOSEN_SECOND) 1 else 2}/application/list?approved=false"
            var response: Response? = null
            try {
                response = OkHttpInstance.getInstance()
                    .newCall(OkHttpInstance.getRequest(requestHttp, TEMP_TOKEN)).execute()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            if (response != null) {
                val res = Gson().fromJson(
                    response.body()?.string() ?: "",
                    Array<com.iuturakulov.domain.entities.RequestEntity>::class.java
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
        mItems.add(com.iuturakulov.domain.entities.RequestEntity(1, 1, 1, 1, false))
        mItems.add(com.iuturakulov.domain.entities.RequestEntity(2, 2, 1, 1, false))
        mItems.add(com.iuturakulov.domain.entities.RequestEntity(3, 3, 1, 1, false))
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
            when {
                mItems[position].userID.toString() == "1" -> {
                    holder.nameOfPerson.text = "Иванов Иван Иванович"
                }
                mItems[position].userID.toString() == "2" -> {
                    holder.nameOfPerson.text = "Жулин Артем Германович"
                }
                else -> {
                    holder.nameOfPerson.text = "Тасбауова Даяна Алексеевна"
                }
            }
            holder.descriptionOfPerson.text =
                if (mItems[position].courseID == 1L) "SECOND COURSE, approved: ${mItems[position].approved}" else "THIRD COURSE, approved: ${mItems[position].approved}"
        } catch (e: Exception) {
            Timber.e(e.message!!)
        }
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    fun addItem(item: com.iuturakulov.domain.entities.RequestEntity, position: Int) {
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

    fun removeItem(position: Int): com.iuturakulov.domain.entities.RequestEntity? {
        var item: com.iuturakulov.domain.entities.RequestEntity? = null
        try {
            item = mItems[position]
            mItems.removeAt(position)
            notifyItemRemoved(position)
        } catch (e: Exception) {
            Timber.e(e.message!!)
        }
        return item
    }

    fun getAllItems(): ArrayList<com.iuturakulov.domain.entities.RequestEntity> {
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
