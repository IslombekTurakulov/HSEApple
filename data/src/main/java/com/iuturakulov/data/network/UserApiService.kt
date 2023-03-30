package com.iuturakulov.data.network

import com.iuturakulov.data.models.user.UserStateResponse
import com.iuturakulov.data.models.user.UserListResponse
import com.iuturakulov.data.models.user.UserResponse
import com.iuturakulov.domain.entities.UserEntity
import retrofit2.Response
import retrofit2.http.*

interface UserApiService {

    @GET("users/{userId}")
    suspend fun getUser(@Path("userId") userId: String): Response<UserResponse>

    @GET("users")
    suspend fun getUsers(): Response<UserListResponse>
    
    @POST("users")
    suspend fun createUser(@Body user: UserEntity): Response<UserStateResponse>

    @PUT("users/{userId}")
    suspend fun updateUser(@Path("userId") userId: String, @Body user: UserEntity): Response<UserStateResponse>

    @DELETE("users/{userId}")
    suspend fun deleteUser(@Path("userId") userId: String): Response<UserStateResponse>
}