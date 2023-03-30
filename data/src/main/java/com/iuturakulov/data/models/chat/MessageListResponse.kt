package com.iuturakulov.data.models.chat

import com.iuturakulov.domain.entities.Message

data class MessageListResponse(
    val messages: List<Message>
)