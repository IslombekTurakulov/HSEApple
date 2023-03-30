package com.iuturakulov.domain.usecases

import com.iuturakulov.domain.entities.TaskEntity
import com.iuturakulov.domain.repositories.TaskRepository

class TaskUseCase(private val taskRepository: TaskRepository) {
    suspend fun getTask(taskId: String): TaskEntity = taskRepository.getTask(taskId)
    suspend fun getTasks(): List<TaskEntity> = taskRepository.getTasks()
    suspend fun createTask(task: TaskEntity): Boolean = taskRepository.createTask(task)
    suspend fun updateTask(task: TaskEntity): Boolean = taskRepository.updateTask(task)
    suspend fun deleteTask(taskId: String): Boolean = taskRepository.deleteTask(taskId)
}