package com.iuturakulov.data.datasource.userTask

import com.iuturakulov.data.network.UserTaskApiService
import com.iuturakulov.domain.entities.UserTaskEntity
import retrofit2.Response

interface UserTaskRemoteDataSource {
    suspend fun getUserTask(userId: String, taskId: String): UserTaskEntity
    suspend fun getUserTasks(userId: String): List<UserTaskEntity>
    suspend fun createUserTask(userTask: UserTaskEntity): Boolean
    suspend fun deleteUserTask(userId: String, taskId: String): Boolean
}

class UserTaskRemoteDataSourceImpl(private val apiService: UserTaskApiService) : UserTaskRemoteDataSource {
    override suspend fun getUserTask(userId: String, taskId: String): UserTaskEntity {
        val response = apiService.getUserTask(userId, taskId)
        return handleResponse(response) { it.userTask }
    }

    override suspend fun getUserTasks(userId: String): List<UserTaskEntity> {
        val response = apiService.getUserTasks(userId)
        return handleResponse(response) { it.userTasks }
    }

    override suspend fun createUserTask(userTask: UserTaskEntity): Boolean {
        val response = apiService.createUserTask(userTask.user.id, userTask)
        return handleResponse(response) { it.success }
    }

    override suspend fun deleteUserTask(userId: String, taskId: String): Boolean {
        val response = apiService.deleteUserTask(userId, taskId)
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