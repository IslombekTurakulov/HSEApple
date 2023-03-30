package com.iuturakulov.domain.repositories

import com.iuturakulov.domain.entities.UserEntity

interface UserRepository {
    suspend fun getUser(userId: String): UserEntity
    suspend fun getUsers(): List<UserEntity>
}
