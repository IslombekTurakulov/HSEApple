package com.iuturakulov.hseapple.model

import java.time.LocalDateTime

class UserTaskEntity(
    val id: Long? = null,
    val answer: String? = null,
    val score: Int? = null,
    val status: Boolean? = null,
    val taskID: Long? = null,
    val createdBy: Long? = null,
    val updatedBy: Long? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
)