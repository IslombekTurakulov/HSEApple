package com.iuturakulov.domain.entities

import java.util.*

data class PostEntity(
    val id: String,
    val author: UserEntity,
    val content: String,
    val mediaLink: String?,
    val timestamp: Date,
    val likes: Int,
    val comments: List<Comment>
)