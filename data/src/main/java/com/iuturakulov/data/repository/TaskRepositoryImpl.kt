package com.iuturakulov.data.repository

import com.iuturakulov.data.datasource.task.TaskLocalDataSource
import com.iuturakulov.data.datasource.task.TaskRemoteDataSource
import com.iuturakulov.domain.entities.TaskEntity
import com.iuturakulov.domain.repositories.TaskRepository

class TaskRepositoryImpl(
    private val localDataSource: TaskLocalDataSource,
    private val remoteDataSource: TaskRemoteDataSource
) : TaskRepository {

    override suspend fun getTask(taskId: String): TaskEntity {
        return localDataSource.getTask(taskId) ?: remoteDataSource.getTask(taskId).also { taskEntity ->
            localDataSource.saveTask(taskEntity)
        }
    }

    override suspend fun getTasks(): List<TaskEntity> {
        val remoteTasks = remoteDataSource.getTasks()
        localDataSource.saveTasks(remoteTasks)
        return localDataSource.getTasks()
    }

    override suspend fun createTask(task: TaskEntity): Boolean {
        return remoteDataSource.createTask(task)
    }

    override suspend fun updateTask(task: TaskEntity): Boolean {
        return remoteDataSource.updateTask(task)
    }

    override suspend fun deleteTask(taskId: String): Boolean {
        return remoteDataSource.deleteTask(taskId)
    }
}