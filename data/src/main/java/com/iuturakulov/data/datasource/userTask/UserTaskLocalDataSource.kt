package com.iuturakulov.data.datasource.userTask

import com.iuturakulov.data.dao.UserTaskDao
import com.iuturakulov.domain.entities.UserTaskEntity

interface UserTaskLocalDataSource {
    suspend fun getUserTask(userId: String, taskId: String): UserTaskEntity?
    suspend fun getUserTasks(userId: String): List<UserTaskEntity>
    suspend fun saveUserTask(userTask: UserTaskEntity)
    suspend fun saveUserTasks(userTasks: List<UserTaskEntity>)
    suspend fun deleteUserTask(userTask: UserTaskEntity)
    suspend fun deleteUserTasks(userId: String)
}

class UserTaskLocalDataSourceImpl(private val userTaskDao: UserTaskDao) : UserTaskLocalDataSource {
    override suspend fun getUserTask(userId: String, taskId: String): UserTaskEntity? {
        return userTaskDao.getUserTask(userId, taskId)
    }

    override suspend fun getUserTasks(userId: String): List<UserTaskEntity> {
        return userTaskDao.getUserTasks(userId)
    }

    override suspend fun saveUserTask(userTask: UserTaskEntity) {
        userTaskDao.insert(userTask)
    }

    override suspend fun saveUserTasks(userTasks: List<UserTaskEntity>) {
        userTaskDao.insert(userTasks)
    }

    override suspend fun deleteUserTask(userTask: UserTaskEntity) {
        userTaskDao.delete(userTask)
    }

    override suspend fun deleteUserTasks(userId: String) {
        userTaskDao.deleteUserTasks(userId)
    }
}
