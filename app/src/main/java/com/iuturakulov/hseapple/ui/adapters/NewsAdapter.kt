package com.iuturakulov.hseapple.ui.adapters

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.api.OkHttpInstance
import com.iuturakulov.hseapple.model.PostEntity
import com.iuturakulov.hseapple.ui.activities.NewsInfoActivity
import com.iuturakulov.hseapple.utils.*
import kotlinx.android.synthetic.main.component_event.view.*
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*

class NewsAdapter(
    context: Context
) : RecyclerView.Adapter<NewsAdapter.DataViewHolder>() {

    private var news: ArrayList<PostEntity> = arrayListOf()

    private fun reloadItems() {
        try {
            val requestHttp =
                "course/${if (SELECTION == CourseSelection.CHOSEN_SECOND) 1 else 2}/post?start=1"
            val response = OkHttpInstance.getInstance()
                .newCall(OkHttpInstance.getRequest(requestHttp = requestHttp, TEMP_TOKEN))
                .execute()
            Timber.d("News: ${response.isSuccessful}")
            if (response.isSuccessful) {
                val tempArr =
                    Gson().fromJson(response.body()?.string() ?: "", Array<PostEntity>::class.java)
                if (!tempArr.isNullOrEmpty()) {
                    news.addAll(tempArr)
                }
            }
        } catch (exception: IOException) {
            exception.printStackTrace()
        }
    }

    fun getAllItems(): ArrayList<PostEntity> {
        return news
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
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(itemNews: PostEntity) {
            itemView.newsTitleItem.text = itemNews.title
            itemView.newsDescriptionItem.text = itemNews.content
            if (itemNews.mediaLink.isNullOrEmpty()) {
                itemView.imageNewsItem.setImageDrawable(itemView.context.getDrawable(com.hse.core.R.drawable.settings))
            } else {
                val bytes: ByteArray = Base64.getDecoder().decode(itemNews.mediaLink)
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                itemView.imageNewsItem.setImageBitmap(bitmap)
            }
            itemView.seeNewsItem.setOnClickListener {
                val intent =
                    Intent(itemView.context, NewsInfoActivity::class.java)
                postInfo = itemNews
                itemView.context.startActivity(intent)
            }
        }

        private fun encodeDrawable(
        ): ByteArrayOutputStream {
            val input = itemView.context.contentResolver.openInputStream(
                (Uri.Builder())
                    .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                    .authority(itemView.resources.getResourcePackageName(com.hse.core.R.drawable.settings))
                    .appendPath(itemView.resources.getResourceTypeName(com.hse.core.R.drawable.settings))
                    .appendPath(itemView.resources.getResourceEntryName(com.hse.core.R.drawable.settings))
                    .build()
            )
            val baos = ByteArrayOutputStream()
            BitmapFactory.decodeStream(input, null, null)
                ?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            return baos
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.component_event, parent,
                false
            )
        )

    override fun getItemCount(): Int = news.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(news[position])
    }

}