package com.iuturakulov.data.network

import com.iuturakulov.data.models.post.PostResponse
import com.iuturakulov.data.models.post.PostsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PostApiService : ApiService {
    @GET("posts/{postId}")
    fun getPost(@Path("postId") postId: String): Response<PostResponse>

    @GET("posts")
    fun getPosts(): Response<PostsResponse>
}