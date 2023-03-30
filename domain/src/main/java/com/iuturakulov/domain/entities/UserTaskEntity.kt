package com.iuturakulov.domain.entities

data class UserTaskEntity(
    val user: UserEntity,
    val task: TaskEntity
)