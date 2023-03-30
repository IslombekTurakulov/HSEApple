package com.iuturakulov.data.repository

import com.iuturakulov.data.datasource.chat.ChatLocalDataSource
import com.iuturakulov.data.datasource.chat.ChatRemoteDataSource
import com.iuturakulov.domain.entities.Chat
import com.iuturakulov.domain.entities.ChatEntity
import com.iuturakulov.domain.entities.Message
import com.iuturakulov.domain.repositories.ChatRepository

class ChatRepositoryImpl(
    private val remoteDataSource: ChatRemoteDataSource,
    private val localDataSource: ChatLocalDataSource
) : ChatRepository {

    override suspend fun getChat(chatId: String): ChatEntity {
        return localDataSource.getChat(chatId)
    }

    override suspend fun getChats(): List<ChatEntity> {
        return localDataSource.getChats()
    }

    override suspend fun createChat(chat: ChatEntity): Boolean {
        val result = remoteDataSource.createChat(chat)
        if (result) {
            localDataSource.saveChat(chat)
        }
        return result
    }

    override suspend fun updateChat(chat: ChatEntity): Boolean {
        val result = remoteDataSource.updateChat(chat)
        if (result) {
            localDataSource.updateChat(chat)
        }
        return result
    }

    override suspend fun deleteChat(chatId: String): Boolean {
        val result = remoteDataSource.deleteChat(chatId)
        if (result) {
            localDataSource.deleteChat(chatId)
        }
        return result
    }

    override suspend fun getChatList(userId: String): List<Chat> {
        return remoteDataSource.getChatList(userId)
    }

    override suspend fun sendMessage(message: Message) {
        remoteDataSource.sendMessage(message)
    }

    override suspend fun getMessages(chatId: String): List<Message> {
        return remoteDataSource.getMessages(chatId)
    }

    override suspend fun markMessageAsRead(messageId: String) {
        remoteDataSource.markMessageAsRead(messageId)
    }
}