package com.iuturakulov.hseapple.model

data class PostEntity(
    var id: Long? = null,
    var courseid: Long? = null,
    var createdBy: Long? = null,
    var updatedBy: Long? = null,
    var title: String? = null,
    var content: String? = null,
    var mediaLink: String? = null,
    var docLink: String? = null,
    var createdAt: String? = null,
    var updatedAt: String? = null
)