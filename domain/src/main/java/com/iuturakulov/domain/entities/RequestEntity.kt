package com.iuturakulov.domain.entities

data class RequestEntity(
    val id: String,
    val fromUser: UserEntity,
    val toUser: UserEntity,
    val message: String,
    val status: RequestStatus
)