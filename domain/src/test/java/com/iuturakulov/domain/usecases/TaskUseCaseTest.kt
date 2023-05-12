package com.iuturakulov.domain.usecases

import com.iuturakulov.domain.entities.TaskEntity
import com.iuturakulov.domain.entities.UserEntity
import com.iuturakulov.domain.entities.TaskStatus
import com.iuturakulov.domain.repositories.TaskRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.util.Date

@RunWith(MockitoJUnitRunner::class)
class TaskUseCaseTest {

    @Mock
    lateinit var taskRepository: TaskRepository

    private lateinit var taskUseCase: TaskUseCase

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

    @Test
    fun `getTask should call corresponding method in repository`() = runBlocking {
        taskUseCase = TaskUseCase(taskRepository)
        val taskId = "1"
        taskUseCase.getTask(taskId)
        verify(taskRepository).getTask(taskId)
    }

    @Test
    fun `getTasks should call corresponding method in repository`() = runBlocking {
        taskUseCase = TaskUseCase(taskRepository)
        taskUseCase.getTasks()
        verify(taskRepository).getTasks()
    }

    @Test
    fun `createTask should call corresponding method in repository`() = runBlocking {
        taskUseCase = TaskUseCase(taskRepository)
        taskUseCase.createTask(testTask)
        verify(taskRepository).createTask(testTask)
    }

    @Test
    fun `updateTask should call corresponding method in repository`() = runBlocking {
        taskUseCase = TaskUseCase(taskRepository)
        taskUseCase.updateTask(testTask)
        verify(taskRepository).updateTask(testTask)
    }

    @Test
    fun `deleteTask should call corresponding method in repository`() = runBlocking {
        taskUseCase = TaskUseCase(taskRepository)
        val taskId = "1"
        taskUseCase.deleteTask(taskId)
        verify(taskRepository).deleteTask(taskId)
    }
}
