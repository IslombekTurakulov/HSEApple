package com.iuturakulov.domain.repositories

import com.iuturakulov.domain.entities.CourseEntity

interface CourseRepository {
    suspend fun getCourse(courseId: String): CourseEntity
    suspend fun getCourses(): List<CourseEntity>
    suspend fun createCourse(course: CourseEntity): Boolean
    suspend fun updateCourse(course: CourseEntity): Boolean
    suspend fun deleteCourse(courseId: String): Boolean
}
