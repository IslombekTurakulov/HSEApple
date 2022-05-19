package com.iuturakulov.hseapple.api

import com.iuturakulov.hseapple.model.PostEntity
import retrofit2.http.GET
import retrofit2.http.Header

interface NewsEndpoints {
    @GET("course/1/post?start=1")
    suspend fun getNewsForSecondCourseList(@Header("x-api-key") key: String): List<PostEntity>

    @GET("course/1/post?start=1")
    suspend fun getNewsForThirdCourseList(@Header("x-api-key") key: String): List<PostEntity>
}
