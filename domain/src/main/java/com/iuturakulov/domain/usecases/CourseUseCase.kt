package com.iuturakulov.domain.usecases

import com.iuturakulov.domain.entities.CourseEntity
import com.iuturakulov.domain.repositories.CourseRepository

class CourseUseCase(private val courseRepository: CourseRepository) {
    suspend fun getCourse(courseId: String): CourseEntity {
        return courseRepository.getCourse(courseId)
    }

    suspend fun getCourses(): List<CourseEntity> {
        return courseRepository.getCourses()
    }

    suspend fun createCourse(course: CourseEntity): Boolean {
        return courseRepository.createCourse(course)
    }

    suspend fun updateCourse(course: CourseEntity): Boolean {
        return courseRepository.updateCourse(course)
    }

    suspend fun deleteCourse(courseId: String): Boolean {
        return courseRepository.deleteCourse(courseId)
    }
}