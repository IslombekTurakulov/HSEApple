package com.iuturakulov.domain.entities

data class Comment(
    val id: String,
    val postId: String,
    val userId: String,
    val content: String,
    val timestamp: Long
)