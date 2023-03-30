package com.iuturakulov.data.models.user

import com.iuturakulov.domain.entities.UserEntity

data class UserListResponse(
    val users: List<UserEntity>
)
