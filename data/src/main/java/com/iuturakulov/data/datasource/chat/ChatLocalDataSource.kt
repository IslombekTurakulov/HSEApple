package com.iuturakulov.data.datasource.chat

import com.iuturakulov.data.dao.ChatDao
import com.iuturakulov.domain.entities.ChatEntity

interface ChatLocalDataSource {

    suspend fun getChat(chatId: String): ChatEntity

    suspend fun getChats(): List<ChatEntity>

    suspend fun saveChat(chat: ChatEntity)

    suspend fun updateChat(chat: ChatEntity)

    suspend fun deleteChat(chatId: String)
}

class ChatLocalDataSourceImpl(private val chatDao: ChatDao) : ChatLocalDataSource {

    override suspend fun getChat(chatId: String): ChatEntity {
        return chatDao.getChat(chatId)
    }

    override suspend fun getChats(): List<ChatEntity> {
        return chatDao.getChats()
    }

    override suspend fun saveChat(chat: ChatEntity) {
        chatDao.insertChat(chat)
    }

    override suspend fun updateChat(chat: ChatEntity) {
        chatDao.updateChat(chat)
    }

    override suspend fun deleteChat(chatId: String) {
        chatDao.deleteChat(chatId)
    }
}
