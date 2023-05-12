package com.iuturakulov.domain.usecases

import com.iuturakulov.domain.entities.ChatEntity
import com.iuturakulov.domain.entities.Message
import com.iuturakulov.domain.entities.UserEntity
import com.iuturakulov.domain.repositories.ChatRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ChatUseCaseTest {

    @Mock
    lateinit var chatRepository: ChatRepository

    private lateinit var chatUseCase: ChatUseCase

    private val testUser = UserEntity(
        id = "1",
        name = "John Doe",
        email = "john.doe@example.com",
        profileImageUrl = "https://example.com/john_doe.jpg",
        university = "XYZ University",
        department = "Computer Science"
    )

    private val testMessage = Message(
        id = "1",
        chatId = "chat_id",
        senderId = "sender_id",
        receiverId = "receiver_id",
        content = "Hello, World!",
        timestamp = System.currentTimeMillis()
    )

    @Test
    fun `getChat should call corresponding method in repository`() = runBlocking {
        chatUseCase = ChatUseCase(chatRepository)
        val chatId = "chat_id"
        chatUseCase.getChat(chatId)
        verify(chatRepository).getChat(chatId)
    }

    @Test
    fun `getChats should call corresponding method in repository`() = runBlocking {
        chatUseCase = ChatUseCase(chatRepository)
        chatUseCase.getChats()
        verify(chatRepository).getChats()
    }

    @Test
    fun `createChat should call corresponding method in repository`() = runBlocking {
        chatUseCase = ChatUseCase(chatRepository)
        val chat = ChatEntity("chat_id", listOf(testUser), listOf(testMessage))
        chatUseCase.createChat(chat)
        verify(chatRepository).createChat(chat)
    }

    @Test
    fun `updateChat should call corresponding method in repository`() = runBlocking {
        chatUseCase = ChatUseCase(chatRepository)
        val chat = ChatEntity("chat_id", listOf(testUser), listOf(testMessage))
        chatUseCase.updateChat(chat)
        verify(chatRepository).updateChat(chat)
    }

    @Test
    fun `deleteChat should call corresponding method in repository`() = runBlocking {
        chatUseCase = ChatUseCase(chatRepository)
        val chatId = "chat_id"
        chatUseCase.deleteChat(chatId)
        verify(chatRepository).deleteChat(chatId)
    }

    @Test
    fun `getChatList should call corresponding method in repository`() = runBlocking {
        chatUseCase = ChatUseCase(chatRepository)
        val userId = "user_id"
        chatUseCase.getChatList(userId)
        verify(chatRepository).getChatList(userId)
    }

    @Test
    fun `sendMessage should call corresponding method in repository`() = runBlocking {
        chatUseCase = ChatUseCase(chatRepository)
        chatUseCase.sendMessage(testMessage)
        verify(chatRepository).sendMessage(testMessage)
    }

    @Test
    fun `getMessages should call corresponding method in repository`() = runBlocking {
        chatUseCase = ChatUseCase(chatRepository)
        val chatId = "chat_id"
        chatUseCase.getMessages(chatId)
        verify(chatRepository).getMessages(chatId)
    }

    @Test
    fun `markMessageAsRead should call corresponding method in repository`() = runBlocking {
        chatUseCase = ChatUseCase(chatRepository)
        val messageId = "message_id"
        chatUseCase.markMessageAsRead(messageId)
        verify(chatRepository).markMessageAsRead(messageId)
    }
}