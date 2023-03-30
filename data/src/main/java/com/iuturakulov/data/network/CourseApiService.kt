package com.iuturakulov.data.network

import com.iuturakulov.data.models.course.CourseResponse
import com.iuturakulov.data.models.course.CoursesResponse
import com.iuturakulov.data.models.OperationResultResponse
import com.iuturakulov.domain.entities.CourseEntity
import retrofit2.Response
import retrofit2.http.*

interface CourseApiService {

    @GET("courses/{courseId}")
    suspend fun getCourse(@Path("courseId") courseId: String): Response<CourseResponse>

    @GET("courses")
    suspend fun getCourses(): Response<CoursesResponse>

    @POST("courses")
    suspend fun createCourse(@Body course: CourseEntity): Response<OperationResultResponse>

    @PUT("courses")
    suspend fun updateCourse(@Body course: CourseEntity): Response<OperationResultResponse>

    @DELETE("courses/{courseId}")
    suspend fun deleteCourse(@Path("courseId") courseId: String): Response<OperationResultResponse>
}