package com.iuturakulov.data.repository

import com.iuturakulov.data.datasource.course.CourseLocalDataSource
import com.iuturakulov.data.datasource.course.CourseRemoteDataSource
import com.iuturakulov.domain.entities.CourseEntity
import com.iuturakulov.domain.repositories.CourseRepository

class CourseRepositoryImpl(
    private val localDataSource: CourseLocalDataSource,
    private val remoteDataSource: CourseRemoteDataSource
) : CourseRepository {

    override suspend fun getCourse(courseId: String): CourseEntity {
        val localCourse = localDataSource.getCourse(courseId)
        return if (localCourse != null) {
            localCourse
        } else {
            val remoteCourse = remoteDataSource.getCourse(courseId)
            localDataSource.saveCourse(remoteCourse)
            remoteCourse
        }
    }

    override suspend fun getCourses(): List<CourseEntity> {
        val remoteCourses = remoteDataSource.getCourses()
        remoteCourses.forEach { localDataSource.saveCourse(it) }
        return localDataSource.getCourses()
    }

    override suspend fun createCourse(course: CourseEntity): Boolean {
        val isSuccessful = remoteDataSource.createCourse(course)
        if (isSuccessful) {
            localDataSource.saveCourse(course)
        }
        return isSuccessful
    }

    override suspend fun updateCourse(course: CourseEntity): Boolean {
        val isSuccessful = remoteDataSource.updateCourse(course)
        if (isSuccessful) {
            localDataSource.saveCourse(course)
        }
        return isSuccessful
    }

    override suspend fun deleteCourse(courseId: String): Boolean {
        val isSuccessful = remoteDataSource.deleteCourse(courseId)
        if (isSuccessful) {
            localDataSource.deleteCourse(courseId)
        }
        return isSuccessful
    }
}
