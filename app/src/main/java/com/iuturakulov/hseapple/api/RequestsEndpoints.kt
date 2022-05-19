package com.iuturakulov.hseapple.api

import com.iuturakulov.hseapple.model.RequestEntity
import retrofit2.http.GET
import retrofit2.http.Header

interface RequestsEndpoints {
    @GET("course/1/application/list?approved=false")
    suspend fun getRequestsForSecondCourseList(@Header("x-api-key") key: String): List<RequestEntity>

    @GET("course/2/application/list?approved=false")
    suspend fun getRequestsForThirdCourseList(@Header("x-api-key") key: String): List<RequestEntity>
}
