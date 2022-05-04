package com.iuturakulov.hseapple.model

import java.time.LocalDateTime

class TaskEntity (
    var id: Long? = null,
    var form: String? = null,
    var title: String? = null,
    var description: String? = null,
    var courseID: Long? = null,
    var createdBy: Long? = null,
    var updatedBy: Long? = null,
    var task_content: String? = null,
    var deadline: LocalDateTime? = null,
    var isStatus: Boolean = false,
    var createdAt: LocalDateTime? = null,
    var updatedAt: LocalDateTime? = null
)