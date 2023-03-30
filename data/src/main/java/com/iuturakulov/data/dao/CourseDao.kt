package com.iuturakulov.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.iuturakulov.domain.entities.CourseEntity

@Dao
interface CourseDao {

    @Query("SELECT * FROM courseentity")
    suspend fun getAllCourses(): List<CourseEntity>

    @Query("SELECT * FROM courseentity WHERE id = :courseId")
    suspend fun getCourse(courseId: String): CourseEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourse(course: CourseEntity)

    @Update
    suspend fun updateCourse(course: CourseEntity)

    @Query("DELETE FROM courses WHERE id = :courseId")
    suspend fun deleteCourse(courseId: String)

    @Query("DELETE FROM courseentity")
    suspend fun deleteAllCourses()
}