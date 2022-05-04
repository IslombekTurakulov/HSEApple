package com.iuturakulov.hseapple.model

import java.sql.Timestamp

class RequestEntity(
    val id: Long? = null,
    val userID: Long? = null,
    val courseID: Long? = null,
    val roleID: Long? = null,
    val approved: Boolean? = null,
    val createdAt: Timestamp? = null,
    val updatedAt: Timestamp? = null
)