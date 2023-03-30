package com.iuturakulov.domain.usecases

import com.iuturakulov.domain.entities.PostEntity
import com.iuturakulov.domain.repositories.PostRepository

class PostUseCase(private val postRepository: PostRepository) {
    suspend fun getPost(postId: String): PostEntity {
        return postRepository.getPost(postId)
    }

    suspend fun getPosts(): List<PostEntity> {
        return postRepository.getPosts()
    }
}