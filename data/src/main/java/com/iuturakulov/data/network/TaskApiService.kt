package com.iuturakulov.data.network

import com.iuturakulov.data.models.SuccessResponse
import com.iuturakulov.data.models.task.TaskResponse
import com.iuturakulov.data.models.task.TasksResponse
import com.iuturakulov.domain.entities.TaskEntity
import retrofit2.Response
import retrofit2.http.*

interface TaskApiService {
    @GET("tasks/{taskId}")
    suspend fun getTask(@Path("taskId") taskId: String): Response<TaskResponse>

    @GET("tasks")
    suspend fun getTasks(): Response<TasksResponse>

    @POST("tasks")
    suspend fun createTask(@Body task: TaskEntity): Response<SuccessResponse>

    @PUT("tasks/{taskId}")
    suspend fun updateTask(@Path("taskId") taskId: String, @Body task: TaskEntity): Response<SuccessResponse>

    @DELETE("tasks/{taskId}")
    suspend fun deleteTask(@Path("taskId") taskId: String): Response<SuccessResponse>
}
