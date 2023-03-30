package com.iuturakulov.domain.repositories

import com.iuturakulov.domain.entities.PostEntity

interface PostRepository {
    suspend fun getPost(postId: String): PostEntity
    suspend fun getPosts(): List<PostEntity>
}