package com.iuturakulov.data.datasource.request

import com.iuturakulov.data.dao.RequestDao
import com.iuturakulov.domain.entities.RequestEntity

interface RequestLocalDataSource {
    suspend fun getRequest(requestId: String): RequestEntity?
    suspend fun getRequests(): List<RequestEntity>
    suspend fun saveRequest(request: RequestEntity)
    suspend fun saveRequests(requests: List<RequestEntity>)
    suspend fun deleteRequest(request: String)
    suspend fun deleteRequests()
}

class RequestLocalDataSourceImpl(private val requestDao: RequestDao) : RequestLocalDataSource {

    override suspend fun getRequest(requestId: String): RequestEntity? {
        return requestDao.getRequest(requestId)
    }

    override suspend fun getRequests(): List<RequestEntity> {
        return requestDao.getRequests()
    }

    override suspend fun saveRequest(request: RequestEntity) {
        requestDao.saveRequest(request)
    }

    override suspend fun saveRequests(requests: List<RequestEntity>) {
        requestDao.saveRequests(requests)
    }

    override suspend fun deleteRequest(request: String) {
        requestDao.deleteRequest(request)
    }

    override suspend fun deleteRequests() {
        requestDao.deleteRequests()
    }
}
