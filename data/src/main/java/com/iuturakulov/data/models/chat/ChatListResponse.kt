package com.iuturakulov.data.models.chat

import com.iuturakulov.domain.entities.Chat
import com.iuturakulov.domain.entities.ChatEntity

data class ChatListResponse(
    val chats: List<Chat>
)