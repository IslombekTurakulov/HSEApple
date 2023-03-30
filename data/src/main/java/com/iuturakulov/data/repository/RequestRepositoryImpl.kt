package com.iuturakulov.data.repository

import com.iuturakulov.data.datasource.request.RequestLocalDataSource
import com.iuturakulov.data.datasource.request.RequestRemoteDataSource
import com.iuturakulov.domain.entities.RequestEntity
import com.iuturakulov.domain.repositories.RequestRepository

class RequestRepositoryImpl(
    private val localDataSource: RequestLocalDataSource,
    private val remoteDataSource: RequestRemoteDataSource
) : RequestRepository {

    override suspend fun getRequest(requestId: String): RequestEntity {
        return localDataSource.getRequest(requestId) ?: remoteDataSource.getRequest(requestId).also { requestEntity ->
            localDataSource.saveRequest(requestEntity)
        }
    }

    override suspend fun getRequests(): List<RequestEntity> {
        val remoteRequests = remoteDataSource.getRequests()
        localDataSource.saveRequests(remoteRequests)
        return localDataSource.getRequests()
    }

    override suspend fun createRequest(request: RequestEntity): Boolean {
        val created = remoteDataSource.createRequest(request)
        if (created) {
            localDataSource.saveRequest(request)
        }
        return created
    }

    override suspend fun updateRequest(request: RequestEntity): Boolean {
        val updated = remoteDataSource.updateRequest(request)
        if (updated) {
            localDataSource.saveRequest(request)
        }
        return updated
    }

    override suspend fun deleteRequest(requestId: String): Boolean {
        val deleted = remoteDataSource.deleteRequest(requestId)
        if (deleted) {
            localDataSource.deleteRequest(requestId)
        }
        return deleted
    }
}