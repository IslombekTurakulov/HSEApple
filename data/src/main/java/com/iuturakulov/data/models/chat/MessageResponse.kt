package com.iuturakulov.data.models.chat

import com.iuturakulov.domain.entities.Message

data class MessageResponse(
    val success: Boolean,
    val message: Message
)