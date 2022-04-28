package com.iuturakulov.hseapple.model.api

import java.sql.Timestamp

data class PostEntity(
    var id: Long? = null,
    var courseid: Long? = null,
    var createdBy: Long? = null,
    var updatedBy: Long? = null,
    var title: String? = null,
    var content: String? = null,
    var mediaLink: String? = null,
    var docLink: String? = null,
    var createdAt: Timestamp? = null,
    var updatedAt: Timestamp? = null
)