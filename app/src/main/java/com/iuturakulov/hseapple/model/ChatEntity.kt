package com.iuturakulov.hseapple.model

import java.sql.Timestamp
import java.time.LocalDateTime

data class ChatEntity (
    val id: Long? = null,
    var title: String? = null,
    var description: String? = null,
    var courseID: Long? = null,
    var updatedBy: Long? = null,
    var group_avatar: String? = null,
    var createdAt: LocalDateTime? = null,
    var updatedAt: LocalDateTime? = null
)