package com.iuturakulov.domain.usecases

import com.iuturakulov.domain.entities.RequestEntity
import com.iuturakulov.domain.entities.UserEntity
import com.iuturakulov.domain.entities.RequestStatus
import com.iuturakulov.domain.repositories.RequestRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RequestUseCaseTest {

    @Mock
    lateinit var requestRepository: RequestRepository

    private lateinit var requestUseCase: RequestUseCase

    private val testUser = UserEntity(
        id = "1",
        name = "John Doe",
        email = "john.doe@example.com",
        profileImageUrl = "https://example.com/john_doe.jpg",
        university = "XYZ University",
        department = "Computer Science"
    )

    private val testStatus = RequestStatus(
        id = 1,
        status = true
    )

    private val testRequest = RequestEntity(
        id = "1",
        fromUser = testUser,
        toUser = testUser,
        message = "This is a test request",
        status = testStatus
    )

    @Test
    fun `getRequest should call corresponding method in repository`() = runBlocking {
        requestUseCase = RequestUseCase(requestRepository)
        val requestId = "1"
        requestUseCase.getRequest(requestId)
        verify(requestRepository).getRequest(requestId)
    }

    @Test
    fun `getRequests should call corresponding method in repository`() = runBlocking {
        requestUseCase = RequestUseCase(requestRepository)
        requestUseCase.getRequests()
        verify(requestRepository).getRequests()
    }

    @Test
    fun `createRequest should call corresponding method in repository`() = runBlocking {
        requestUseCase = RequestUseCase(requestRepository)
        requestUseCase.createRequest(testRequest)
        verify(requestRepository).createRequest(testRequest)
    }

    @Test
    fun `updateRequest should call corresponding method in repository`() = runBlocking {
        requestUseCase = RequestUseCase(requestRepository)
        requestUseCase.updateRequest(testRequest)
        verify(requestRepository).updateRequest(testRequest)
    }

    @Test
    fun `deleteRequest should call corresponding method in repository`() = runBlocking {
        requestUseCase = RequestUseCase(requestRepository)
        val requestId = "1"
        requestUseCase.deleteRequest(requestId)
        verify(requestRepository).deleteRequest(requestId)
    }
}
