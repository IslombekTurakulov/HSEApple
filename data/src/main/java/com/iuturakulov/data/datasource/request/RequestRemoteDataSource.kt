package com.iuturakulov.data.datasource.request

import com.iuturakulov.data.network.RequestApiService
import com.iuturakulov.domain.entities.RequestEntity
import retrofit2.Response

interface RequestRemoteDataSource {
    suspend fun getRequest(requestId: String): RequestEntity
    suspend fun getRequests(): List<RequestEntity>
    suspend fun createRequest(request: RequestEntity): Boolean
    suspend fun updateRequest(request: RequestEntity): Boolean
    suspend fun deleteRequest(requestId: String): Boolean
}

class RequestRemoteDataSourceImpl(private val apiService: RequestApiService) : RequestRemoteDataSource {

    override suspend fun getRequest(requestId: String): RequestEntity {
        val response = apiService.getRequest(requestId)
        return handleResponse(response) { it.request }
    }

    override suspend fun getRequests(): List<RequestEntity> {
        val response = apiService.getRequests()
        return handleResponse(response) { it.requests }
    }

    override suspend fun createRequest(request: RequestEntity): Boolean {
        val response = apiService.createRequest(request)
        return handleResponse(response) { it.success }
    }

    override suspend fun updateRequest(request: RequestEntity): Boolean {
        val response = apiService.updateRequest(request.id, request)
        return handleResponse(response) { it.success }
    }

    override suspend fun deleteRequest(requestId: String): Boolean {
        val response = apiService.deleteRequest(requestId)
        return handleResponse(response) { it.success }
    }

    private inline fun <T, R> handleResponse(response: Response<T>, block: (T) -> R): R {
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return block(body)
            }
        }
        throw ApiException(response.message())
    }
}

class ApiException(message: String) : Exception(message)
