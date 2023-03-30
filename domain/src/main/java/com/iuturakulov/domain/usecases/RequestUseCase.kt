package com.iuturakulov.domain.usecases

import com.iuturakulov.domain.entities.RequestEntity
import com.iuturakulov.domain.repositories.RequestRepository

class RequestUseCase(private val requestRepository: RequestRepository) {
    suspend fun getRequest(requestId: String): RequestEntity {
        return requestRepository.getRequest(requestId)
    }

    suspend fun getRequests(): List<RequestEntity> {
        return requestRepository.getRequests()
    }

    suspend fun createRequest(request: RequestEntity): Boolean {
        return requestRepository.createRequest(request)
    }

    suspend fun updateRequest(request: RequestEntity): Boolean {
        return requestRepository.updateRequest(request)
    }

    suspend fun deleteRequest(requestId: String): Boolean {
        return requestRepository.deleteRequest(requestId)
    }
}