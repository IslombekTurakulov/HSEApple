package com.iuturakulov.domain.usecases

import com.iuturakulov.domain.entities.ChatEntity
import com.iuturakulov.domain.entities.Message
import com.iuturakulov.domain.repositories.ChatRepository

class ChatUseCase(private val chatRepository: ChatRepository) {
    suspend fun getChat(chatId: String) = chatRepository.getChat(chatId)
    suspend fun getChats() = chatRepository.getChats()
    suspend fun createChat(chat: ChatEntity) = chatRepository.createChat(chat)
    suspend fun updateChat(chat: ChatEntity) = chatRepository.updateChat(chat)
    suspend fun deleteChat(chatId: String) = chatRepository.deleteChat(chatId)
    suspend fun getChatList(userId: String) = chatRepository.getChatList(userId)
    suspend fun sendMessage(message: Message) = chatRepository.sendMessage(message)
    suspend fun getMessages(chatId: String) = chatRepository.getMessages(chatId)
    suspend fun markMessageAsRead(messageId: String) = chatRepository.markMessageAsRead(messageId)
}