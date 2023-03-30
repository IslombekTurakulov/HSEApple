package com.iuturakulov.domain.entities

// ChatEntity.kt
data class ChatEntity(
    val id: String,
    val participants: List<UserEntity>,
    val messages: List<Message>
)