package com.iuturakulov.data.dao

import androidx.room.*
import com.iuturakulov.domain.entities.TaskEntity

@Dao
interface TaskDao {
    @Query("SELECT * FROM task WHERE id = :taskId")
    suspend fun getTask(taskId: String): TaskEntity?

    @Query("SELECT * FROM task")
    suspend fun getTasks(): List<TaskEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTask(task: TaskEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTasks(tasks: List<TaskEntity>)

    @Delete
    suspend fun deleteTask(task: TaskEntity)

    @Query("DELETE FROM task")
    suspend fun deleteAllTasks()
}
