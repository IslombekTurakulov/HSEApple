package com.iuturakulov.data.repository

import com.iuturakulov.data.datasource.userTask.UserTaskLocalDataSource
import com.iuturakulov.data.datasource.userTask.UserTaskRemoteDataSource
import com.iuturakulov.domain.entities.UserTaskEntity
import com.iuturakulov.domain.repositories.UserTaskRepository
import retrofit2.Response

class UserTaskRepositoryImpl(
    private val localDataSource: UserTaskLocalDataSource,
    private val remoteDataSource: UserTaskRemoteDataSource
) : UserTaskRepository {

    override suspend fun getUserTask(userId: String, taskId: String): UserTaskEntity {
        return localDataSource.getUserTask(userId, taskId) ?: remoteDataSource.getUserTask(userId, taskId).also { userTaskEntity ->
            localDataSource.saveUserTask(userTaskEntity)
        }
    }

    override suspend fun getUserTasks(userId: String): List<UserTaskEntity> {
        val remoteUserTasks = remoteDataSource.getUserTasks(userId)
        localDataSource.saveUserTasks(remoteUserTasks)
        return localDataSource.getUserTasks(userId)
    }

    override suspend fun createUserTask(userTask: UserTaskEntity): Boolean {
        return remoteDataSource.createUserTask(userTask)
    }

    override suspend fun deleteUserTask(userId: String, taskId: String): Boolean {
        return remoteDataSource.deleteUserTask(userId, taskId)
    }
}
