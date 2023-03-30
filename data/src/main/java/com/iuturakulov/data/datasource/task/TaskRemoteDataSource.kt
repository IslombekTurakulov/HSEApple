package com.iuturakulov.data.datasource.task

import com.iuturakulov.data.network.TaskApiService
import com.iuturakulov.domain.entities.TaskEntity
import retrofit2.Response

interface TaskRemoteDataSource {
    suspend fun getTask(taskId: String): TaskEntity
    suspend fun getTasks(): List<TaskEntity>
    suspend fun createTask(task: TaskEntity): Boolean
    suspend fun updateTask(task: TaskEntity): Boolean
    suspend fun deleteTask(taskId: String): Boolean
}

class TaskRemoteDataSourceImpl(private val apiService: TaskApiService) : TaskRemoteDataSource {

    override suspend fun getTask(taskId: String): TaskEntity {
        val response = apiService.getTask(taskId)
        return handleResponse(response) { it.task }
    }

    override suspend fun getTasks(): List<TaskEntity> {
        val response = apiService.getTasks()
        return handleResponse(response) { it.tasks }
    }

    override suspend fun createTask(task: TaskEntity): Boolean {
        val response = apiService.createTask(task)
        return handleResponse(response) { it.success }
    }

    override suspend fun updateTask(task: TaskEntity): Boolean {
        val response = apiService.updateTask(task.id, task)
        return handleResponse(response) { it.success }
    }

    override suspend fun deleteTask(taskId: String): Boolean {
        val response = apiService.deleteTask(taskId)
        return handleResponse(response) { it.success }
    }

    private inline fun <T, R> handleResponse(response: Response<T>, block: (T) -> R): R {
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return block(body)
            }
        }
        throw ApiException(response.message())
    }
}

class ApiException(message: String) : Exception(message)
