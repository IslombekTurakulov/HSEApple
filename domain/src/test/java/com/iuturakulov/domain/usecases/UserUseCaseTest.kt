package com.iuturakulov.domain.usecases

import com.iuturakulov.domain.entities.UserEntity
import com.iuturakulov.domain.repositories.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UserUseCaseTest {

    @Mock
    lateinit var userRepository: UserRepository

    private lateinit var userUseCase: UserUseCase

    private val testUser = UserEntity(
        id = "1",
        name = "John Doe",
        email = "john.doe@example.com",
        profileImageUrl = "https://example.com/john_doe.jpg",
        university = "XYZ University",
        department = "Computer Science"
    )

    @Test
    fun `getUser should call corresponding method in repository`() = runBlocking {
        userUseCase = UserUseCase(userRepository)
        val userId = "1"
        userUseCase.getUser(userId)
        verify(userRepository).getUser(userId)
    }

    @Test
    fun `getUsers should call corresponding method in repository`() = runBlocking {
        userUseCase = UserUseCase(userRepository)
        userUseCase.getUsers()
        verify(userRepository).getUsers()
    }
}
