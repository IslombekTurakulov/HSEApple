package com.iuturakulov.domain.repositories

import com.iuturakulov.domain.entities.TaskEntity

interface TaskRepository {
    suspend fun getTask(taskId: String): TaskEntity
    suspend fun getTasks(): List<TaskEntity>
    suspend fun createTask(task: TaskEntity): Boolean
    suspend fun updateTask(task: TaskEntity): Boolean
    suspend fun deleteTask(taskId: String): Boolean
}
