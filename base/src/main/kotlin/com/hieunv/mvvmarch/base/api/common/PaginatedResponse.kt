package com.hieunv.mvvmarch.base.api.common

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PaginatedResponse <out T>(
        @Json(name = "result")
        val data: List<T>?,

        @Json(name = "pagination")
        val pagination: Pagination?
) {

    @JsonClass(generateAdapter = true)
    data class Pagination(
            @Json(name = "after_id")
            val afterId: Long?
    )

}
