package com.iuturakulov.domain.entities

data class Message(
    val id: String,
    val chatId: String,
    val senderId: String,
    val receiverId: String,
    val content: String,
    val timestamp: Long
)