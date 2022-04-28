package com.iuturakulov.hseapple.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.model.api.PostEntity
import com.iuturakulov.hseapple.model.models.News
import kotlinx.android.synthetic.main.component_event.view.*

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

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(news[position])

}