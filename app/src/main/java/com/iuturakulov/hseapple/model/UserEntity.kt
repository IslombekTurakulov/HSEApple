package com.iuturakulov.hseapple.model

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class UserEntity(
    val id: Long? = null,
    var firstname: String? = null,
    var lastname: String? = null,
    @SerializedName("commonname")
    val fullName: String? = null,
    var email: String? = null,
    @SerializedName("created_at")
    var createdAt: Timestamp? = null
)