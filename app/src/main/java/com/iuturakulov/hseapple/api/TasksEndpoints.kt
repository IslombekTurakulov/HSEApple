package com.iuturakulov.hseapple.api

import com.iuturakulov.hseapple.model.TaskEntity
import retrofit2.http.GET
import retrofit2.http.Header

interface TasksEndpoints {
    @GET("course/1/task?start=1")
    suspend fun getSecondCourseTasks(@Header("x-api-key") key: String): List<TaskEntity>

    @GET("course/2/task?start=1")
    suspend fun getThirdCourseTasks(@Header("x-api-key") key: String): List<TaskEntity>
}
