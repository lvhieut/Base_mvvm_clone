package com.hieunv.mvvmarch.base.api.common

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResultResponse<out T>(

        @Json(name = "result")
        val data: T?

)
