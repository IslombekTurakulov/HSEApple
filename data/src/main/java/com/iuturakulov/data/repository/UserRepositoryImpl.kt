package com.iuturakulov.data.repository

import com.iuturakulov.data.datasource.user.UserLocalDataSource
import com.iuturakulov.data.datasource.user.UserRemoteDataSource
import com.iuturakulov.domain.entities.UserEntity
import com.iuturakulov.domain.repositories.UserRepository

class UserRepositoryImpl(
    private val localDataSource: UserLocalDataSource,
    private val remoteDataSource: UserRemoteDataSource
) : UserRepository {

    override suspend fun getUser(userId: String): UserEntity {
        return localDataSource.getUser(userId) ?: remoteDataSource.getUser(userId).also { userEntity ->
            localDataSource.saveUser(userEntity)
        }
    }

    override suspend fun getUsers(): List<UserEntity> {
        val remoteUsers = remoteDataSource.getUsers()
        localDataSource.saveUsers(remoteUsers)
        return localDataSource.getUsers()
    }
}
