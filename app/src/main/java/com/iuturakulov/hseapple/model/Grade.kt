package com.iuturakulov.hseapple.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class Grade(
    @SerializedName("name_of_task") @Expose val nameOfTask: String,
    @SerializedName("grade") @Expose val grade: Int,
    @SerializedName("deadline") @Expose val date: LocalDateTime
)