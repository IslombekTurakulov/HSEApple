package com.iuturakulov.hseapple.view.adapters

import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.model.api.PostEntity
import com.iuturakulov.hseapple.utils.postInfo
import com.iuturakulov.hseapple.view.activities.NewsInfoActivity
import kotlinx.android.synthetic.main.component_event.view.*
import java.io.ByteArrayOutputStream
import java.util.*

class NewsAdapter(
    private val news: Array<PostEntity>
) : RecyclerView.Adapter<NewsAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(itemNews: PostEntity) {
            itemView.newsTitleItem.text = itemNews.title
            itemView.newsDescriptionItem.text = itemNews.content
            Glide.with(itemView.imageNewsItem.context)
                .load(itemNews.mediaLink)
                .into(itemView.imageNewsItem)
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
        val rnd = Random()
        val random = rnd.nextInt()
        if (news[position].mediaLink.isNullOrEmpty()) {
            holder.itemView.imageNewsItem.setImageDrawable(holder.itemView.context.getDrawable(if (random % 2 == 0) R.drawable.good_night_img else R.drawable.good_morning_img))
        }
        holder.itemView.seeNewsItem.setOnClickListener {
            val intent =
                Intent(holder.itemView.context, NewsInfoActivity::class.java)
            postInfo = news[position]
            val baos = encodeDrawable(random, holder)
            postInfo.mediaLink = Base64.getEncoder().encodeToString(baos.toByteArray())
            holder.itemView.context.startActivity(intent)
        }
    }

    private fun encodeDrawable(
        random: Int,
        holder: DataViewHolder
    ): ByteArrayOutputStream {
        val resourceId: Int =
            if (random % 2 == 0) R.drawable.good_night_img else R.drawable.good_morning_img
        val input = holder.itemView.context.contentResolver.openInputStream(
            (Uri.Builder())
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(holder.itemView.resources.getResourcePackageName(resourceId))
                .appendPath(holder.itemView.resources.getResourceTypeName(resourceId))
                .appendPath(holder.itemView.resources.getResourceEntryName(resourceId))
                .build()
        )
        val baos = ByteArrayOutputStream()
        BitmapFactory.decodeStream(input, null, null)
            ?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        return baos
    }


}