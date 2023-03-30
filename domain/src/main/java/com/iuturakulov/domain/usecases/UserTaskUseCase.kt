package com.iuturakulov.domain.usecases

import com.iuturakulov.domain.entities.UserTaskEntity
import com.iuturakulov.domain.repositories.UserTaskRepository

class UserTaskUseCase(private val userTaskRepository: UserTaskRepository) {

    suspend fun getUserTask(userId: String, taskId: String): UserTaskEntity {
        return userTaskRepository.getUserTask(userId, taskId)
    }

    suspend fun getUserTasks(userId: String): List<UserTaskEntity> {
        return userTaskRepository.getUserTasks(userId)
    }
    suspend fun createUserTask(userTask: UserTaskEntity): Boolean {
        return userTaskRepository.createUserTask(userTask)
    }

    suspend fun deleteUserTask(userId: String, taskId: String): Boolean {
        return userTaskRepository.deleteUserTask(userId, taskId)
    }
}