package com.iuturakulov.data.datasource.user

import com.iuturakulov.data.network.UserApiService
import com.iuturakulov.domain.entities.UserEntity
import retrofit2.Response

interface UserRemoteDataSource {
    suspend fun getUser(userId: String): UserEntity
    suspend fun getUsers(): List<UserEntity>
}

class UserRemoteDataSourceImpl(private val apiService: UserApiService) : UserRemoteDataSource {

    override suspend fun getUser(userId: String): UserEntity {
        val response = apiService.getUser(userId)
        return handleResponse(response) { it.user }
    }

    override suspend fun getUsers(): List<UserEntity> {
        val response = apiService.getUsers()
        return handleResponse(response) { it.users }
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
