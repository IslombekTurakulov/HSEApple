package com.iuturakulov.domain.usecases

import com.iuturakulov.domain.entities.UserEntity
import com.iuturakulov.domain.repositories.UserRepository

class UserUseCase(private val userRepository: UserRepository) {
    suspend fun getUser(userId: String): UserEntity {
        return userRepository.getUser(userId)
    }

    suspend fun getUsers(): List<UserEntity> {
        return userRepository.getUsers()
    }
}