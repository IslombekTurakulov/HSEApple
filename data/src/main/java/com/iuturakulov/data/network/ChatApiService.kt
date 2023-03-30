package com.iuturakulov.data.network

import com.iuturakulov.data.models.chat.ChatListResponse
import com.iuturakulov.data.models.chat.ChatResponse
import com.iuturakulov.data.models.chat.MessageListResponse
import com.iuturakulov.data.models.chat.MessageResponse
import com.iuturakulov.domain.entities.ChatEntity
import com.iuturakulov.domain.entities.Message
import retrofit2.Response
import retrofit2.http.*

interface ChatApiService {
    @POST("/createChat")
    suspend fun createChat(@Body chat: ChatEntity): Response<ChatResponse>

    @PUT("/updateChat")
    suspend fun updateChat(@Body chat: ChatEntity): Response<ChatResponse>

    @DELETE("/deleteChat/{chatId}")
    suspend fun deleteChat(@Path("chatId") chatId: String): Response<ChatResponse>

    @GET("/chats/{userId}")
    suspend fun getChatList(@Path("userId") userId: String): Response<ChatListResponse>

    @POST("/sendMessage")
    suspend fun sendMessage(@Body message: Message): Response<MessageResponse>

    @GET("/messages/{chatId}")
    suspend fun getMessages(@Path("chatId") chatId: String): Response<MessageListResponse>

    @PATCH("/messages/{messageId}")
    suspend fun markMessageAsRead(@Path("messageId") messageId: String): Response<MessageResponse>
}
