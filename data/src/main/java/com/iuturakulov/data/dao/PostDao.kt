package com.iuturakulov.data.dao

import androidx.room.*
import com.iuturakulov.domain.entities.PostEntity

@Dao
interface PostDao {
    @Query("SELECT * FROM post WHERE id = :postId")
    suspend fun getPost(postId: String): PostEntity

    @Query("SELECT * FROM post")
    suspend fun getPosts(): List<PostEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: PostEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<PostEntity>)

    @Delete
    suspend fun deletePost(post: PostEntity)

    @Query("DELETE FROM post")
    suspend fun deleteAllPosts()
}