package com.iuturakulov.hseapple.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class UserEntity(
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("commonname")
    val fullName: String? = null,
    @SerializedName("email")
    var email: String? = null,
    @SerializedName("created_at")
    var createdAt: LocalDateTime? = null,
)