package com.iuturakulov.hseapple.model.api

import java.sql.Timestamp

data class UserEntity(
    val id: Long? = null,
    var firstname: String? = null,
    var lastname: String? = null,
    val fullName: String? = null,
    var email: String? = null,
    var createdAt: Timestamp? = null
)