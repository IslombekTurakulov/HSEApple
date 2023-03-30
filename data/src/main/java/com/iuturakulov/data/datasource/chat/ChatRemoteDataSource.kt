package com.iuturakulov.data.datasource.chat

import com.iuturakulov.data.network.ChatApiService
import com.iuturakulov.domain.entities.Chat
import com.iuturakulov.domain.entities.ChatEntity
import com.iuturakulov.domain.entities.Message
import retrofit2.Response

interface ChatRemoteDataSource {

    suspend fun createChat(chat: ChatEntity): Boolean

    suspend fun updateChat(chat: ChatEntity): Boolean

    suspend fun deleteChat(chatId: String): Boolean

    suspend fun getChatList(userId: String): List<Chat>

    suspend fun sendMessage(message: Message)

    suspend fun getMessages(chatId: String): List<Message>

    suspend fun markMessageAsRead(messageId: String)
}

class ChatRemoteDataSourceImpl(private val apiService: ChatApiService) : ChatRemoteDataSource {

    override suspend fun createChat(chat: ChatEntity): Boolean {
        val response = apiService.createChat(chat)
        return handleResponse(response) { it.success }
    }

    override suspend fun updateChat(chat: ChatEntity): Boolean {
        val response = apiService.updateChat(chat)
        return handleResponse(response) { it.success }
    }

    override suspend fun deleteChat(chatId: String): Boolean {
        val response = apiService.deleteChat(chatId)
        return handleResponse(response) { it.success }
    }

    override suspend fun getChatList(userId: String): List<Chat> {
        val response = apiService.getChatList(userId)
        return handleResponse(response) { it.chats }
    }

    override suspend fun sendMessage(message: Message) {
        val response = apiService.sendMessage(message)
        handleResponse(response) { }
    }

    override suspend fun getMessages(chatId: String): List<Message> {
        val response = apiService.getMessages(chatId)
        return handleResponse(response) { it.messages }
    }

    override suspend fun markMessageAsRead(messageId: String) {
        val response = apiService.markMessageAsRead(messageId)
        handleResponse(response) { }
    }

    private inline fun <T, R> handleResponse(response: Response<T>, block: (T) -> R): R {
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return block(body)
            }
        }
        throw ApiException(response.message())
    }
}

internal class ApiException(message: String) : Exception(message)
