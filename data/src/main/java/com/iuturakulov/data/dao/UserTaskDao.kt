package com.iuturakulov.data.dao

import androidx.room.*
import com.iuturakulov.domain.entities.UserTaskEntity

@Dao
interface UserTaskDao {

    @Query("SELECT * FROM user_tasks WHERE user_id = :userId AND task_id = :taskId")
    suspend fun getUserTask(userId: String, taskId: String): UserTaskEntity?

    @Query("SELECT * FROM user_tasks WHERE user_id = :userId")
    suspend fun getUserTasks(userId: String): List<UserTaskEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userTask: UserTaskEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userTasks: List<UserTaskEntity>)

    @Delete
    suspend fun delete(userTask: UserTaskEntity)

    @Query("DELETE FROM user_tasks WHERE user_id = :userId")
    suspend fun deleteUserTasks(userId: String)
}
