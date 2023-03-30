package com.iuturakulov.domain.repositories

import com.iuturakulov.domain.entities.Chat
import com.iuturakulov.domain.entities.ChatEntity
import com.iuturakulov.domain.entities.Message

interface ChatRepository {
    suspend fun getChat(chatId: String): ChatEntity
    suspend fun getChats(): List<ChatEntity>
    suspend fun createChat(chat: ChatEntity): Boolean
    suspend fun updateChat(chat: ChatEntity): Boolean
    suspend fun deleteChat(chatId: String): Boolean
    suspend fun getChatList(userId: String): List<Chat>
    suspend fun sendMessage(message: Message)
    suspend fun getMessages(chatId: String): List<Message>
    suspend fun markMessageAsRead(messageId: String)
}

