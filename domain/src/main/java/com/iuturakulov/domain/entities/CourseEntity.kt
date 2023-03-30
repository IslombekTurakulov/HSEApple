package com.iuturakulov.domain.entities

import java.util.*

data class CourseEntity(
    val id: String,
    val title: String,
    val description: String,
    val instructor: UserEntity,
    val startDate: Date,
    val endDate: Date
)