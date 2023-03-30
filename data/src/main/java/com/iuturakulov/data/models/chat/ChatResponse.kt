package com.iuturakulov.data.models.chat

import com.iuturakulov.domain.entities.ChatEntity

data class ChatResponse(
    val success: Boolean,
    val chat: ChatEntity
)
