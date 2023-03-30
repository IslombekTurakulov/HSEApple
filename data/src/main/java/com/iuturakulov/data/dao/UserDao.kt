package com.iuturakulov.data.dao

import androidx.room.*
import com.iuturakulov.domain.entities.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE id = :userId")
    suspend fun getUser(userId: String): UserEntity?

    @Query("SELECT * FROM user")
    suspend fun getUsers(): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(user: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUsers(users: List<UserEntity>)

    @Delete
    suspend fun deleteUser(user: UserEntity)

    @Query("DELETE FROM user")
    suspend fun deleteUsers()
}