package com.iuturakulov.hseapple.view.adapters

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.model.api.PostEntity
import com.iuturakulov.hseapple.utils.CourseSelection
import com.iuturakulov.hseapple.utils.SELECTION
import com.iuturakulov.hseapple.utils.TEMP_TOKEN
import com.iuturakulov.hseapple.utils.postInfo
import com.iuturakulov.hseapple.view.activities.NewsInfoActivity
import kotlinx.android.synthetic.main.component_event.view.*
import okhttp3.OkHttpClient
import okhttp3.Request
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*

class NewsAdapter(
    context: Context
) : RecyclerView.Adapter<NewsAdapter.DataViewHolder>() {

    private var news: ArrayList<PostEntity> = arrayListOf()

    private fun reloadItems() {
        val client = OkHttpClient().newBuilder()
            .build()
        val request = Request.Builder()
            .url("http://80.66.64.53:8080/course/${if (SELECTION == CourseSelection.CHOSEN_SECOND) 1 else 2}/post?start=1")
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
        val sdkInt = android.os.Build.VERSION.SDK_INT;
        if (sdkInt > 8) {
            val policy: StrictMode.ThreadPolicy = StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            reloadItems()
        }
    }

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(itemNews: PostEntity) {
            itemView.newsTitleItem.text = itemNews.title
            itemView.newsDescriptionItem.text = itemNews.content
            if (itemNews.mediaLink.isNullOrEmpty()) {
                itemView.imageNewsItem.setImageDrawable(itemView.context.getDrawable(R.drawable.good_night_img))
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
                    .authority(itemView.resources.getResourcePackageName(R.drawable.good_night_img))
                    .appendPath(itemView.resources.getResourceTypeName(R.drawable.good_night_img))
                    .appendPath(itemView.resources.getResourceEntryName(R.drawable.good_night_img))
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

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(news[position])
    }

}