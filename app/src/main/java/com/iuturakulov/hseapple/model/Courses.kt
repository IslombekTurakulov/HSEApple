package com.iuturakulov.hseapple.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Courses(@SerializedName("name_of_course") @Expose val nameOfCourse: String,
                   @SerializedName("image_course") @Expose val image: String?,
)