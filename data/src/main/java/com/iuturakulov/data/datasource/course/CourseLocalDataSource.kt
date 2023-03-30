package com.iuturakulov.data.datasource.course

import com.iuturakulov.data.dao.CourseDao
import com.iuturakulov.domain.entities.CourseEntity

interface CourseLocalDataSource {
    suspend fun getCourse(courseId: String): CourseEntity?
    suspend fun getCourses(): List<CourseEntity>
    suspend fun saveCourse(course: CourseEntity)
    suspend fun updateCourse(course: CourseEntity)
    suspend fun deleteCourse(courseId: String)
}

class CourseLocalDataSourceImpl(private val courseDao: CourseDao) : CourseLocalDataSource {

    override suspend fun getCourse(courseId: String): CourseEntity? {
        return courseDao.getCourse(courseId)
    }

    override suspend fun getCourses(): List<CourseEntity> {
        return courseDao.getAllCourses()
    }

    override suspend fun saveCourse(course: CourseEntity) {
        courseDao.insertCourse(course)
    }

    override suspend fun updateCourse(course: CourseEntity) {
        courseDao.updateCourse(course)
    }

    override suspend fun deleteCourse(courseId: String) {
        courseDao.deleteCourse(courseId)
    }
}