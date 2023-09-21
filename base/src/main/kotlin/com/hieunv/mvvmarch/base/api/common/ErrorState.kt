package com.hieunv.mvvmarch.base.api.common

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorState(
        @Json(name = "error_message")
        val errorMessage: String?,

        @Json(name = "error_code")
        val errorCode: Int?
)
