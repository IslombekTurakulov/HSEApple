package com.iuturakulov.domain.entities

import java.util.*

data class TaskEntity(
    val id: String,
    val title: String,
    val description: String,
    val dueDate: Date,
    val status: TaskStatus,
    val assignees: List<UserEntity>
)