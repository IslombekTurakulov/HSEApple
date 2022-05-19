package com.iuturakulov.hseapple.api

import com.iuturakulov.hseapple.utils.IP_ADDRESS
import com.iuturakulov.hseapple.utils.TEMP_TOKEN
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.util.concurrent.TimeUnit

/**
 * Not used.
 */
object OkHttpInstance {

    private var INSTANCE: OkHttpClient? = null

    fun getInstance(): OkHttpClient = INSTANCE ?: kotlin.run {
        OkHttpClient().newBuilder()
            .retryOnConnectionFailure(true)
            .pingInterval(8, TimeUnit.SECONDS)
            .connectTimeout(4, TimeUnit.SECONDS)
            .readTimeout(7, TimeUnit.SECONDS)
            .writeTimeout(7, TimeUnit.SECONDS)
            .connectionPool(ConnectionPool(1, 25, TimeUnit.SECONDS))
            .build()
    }

    fun getRequest(requestHttp: String?, token: String?): Request {
        return Request.Builder()
            .url("${IP_ADDRESS}$requestHttp")
            .method("GET", null)
            .addHeader(
                "Authorization",
                TEMP_TOKEN
            )
            .addHeader("Content-Type", "application/json")
            // .addHeader("Cookie", "JSESSIONID=53530B6092B00A54239E5E86BAEE3EE6")
            .build()
    }


    fun postRequest(requestHttp: String?, body: RequestBody?, token: String?): Request {
        return Request.Builder()
            .url(
                "${IP_ADDRESS}$requestHttp"
            )
            .method("PUT", body)
            .addHeader(
                "Authorization",
                token
            )
            .addHeader("Content-Type", "application/json")
            // .addHeader("Cookie", "JSESSIONID=53530B6092B00A54239E5E86BAEE3EE6")
            .build()
    }

    fun putRequest(requestHttp: String?, body: RequestBody?, token: String?): Request {
        return Request.Builder()
            .url("${IP_ADDRESS}$requestHttp")
            .method("PUT", body)
            .addHeader(
                "Authorization",
                TEMP_TOKEN
            )
            .addHeader("Content-Type", "application/json")
            // .addHeader("Cookie", "JSESSIONID=53530B6092B00A54239E5E86BAEE3EE6")
            .build()
    }
}
