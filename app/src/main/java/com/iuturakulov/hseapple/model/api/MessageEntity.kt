package com.iuturakulov.hseapple.model.api

import java.sql.Timestamp

data class MessageEntity(
    val id: Long? = null,
    val chatID: Long? = null,
    val userID: Long? = null,
    val replyTo: Long? = null,
    val message: String? = null,
    val mediaLink: String? = null,
    val docLink: String? = null,
    val createdAt: Timestamp? = null,
    val updatedAt: Timestamp? = null
)