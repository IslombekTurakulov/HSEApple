package com.iuturakulov.data.datasource.post

import com.iuturakulov.data.dao.PostDao
import com.iuturakulov.domain.entities.PostEntity

interface PostLocalDataSource {
    suspend fun getPost(postId: String): PostEntity?
    suspend fun getPosts(): List<PostEntity>
    suspend fun savePost(post: PostEntity)
    suspend fun savePosts(posts: List<PostEntity>)
}

class PostLocalDataSourceImpl(private val postDao: PostDao) : PostLocalDataSource {
    override suspend fun getPost(postId: String): PostEntity? {
        return postDao.getPost(postId)
    }

    override suspend fun getPosts(): List<PostEntity> {
        return postDao.getPosts()
    }

    override suspend fun savePost(post: PostEntity) {
        postDao.insertPost(post)
    }

    override suspend fun savePosts(posts: List<PostEntity>) {
        postDao.insertPosts(posts)
    }
}
