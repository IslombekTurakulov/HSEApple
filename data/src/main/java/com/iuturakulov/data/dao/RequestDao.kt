package com.iuturakulov.data.dao

import androidx.room.*
import com.iuturakulov.domain.entities.RequestEntity

@Dao
interface RequestDao {
    @Query("SELECT * FROM RequestEntity WHERE id=:requestId")
    suspend fun getRequest(requestId: String): RequestEntity?

    @Query("SELECT * FROM RequestEntity")
    suspend fun getRequests(): List<RequestEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveRequest(request: RequestEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveRequests(requests: List<RequestEntity>)

    @Query("DELETE FROM RequestEntity WHERE id=:request")
    suspend fun deleteRequest(request: String)

    @Query("DELETE FROM RequestEntity")
    suspend fun deleteRequests()
}
