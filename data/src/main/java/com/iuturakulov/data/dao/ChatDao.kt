package com.iuturakulov.data.dao

import androidx.room.*
import com.iuturakulov.domain.entities.ChatEntity

@Dao
interface ChatDao {

    @Query("SELECT * FROM chatentity WHERE id = :chatId")
    suspend fun getChat(chatId: String): ChatEntity

    @Query("SELECT * FROM chatentity")
    suspend fun getChats(): List<ChatEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChat(chat: ChatEntity)

    @Update
    suspend fun updateChat(chat: ChatEntity)

    @Query("DELETE FROM chatentity WHERE id = :chatId")
    suspend fun deleteChat(chatId: String)
}