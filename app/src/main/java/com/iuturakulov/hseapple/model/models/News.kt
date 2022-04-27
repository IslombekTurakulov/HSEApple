package com.iuturakulov.hseapple.model.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.iuturakulov.hseapple.utils.asDateTime
import java.time.LocalDateTime
import java.util.*

class News(
    @SerializedName("title") @Expose val titleOfNewsItem: String = "Title sample",
    @SerializedName("description") @Expose val descriptionOfNews: String = "Description sample",
    @SerializedName("created_date_news") @Expose val createdDate: String = LocalDateTime.now().toString().asDateTime(),
    @SerializedName("image") @Expose val image: String
)