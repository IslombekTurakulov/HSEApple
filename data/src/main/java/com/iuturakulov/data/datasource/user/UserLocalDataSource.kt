package com.iuturakulov.data.datasource.user

import com.iuturakulov.data.dao.UserDao
import com.iuturakulov.domain.entities.UserEntity

interface UserLocalDataSource {
    suspend fun getUser(userId: String): UserEntity?
    suspend fun getUsers(): List<UserEntity>
    suspend fun saveUser(user: UserEntity)
    suspend fun saveUsers(users: List<UserEntity>)
    suspend fun deleteUser(user: UserEntity)
    suspend fun deleteUsers()
}

class UserLocalDataSourceImpl(private val userDao: UserDao) : UserLocalDataSource {

    override suspend fun getUser(userId: String): UserEntity? {
        return userDao.getUser(userId)
    }

    override suspend fun getUsers(): List<UserEntity> {
        return userDao.getUsers()
    }

    override suspend fun saveUser(user: UserEntity) {
        userDao.saveUser(user)
    }

    override suspend fun saveUsers(users: List<UserEntity>) {
        userDao.saveUsers(users)
    }

    override suspend fun deleteUser(user: UserEntity) {
        userDao.deleteUser(user)
    }

    override suspend fun deleteUsers() {
        userDao.deleteUsers()
    }
}
