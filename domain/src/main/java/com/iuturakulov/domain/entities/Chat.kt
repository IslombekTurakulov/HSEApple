package com.iuturakulov.domain.entities

data class Chat(
    val id: String,
    val participants: List<String>,
    val lastMessage: Message,
    val createdAt: Long,
    val updatedAt: Long
)