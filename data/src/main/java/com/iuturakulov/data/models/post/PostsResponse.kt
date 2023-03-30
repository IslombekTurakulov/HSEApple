package com.iuturakulov.data.models.post

import com.iuturakulov.domain.entities.PostEntity

data class PostsResponse(
    val posts: List<PostEntity>
)