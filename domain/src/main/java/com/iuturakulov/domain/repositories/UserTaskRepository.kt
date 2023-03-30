package com.iuturakulov.domain.repositories

import com.iuturakulov.domain.entities.UserTaskEntity

interface UserTaskRepository {
    suspend fun getUserTask(userId: String, taskId: String): UserTaskEntity
    suspend fun getUserTasks(userId: String): List<UserTaskEntity>
    suspend fun createUserTask(userTask: UserTaskEntity): Boolean
    suspend fun deleteUserTask(userId: String, taskId: String): Boolean
}
