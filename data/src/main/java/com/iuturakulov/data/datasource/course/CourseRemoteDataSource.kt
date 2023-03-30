package com.iuturakulov.data.datasource.course

import com.iuturakulov.data.network.CourseApiService
import com.iuturakulov.domain.entities.CourseEntity
import retrofit2.Response

interface CourseRemoteDataSource {
    suspend fun getCourse(courseId: String): CourseEntity
    suspend fun getCourses(): List<CourseEntity>
    suspend fun createCourse(course: CourseEntity): Boolean
    suspend fun updateCourse(course: CourseEntity): Boolean
    suspend fun deleteCourse(courseId: String): Boolean
}

class CourseRemoteDataSourceImpl(private val apiService: CourseApiService) :
    CourseRemoteDataSource {

    override suspend fun getCourse(courseId: String): CourseEntity {
        val response = apiService.getCourse(courseId)
        return handleResponse(response) { it.course }
    }

    override suspend fun getCourses(): List<CourseEntity> {
        val response = apiService.getCourses()
        return handleResponse(response) { it.courses }
    }

    override suspend fun createCourse(course: CourseEntity): Boolean {
        val response = apiService.createCourse(course)
        return handleResponse(response) { it.success }
    }

    override suspend fun updateCourse(course: CourseEntity): Boolean {
        val response = apiService.updateCourse(course)
        return handleResponse(response) { it.success }
    }

    override suspend fun deleteCourse(courseId: String): Boolean {
        val response = apiService.deleteCourse(courseId)
        return handleResponse(response) { it.success }
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