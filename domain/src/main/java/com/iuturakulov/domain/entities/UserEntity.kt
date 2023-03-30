package com.iuturakulov.domain.entities

data class UserEntity(
    val id: String,
    val name: String,
    val email: String,
    val profileImageUrl: String?,
    val university: String,
    val department: String
)