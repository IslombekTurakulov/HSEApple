package com.iuturakulov.data.network

import com.iuturakulov.data.models.request.RequestResponse
import com.iuturakulov.data.models.request.RequestsResponse
import com.iuturakulov.data.models.SuccessResponse
import com.iuturakulov.domain.entities.RequestEntity
import retrofit2.Response
import retrofit2.http.*

interface RequestApiService {
    @GET("/requests/{requestId}")
    suspend fun getRequest(@Path("requestId") requestId: String): Response<RequestResponse>

    @GET("/requests")
    suspend fun getRequests(): Response<RequestsResponse>

    @POST("/requests")
    suspend fun createRequest(@Body request: RequestEntity): Response<SuccessResponse>

    @PUT("/requests/{requestId}")
    suspend fun updateRequest(@Path("requestId") requestId: String, @Body request: RequestEntity): Response<SuccessResponse>

    @DELETE("/requests/{requestId}")
    suspend fun deleteRequest(@Path("requestId") requestId: String): Response<SuccessResponse>
}
