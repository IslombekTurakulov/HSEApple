package com.iuturakulov.data.datasource.post

import com.iuturakulov.data.network.PostApiService
import com.iuturakulov.domain.entities.PostEntity
import retrofit2.Response

interface PostRemoteDataSource {
    suspend fun getPost(postId: String): PostEntity
    suspend fun getPosts(): List<PostEntity>
}

class PostRemoteDataSourceImpl(private val apiService: PostApiService) : PostRemoteDataSource {
    override suspend fun getPost(postId: String): PostEntity {
        val response = apiService.getPost(postId)
        return handleResponse(response) { it.post }
    }

    override suspend fun getPosts(): List<PostEntity> {
        val response = apiService.getPosts()
        return handleResponse(response) { it.posts }
    }

    private inline fun <T, R> handleResponse(response: Response<T>, block: (T) -> R): R {
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return block(body)
            }
        }
        throw ApiException(response.message())
    }
}

internal class ApiException(message: String) : Exception(message)
