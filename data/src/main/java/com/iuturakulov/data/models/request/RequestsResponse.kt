package com.iuturakulov.data.models.request

import com.iuturakulov.domain.entities.RequestEntity

data class RequestsResponse(
    val requests: List<RequestEntity>
)
