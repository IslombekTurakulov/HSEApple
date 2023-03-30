package com.iuturakulov.data.network

import com.iuturakulov.data.models.SuccessResponse
import com.iuturakulov.data.models.userTask.UserTaskListResponse
import com.iuturakulov.data.models.userTask.UserTaskResponse
import com.iuturakulov.domain.entities.UserTaskEntity
import retrofit2.Response
import retrofit2.http.*

interface UserTaskApiService {
    @GET("users/{userId}/tasks/{taskId}")
    suspend fun getUserTask(@Path("userId") userId: String, @Path("taskId") taskId: String): Response<UserTaskResponse>

    @GET("users/{userId}/tasks")
    suspend fun getUserTasks(@Path("userId") userId: String): Response<UserTaskListResponse>

    @POST("users/{userId}/tasks")
    suspend fun createUserTask(@Path("userId") userId: String, @Body userTask: UserTaskEntity): Response<SuccessResponse>

    @DELETE("users/{userId}/tasks/{taskId}")
    suspend fun deleteUserTask(@Path("userId") userId: String, @Path("taskId") taskId: String): Response<SuccessResponse>
}
