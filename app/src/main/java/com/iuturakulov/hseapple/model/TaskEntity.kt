package com.iuturakulov.hseapple.model

class TaskEntity(
    var id: Long? = null,
    var courseID: Long? = null,
    var form: String? = null,
    var title: String? = null,
    var description: String? = null,
    var task_content: String? = null,
    var deadline: String? = null,
    var status: Boolean = false,
    var createdBy: Long? = null,
    var updatedBy: Long? = null,
    var createdAt: String? = null,
    var updatedAt: String? = null
)