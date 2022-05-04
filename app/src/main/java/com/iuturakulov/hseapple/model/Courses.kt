package com.iuturakulov.hseapple.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Courses(@SerializedName("name_of_course") @Expose val nameOfCourse: String,
                   @SerializedName("image_course") @Expose val image: String?,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Courses

        if (nameOfCourse != other.nameOfCourse) return false

        return true
    }

    override fun hashCode(): Int {
        return nameOfCourse.hashCode()
    }
}