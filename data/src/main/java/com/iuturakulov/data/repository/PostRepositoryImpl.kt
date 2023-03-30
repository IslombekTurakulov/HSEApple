package com.iuturakulov.data.repository

import com.iuturakulov.data.datasource.post.PostLocalDataSource
import com.iuturakulov.data.datasource.post.PostRemoteDataSource
import com.iuturakulov.domain.entities.PostEntity
import com.iuturakulov.domain.repositories.PostRepository

class PostRepositoryImpl(
    private val localDataSource: PostLocalDataSource,
    private val remoteDataSource: PostRemoteDataSource
) : PostRepository {

    override suspend fun getPost(postId: String): PostEntity {
        return localDataSource.getPost(postId) ?: remoteDataSource.getPost(postId).also { postEntity ->
            localDataSource.savePost(postEntity)
        }
    }

    override suspend fun getPosts(): List<PostEntity> {
        val remotePosts = remoteDataSource.getPosts()
        localDataSource.savePosts(remotePosts)
        return localDataSource.getPosts()
    }
}