package com.iuturakulov.domain.usecases

import com.iuturakulov.domain.entities.TaskEntity
import com.iuturakulov.domain.entities.UserEntity
import com.iuturakulov.domain.entities.TaskStatus
import com.iuturakulov.domain.entities.UserTaskEntity
import com.iuturakulov.domain.repositories.UserTaskRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.util.Date

@RunWith(MockitoJUnitRunner::class)
class UserTaskUseCaseTest {

    @Mock
    lateinit var userTaskRepository: UserTaskRepository

    private lateinit var userTaskUseCase: UserTaskUseCase

    private val testUser = UserEntity(
        id = "1",
        name = "John Doe",
        email = "john.doe@example.com",
        profileImageUrl = "https://example.com/john_doe.jpg",
        university = "XYZ University",
        department = "Computer Science"
    )

    private val testStatus = TaskStatus(
        id = 1,
        status = true
    )

    private val testTask = TaskEntity(
        id = "1",
        title = "Test Task",
        description = "This is a test task",
        dueDate = Date(),
        status = testStatus,
        assignees = listOf(testUser)
    )

    private val testUserTask = UserTaskEntity(
        user = testUser,
        task = testTask
    )

    @Test
    fun `getUserTask should call corresponding method in repository`() = runBlocking {
        userTaskUseCase = UserTaskUseCase(userTaskRepository)
        val userId = "1"
        val taskId = "1"
        userTaskUseCase.getUserTask(userId, taskId)
        verify(userTaskRepository).getUserTask(userId, taskId)
    }

    @Test
    fun `getUserTasks should call corresponding method in repository`() = runBlocking {
        userTaskUseCase = UserTaskUseCase(userTaskRepository)
        val userId = "1"
        userTaskUseCase.getUserTasks(userId)
        verify(userTaskRepository).getUserTasks(userId)
    }

    @Test
    fun `createUserTask should call corresponding method in repository`() = runBlocking {
        userTaskUseCase = UserTaskUseCase(userTaskRepository)
        userTaskUseCase.createUserTask(testUserTask)
        verify(userTaskRepository).createUserTask(testUserTask)
    }

    @Test
    fun `deleteUserTask should call corresponding method in repository`() = runBlocking {
        userTaskUseCase = UserTaskUseCase(userTaskRepository)
        val userId = "1"
        val taskId = "1"
        userTaskUseCase.deleteUserTask(userId, taskId)
        verify(userTaskRepository).deleteUserTask(userId, taskId)
    }
}
