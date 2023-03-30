package com.iuturakulov.domain.repositories

import com.iuturakulov.domain.entities.RequestEntity

interface RequestRepository {
    suspend fun getRequest(requestId: String): RequestEntity
    suspend fun getRequests(): List<RequestEntity>
    suspend fun createRequest(request: RequestEntity): Boolean
    suspend fun updateRequest(request: RequestEntity): Boolean
    suspend fun deleteRequest(requestId: String): Boolean
}
